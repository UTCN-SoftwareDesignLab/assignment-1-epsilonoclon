package service.activity;

import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.ActivityValidator;
import model.validation.Notification;
import model.validation.Validator;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;

import java.sql.Date;
import java.util.List;

public class ActivityServiceSQL implements ActivityService
{
    private final ActivityRepository repository;

    public ActivityServiceSQL(ActivityRepository activityRepository) {
        this.repository = activityRepository;
    }

    @Override
    public List<Activity> findAll()
    {
        return repository.findAll();
    }

    @Override
    public Activity findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public List<Activity> findByPID(Long userId) {
        return repository.findByPID(userId);
    }

    @Override
    public Notification<Boolean> save(String type, Long userId, Date date, Long clientId, Long accountId) {
        Activity activity=new ActivityBuilder().setPid(userId).setType(type).setModifiedAccountId(accountId).setModifiedClientId(clientId).setDate(date).build();

        Validator activityValidator=new ActivityValidator(activity);

        boolean activityValid = activityValidator.validate();
        Notification<Boolean> activitySavingNotification = new Notification<>();

        if (!activityValid) {
            activityValidator.getErrors().forEach(activitySavingNotification::addError);
            activitySavingNotification.setResult(Boolean.FALSE);
        } else {
            activitySavingNotification.setResult(repository.save(activity));
        }
        return activitySavingNotification;
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }
}
