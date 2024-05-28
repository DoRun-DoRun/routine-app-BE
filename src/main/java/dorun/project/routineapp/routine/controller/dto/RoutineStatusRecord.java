package dorun.project.routineapp.routine.controller.dto;

import dorun.project.routineapp.routine.domain.model.RoutineStatus;
import java.time.LocalDate;
import java.util.List;

public record RoutineStatusRecord (RoutineStatus status, int count, List<LocalDate> dates) {
}
