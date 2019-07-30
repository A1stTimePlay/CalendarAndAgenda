package bss.intern.calendarandagenda.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "AgendaEvent")
public class AgendaEvent {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String Name;
    @ColumnInfo
    private String Note;
    @ColumnInfo
    private String DateBegin;
    @ColumnInfo
    private String DateEnd;
    @ColumnInfo
    private String TimeBegin;
    @ColumnInfo
    private String TimeEnd;
    @ColumnInfo
    private String Location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getDateBegin() {
        return DateBegin;
    }

    public void setDateBegin(String dateBegin) {
        DateBegin = dateBegin;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getTimeBegin() {
        return TimeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        TimeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        TimeEnd = timeEnd;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
