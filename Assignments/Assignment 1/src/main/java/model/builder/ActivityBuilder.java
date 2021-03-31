package model.builder;

import model.Account;
import model.Activity;

import java.sql.Date;

public class ActivityBuilder implements Builder<Activity>
{
    private Activity activity;

    public ActivityBuilder setId(Long id)
    {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setPid(Long pid)
    {
        activity.setPid(pid);
        return this;
    }

    public ActivityBuilder setDate(Date date)
    {
        activity.setDate(date);
        return this;
    }

    public ActivityBuilder setType(String type) {
        activity.setType(type);
        return this;
    }

    public ActivityBuilder setModifiedClientId(Long modifiedId) {
        activity.setModifiedClientID(modifiedId);
        return this;
    }

    public ActivityBuilder setModifiedAccountId(Long modifiedId) {
        activity.setModifiedAccountID(modifiedId);
        return this;
    }

    public Activity build() {
        return activity;
    }
}
