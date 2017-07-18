package rs.fon.dontforgetyourmedicine.data.model;

import java.util.Date;

/**
 * Created by radovan.bogdanic on 6/6/2017.
 */

public class HistoryModel {

    private int histid;
    private String histmMedicName;
    private Date histStartDate;
    private int histAlarmRepeat;
    private Date histEndDate;
    private Date dateCreated;

    public HistoryModel() {
    }

    public HistoryModel(String histmMedicName, Date histStartDate, int histAlarmRepeat, Date histEndDate, Date dateCreated) {
        this.histmMedicName = histmMedicName;
        this.histStartDate = histStartDate;
        this.histAlarmRepeat = histAlarmRepeat;
        this.histEndDate = histEndDate;
        this.dateCreated = dateCreated;
    }

    public int getHistid() {
        return histid;
    }

    public void setHistid(int histid) {
        this.histid = histid;
    }

    public String getHistmMedicName() {
        return histmMedicName;
    }

    public void setHistmMedicName(String histmMedicName) {
        this.histmMedicName = histmMedicName;
    }

    public Date getHistStartDate() {
        return histStartDate;
    }

    public void setHistStartDate(Date histStartDate) {
        this.histStartDate = histStartDate;
    }

    public Date getHistEndDate() {
        return histEndDate;
    }

    public int getHistAlarmRepeat() {
        return histAlarmRepeat;
    }

    public void setHistAlarmRepeat(int histAlarmRepeat) {
        this.histAlarmRepeat = histAlarmRepeat;
    }

    public void setHistEndDate(Date histEndDate) {
        this.histEndDate = histEndDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
