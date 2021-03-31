package repository.activity;

import model.Activity;
import repository.EntityNotFoundException;

import java.util.List;

public interface ActivityRepository
{
    List<Activity> findAll();

    Activity findById(Long id) throws EntityNotFoundException;

    List<Activity> findByPID(Long userId);

    boolean save(Activity activity);

    void removeAll();
}
