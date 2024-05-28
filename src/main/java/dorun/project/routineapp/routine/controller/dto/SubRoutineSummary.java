package dorun.project.routineapp.routine.controller.dto;

import dorun.project.routineapp.routine.domain.model.SubRoutine;
import dorun.project.routineapp.routine.domain.model.SubRoutineStatus;

public record SubRoutineSummary(Long subRoutineId, String content, int duration, SubRoutineStatus status) {

    public static SubRoutineSummary of(final SubRoutine subRoutine) {
        return new SubRoutineSummary(
                subRoutine.getSubRoutineId(),
                subRoutine.getContent(),
                subRoutine.getDuration(),
                subRoutine.getSubRoutineStatus());
    }
}
