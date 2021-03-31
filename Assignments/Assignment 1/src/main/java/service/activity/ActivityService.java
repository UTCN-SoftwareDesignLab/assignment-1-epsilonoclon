package service.activity;

import model.Activity;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.Date;
import java.util.List;

public interface ActivityService {
    List<Activity> findAll();

    Activity findById(Long id) throws EntityNotFoundException;

    List<Activity> findByPID(Long userId);

    Notification<Boolean> save(String type, Long userId, Date date, Long clientId, Long accountId);

    void removeAll();
}
