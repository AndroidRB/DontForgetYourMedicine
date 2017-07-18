package rs.fon.dontforgetyourmedicine.data.model;

import java.util.Date;

/**
 * Created by radovan.bogdanic on 3/18/2017.
 */

public class AlarmDatabaseModel {

    private int id;
    private String medicName;
    private Date startTime;
    private Date startDate;
    private int alarmRepeat;
    private Date alarmExpireDate;
    private Date dateCreated;
    private Date dateDeleted;

    public AlarmDatabaseModel() {}

    public AlarmDatabaseModel(String medicName, Date startTime, Date startDate, int alarmRepeat, Date alarmExpireDate, Date dateCreated) {
        this.medicName = medicName;
        this.startTime = startTime;
        this.startDate = startDate;
        this.alarmRepeat = alarmRepeat;
        this.alarmExpireDate = alarmExpireDate;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicName() {
        return medicName;
    }

    public void setMedicName(String medicName) {
        this.medicName = medicName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getAlarmRepeat() {
        return alarmRepeat;
    }

    public void setAlarmRepeat(int alarmRepeat) {
        this.alarmRepeat = alarmRepeat;
    }

    public Date getAlarmExpireDate() {
        return alarmExpireDate;
    }

    public void setAlarmExpireDate(Date alarmExpireDate) {
        this.alarmExpireDate = alarmExpireDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
}
