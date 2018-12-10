package by.bsuir.course.database;

import by.bsuir.course.entities.Referee;
import by.bsuir.course.entities.Sportsman;

import java.io.Serializable;
import java.util.List;

public class DataBaseData implements Serializable {
    private List<Sportsman> sportsmen;
    private List<Referee> referees;

    public DataBaseData(List<Sportsman> sportsmen, List<Referee> referees) {
        this.sportsmen = sportsmen;
        this.referees = referees;
    }

    public DataBaseData() {
    }

    public List<Sportsman> getSportsmen() {
        return sportsmen;
    }

    public void setSportsmen(List<Sportsman> sportsmen) {
        this.sportsmen = sportsmen;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }
}
