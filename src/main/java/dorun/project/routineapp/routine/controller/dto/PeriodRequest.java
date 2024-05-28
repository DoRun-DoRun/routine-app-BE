package dorun.project.routineapp.routine.controller.dto;

import java.time.LocalDate;

public record PeriodRequest(LocalDate startDate, LocalDate endDate) {
}
