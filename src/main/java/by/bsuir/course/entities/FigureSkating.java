package by.bsuir.course.entities;

import java.io.Serializable;
import java.util.Objects;

public class FigureSkating extends SoloSport implements Serializable {
    private double maxMark = 6;
    private int countOfReferees = 2;

    public FigureSkating(String name) {
        super(name);

    }

    @Override
    public void addResult(Referee referee, Mark mark) {
        marks.put(referee, mark);
    }

    @Override
    public int getCountOfReferees() {
        return countOfReferees;
    }

    @Override
    public double getMaxMark() {
        return maxMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FigureSkating that = (FigureSkating) o;
        return Double.compare(that.maxMark, maxMark) == 0 &&
                countOfReferees == that.countOfReferees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxMark, countOfReferees);
    }
}
