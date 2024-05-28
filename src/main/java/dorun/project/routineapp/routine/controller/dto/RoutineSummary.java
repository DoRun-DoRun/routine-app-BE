package dorun.project.routineapp.routine.controller.dto;

import dorun.project.routineapp.routine.domain.model.Routine;

public record RoutineSummary(Long routineId, String goal, int continuousCount) {

    public static RoutineSummary from(Routine routine) {
        return new RoutineSummary(routine.getRoutineId(), routine.getGoal(), routine.getContinuousCount());
    }
}
