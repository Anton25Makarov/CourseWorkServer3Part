package by.bsuir.course.logic;

import by.bsuir.course.entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MarkCalculator {
    public double calculate(Sportsman sportsman){
        SoloSport performance = sportsman.getPerformance();
        double total = 0;
        if (performance instanceof FigureSkating) {
            total = calculateFigureSkatingMark(performance);
        } else if (performance instanceof Diving) {
            total = calculateDivingMark(performance);
        } else if (performance instanceof SkiJumping) {
            total = calculateSkiJumpingMark(performance);
        }
        return total;
    }
    private double calculateFigureSkatingMark(SoloSport performance) {
        List<Mark> marks = new ArrayList<>(performance.getMarks().values());
        marks.removeAll(Collections.singleton(null));
        double total = 0;
        for (Mark mark : marks) {
            if (mark == null) {
                continue;
            }
            double technicalMark = ((FigureSkatingMark) mark).getTechnicalMark();
            double presentationMark = ((FigureSkatingMark) mark).getPresentationMark();
            total += (technicalMark + presentationMark) / 2;
        }
        if (marks.size() == 0) {
            return 0;
        }
        return total / marks.size();
    }

    private double calculateDivingMark(SoloSport performance) {
        List<Mark> marks = new ArrayList<>(performance.getMarks().values());
        double total = 0;
        marks.removeAll(Collections.singleton(null));
        if (marks.size() > 2) {
            marks = marks.stream()
                    .sorted(Comparator.comparingDouble(mark -> ((DivingMark) mark).getMark()))
                    .collect(Collectors.toList());
            marks.remove(0);
            marks.remove(marks.size() - 1);
            for (Mark mark : marks) {
                total += ((DivingMark) mark).getMark() * 0.6;
            }
            return total;
        }
        return 0;
    }

    private double calculateSkiJumpingMark(SoloSport performance) {
        List<Mark> marks = new ArrayList<>(performance.getMarks().values());
        double total = 0;
        marks.removeAll(Collections.singleton(null));
        marks = marks.stream()
                .sorted(Comparator.comparingDouble(mark -> ((SkiJumpingMark) mark).getMark()))
                .collect(Collectors.toList());
        if (marks.size() > 2) {

            marks.remove(0);
            marks.remove(marks.size() - 1);
            for (Mark mark : marks) {
                total += ((SkiJumpingMark) mark).getMark();
            }
            return total;
        }
        return 0;
    }
}
