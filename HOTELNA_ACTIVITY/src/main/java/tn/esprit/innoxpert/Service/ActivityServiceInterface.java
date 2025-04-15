package tn.esprit.innoxpert.Service;

import tn.esprit.innoxpert.DTO.ParticipationRequest;
import tn.esprit.innoxpert.Entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityServiceInterface {
    public List<Activity> getAllActivities();
    public Activity getActivityById(Long ActivityId);
    public Activity addActivity(Activity b);

    public void removeActivityById(Long ActivityId);
    public Activity updateActivity (Activity b );
    public String participateInActivity(Long userId, Long activityId, String firstName, String lastName, String email, Long telephone) ;
    public Map<String, Object> getActivityStatistics();
}
