package model;

import java.sql.Date;

public class Activity {
    private Long id;
    private Long Pid;
    private Date date;
    private String type;
    private Long modifiedClientID;
    private Long modifiedAccountID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return Pid;
    }

    public void setPid(Long pid) {
        Pid = pid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getModifiedClientID() {
        return modifiedClientID;
    }

    public void setModifiedClientID(Long modifiedClientID) {
        this.modifiedClientID = modifiedClientID;
    }

    public Long getModifiedAccountID() {
        return modifiedAccountID;
    }

    public void setModifiedAccountID(Long modifiedAccountID) {
        this.modifiedAccountID = modifiedAccountID;
    }
//**************************** Have some activity log message ready
    public String toString()
    {
        String activity="";
        activity=activity.concat("Type of activity "+getType()+"\n");
        activity=activity.concat("Date "+getDate()+"\n");
        activity=activity.concat("modified account "+getModifiedAccountID()+"\n");
        activity=activity.concat("modified client "+getModifiedClientID()+"\n");
        return activity;
    }

}
