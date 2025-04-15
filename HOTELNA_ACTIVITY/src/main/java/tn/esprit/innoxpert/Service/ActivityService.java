package tn.esprit.innoxpert.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.innoxpert.DTO.ParticipationRequest;
import tn.esprit.innoxpert.Entity.Activity;
import tn.esprit.innoxpert.Entity.TypeActivity;
import tn.esprit.innoxpert.Entity.TypeUser;
import tn.esprit.innoxpert.Entity.User;
import tn.esprit.innoxpert.Repository.ActivityRepository;
import tn.esprit.innoxpert.Repository.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActivityService implements ActivityServiceInterface {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(Long activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    @Override
    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void removeActivityById(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public String participateInActivity(Long userId, Long activityId, String firstName, String lastName, String email, Long telephone) {
        // Get the activity by its ID
        Optional<Activity> activityOpt = activityRepository.findById(activityId);
        if (activityOpt.isEmpty()) {
            return "Activité non trouvée.";
        }

        Activity activity = activityOpt.get();

        // Check if the activity has expired
        if (activity.isExpired()) {
            return "L'activité est expirée.";
        }

        // Check if the activity has space left (capacity > 0)
        if (activity.getCapacity() <= 0) {
            return "Capacité maximale atteinte.";
        }

        // Get the user by userId
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "Utilisateur non trouvé. Seuls les utilisateurs existants peuvent participer.";
        }

        User user = userOpt.get();

        // Check if the provided first name, last name, and telephone match the user details
        if (!user.getFirstName().equals(firstName) ||
                !user.getLastName().equals(lastName) ||
                !user.getTelephone().equals(telephone)) {
            return "Les informations fournies ne correspondent pas à un utilisateur existant.";
        }

        // Ensure the user is of type 'Visiteur'
        if (user.getTypeUser() != TypeUser.Visiteur) {
            return "Seuls les utilisateurs de type 'Visiteur' peuvent participer.";
        }

        // Check if the user has already participated in this activity
        if (activity.getUsers().contains(user)) {
            return "Vous avez déjà participé à cette activité.";
        }

        // Add the user to the activity and the activity to the user's activities
        activity.getUsers().add(user);
        user.getActivities().add(activity);

        // Decrease the capacity of the activity
        activity.setCapacity(activity.getCapacity() - 1);

        // Save both the updated activity and user to the database
        activityRepository.save(activity);
        userRepository.save(user);



        return "Participation réussie !";
    }
    public Map<String, Object> getActivityStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Get all activities
        List<Activity> allActivities = activityRepository.findAll();

        // 1. Count of activities by type
        Map<TypeActivity, Long> activitiesByType = allActivities.stream()
                .collect(Collectors.groupingBy(Activity::getTypeActivity, Collectors.counting()));
        stats.put("activitiesByType", activitiesByType);

        // 2. Most popular activity types (by participation count)
        Map<TypeActivity, Long> participationByType = new HashMap<>();
        for (TypeActivity type : TypeActivity.values()) {
            long count = allActivities.stream()
                    .filter(activity -> activity.getTypeActivity() == type)
                    .flatMap(activity -> activity.getUsers().stream())
                    .count();
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
                    activityMap.put("originalCapacity", activity.getCapacity() + activity.getUsers().size());
                    activityMap.put("participantsCount", activity.getUsers().size());
                    return activityMap;
                })
                .collect(Collectors.toList()));

        stats.put("fullCapacityActivities", fullCapacityStats);

        // 4. Most popular type in the last week
        LocalDate oneWeekAgo = LocalDate.now().minus(1, ChronoUnit.WEEKS);
        Map<TypeActivity, Long> recentParticipation = allActivities.stream()
                .filter(activity -> activity.getStartDate().isAfter(oneWeekAgo))
                .flatMap(activity -> activity.getUsers().stream()
                        .map(user -> new AbstractMap.SimpleEntry<>(activity.getTypeActivity(), user)))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.counting()
                ));

        Optional<Map.Entry<TypeActivity, Long>> mostPopularRecent = recentParticipation.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        stats.put("mostPopularRecentType",
                mostPopularRecent.isPresent() ? mostPopularRecent.get().getKey() : "No recent participation");
        stats.put("recentParticipationCounts", recentParticipation);

        // 5. User participation by activity type
        Map<TypeActivity, Set<User>> usersByActivityType = new HashMap<>();
        for (TypeActivity type : TypeActivity.values()) {
            Set<User> users = allActivities.stream()
                    .filter(activity -> activity.getTypeActivity() == type)
                    .flatMap(activity -> activity.getUsers().stream())
                    .collect(Collectors.toSet());
            usersByActivityType.put(type, users);
        }
        stats.put("uniqueUsersByType", usersByActivityType.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())));

        // 6. Additional statistics
        long totalActivities = allActivities.size();
        long totalParticipants = userRepository.findAll().stream()
                .filter(user -> !user.getActivities().isEmpty())
                .count();

        stats.put("totalActivities", totalActivities);
        stats.put("totalParticipants", totalParticipants);
        stats.put("averageParticipationPerActivity",
                totalActivities > 0 ? (double) totalParticipants / totalActivities : 0);

        return stats;
    }


}
