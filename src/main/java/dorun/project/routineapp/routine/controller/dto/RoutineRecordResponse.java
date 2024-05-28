package dorun.project.routineapp.routine.controller.dto;

public record RoutineRecordResponse (Long routineId, String routineGoal,
                                     RoutineStatusRecord completedRecord,
                                     RoutineStatusRecord failedRecord,
                                     RoutineStatusRecord skippedRecord) {
}
