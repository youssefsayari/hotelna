package tn.esprit.innoxpert.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.Entity.Activity;
import tn.esprit.innoxpert.Entity.TypeActivity;
import tn.esprit.innoxpert.Entity.TypeUser;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Repository.ActivityRepository;
import tn.esprit.innoxpert.Util.UserClient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActivityService implements ActivityServiceInterface {

    private final ActivityRepository activityRepository;
    private final UserClient userClient;  // Injecting UserClient

    // 1. Get all activities
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // 2. Get activity by ID
    @Override
    public Activity getActivityById(Long activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    // 3. Add a new activity
    @Override
    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // 4. Remove activity by ID
    @Override
    public void removeActivityById(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    // 5. Update an existing activity
    @Override
    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // 6. Participate in an activity (with UserClient usage)
    public String participateInActivity(Long activityId, Long userId) {
        // Get the activity
        Optional<Activity> activityOpt = activityRepository.findById(activityId);
        if (activityOpt.isEmpty()) {
            return "Activity not found.";
        }

        Activity activity = activityOpt.get();

        // Check if the activity is expired
        if (activity.isExpired()) {
            return "The activity is expired.";
        }

        // Fetch the user using UserClient (calls the user service)
        User user = userClient.getUserById(userId);  // Feign call to get the user

        if (user == null) {
            return "User not found.";
        }
        // Check if user type is "Visiteur"
        if (!user.getTypeUser().equals(TypeUser.Visiteur)) {
            return "Only 'Visiteur' type users can participate.";
        }

        // Check if the user is already participating in the activity
        if (activity.getUserIds().contains(user.getIdUser())) {
            return "You have already participated in this activity.";
        }

        // Add the user to the activity and update the capacity
        activity.getUserIds().add(user.getIdUser());
        activity.setCapacity(activity.getCapacity() - 1);

        // Save the updated activity
        activityRepository.save(activity);
        return "Participation successful!";
    }

    // 7. Get activity statistics
    public Map<String, Object> getActivityStatistics() {
        Map<String, Object> stats = new HashMap<>();

        List<Activity> allActivities = activityRepository.findAll();

        // 1. Count of activities by type
        Map<TypeActivity, Long> activitiesByType = allActivities.stream()
                .collect(Collectors.groupingBy(Activity::getTypeActivity, Collectors.counting()));
        stats.put("activitiesByType", activitiesByType);

        // 2. Participation by type
        Map<TypeActivity, Long> participationByType = new HashMap<>();
        for (TypeActivity type : TypeActivity.values()) {
            long count = allActivities.stream()
                    .filter(activity -> activity.getTypeActivity() == type)
                    .mapToLong(activity -> activity.getUserIds().size())
                    .sum();
            participationByType.put(type, count);
        }
        stats.put("participationByType", participationByType);

        // 3. Activities that reached full capacity
        List<Activity> fullCapacityActivities = allActivities.stream()
                .filter(activity -> activity.getCapacity() <= 0)
                .collect(Collectors.toList());

        Map<String, Object> fullCapacityStats = new HashMap<>();
        fullCapacityStats.put("count", fullCapacityActivities.size());
        fullCapacityStats.put("activities", fullCapacityActivities.stream()
                .map(activity -> {
                    Map<String, Object> activityMap = new HashMap<>();
                    activityMap.put("id", activity.getId());
                    activityMap.put("name", activity.getName());
                    activityMap.put("type", activity.getTypeActivity());
                    activityMap.put("originalCapacity", activity.getUserIds().size() + activity.getCapacity());
                    activityMap.put("participantsCount", activity.getUserIds().size());
                    return activityMap;
                })
                .collect(Collectors.toList()));
        stats.put("fullCapacityActivities", fullCapacityStats);

        // 4. Most popular type in the last week
        LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.WEEKS);
        Map<TypeActivity, Long> recentParticipation = new HashMap<>();
        for (Activity activity : allActivities) {
            if (activity.getStartDate().isAfter(oneWeekAgo)) {
                recentParticipation.merge(activity.getTypeActivity(), (long) activity.getUserIds().size(), Long::sum);
            }
        }

        Optional<Map.Entry<TypeActivity, Long>> mostPopularRecent = recentParticipation.entrySet().stream()
                .max(Map.Entry.comparingByValue());

           // Use string label if none found
        stats.put("mostPopularRecentType",
                mostPopularRecent.map(entry -> entry.getKey().name()).orElse("No recent participation"));
        stats.put("recentParticipationCounts", recentParticipation);


        // 5. User participation by activity type (count of unique users)
        Map<TypeActivity, Set<Long>> usersByActivityType = new HashMap<>();
        for (TypeActivity type : TypeActivity.values()) {
            Set<Long> userIds = allActivities.stream()
                    .filter(activity -> activity.getTypeActivity() == type)
                    .flatMap(activity -> activity.getUserIds().stream())
                    .collect(Collectors.toSet());
            usersByActivityType.put(type, userIds);
        }

        stats.put("uniqueUsersByType", usersByActivityType.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())));

        // 6. Totals
        long totalActivities = allActivities.size();
        long totalParticipants = allActivities.stream()
                .mapToLong(activity -> activity.getUserIds().size())
                .sum();

        stats.put("totalActivities", totalActivities);
        stats.put("totalParticipants", totalParticipants);
        stats.put("averageParticipationPerActivity",
                totalActivities > 0 ? (double) totalParticipants / totalActivities : 0);

        return stats;
    }


}
