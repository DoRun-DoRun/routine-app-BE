package dorun.project.routineapp.routine.domain.vo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public record WeekPeriod(LocalDate startDate, LocalDate endDate) {

    public static WeekPeriod calculateCurrentWeekPeriod() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return new WeekPeriod(startDate, endDate);
    }

    public static WeekPeriod calculatePreviousWeekPeriod() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(6);
        return new WeekPeriod(startDate, endDate);
    }
}
