package model.validation;

import model.Activity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ActivityValidator implements Validator
{
    private final Activity activity;

    private final List<String> errors;

    public ActivityValidator(Activity activity) {
        this.activity=activity;
        errors = new ArrayList<>();
    }

    public boolean validate()
    {
        validateDate(activity.getDate());
        return errors.isEmpty();
    }

    private void validateDate(Date date)
    {
        if(date.after(new Date()))
            errors.add("Time paradox");
    }
    public List<String> getErrors() {
        return errors;
    }
}
