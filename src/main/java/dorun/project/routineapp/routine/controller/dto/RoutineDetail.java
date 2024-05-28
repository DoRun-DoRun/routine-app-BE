package dorun.project.routineapp.routine.controller.dto;

import dorun.project.routineapp.routine.domain.model.Routine;
import dorun.project.routineapp.routine.domain.model.RoutinePattern;
import java.util.List;

public record RoutineDetail(Long routineId, String goal,
                            int totalDuration, int continuousCount, int cumulativeCount,
                            List<RoutinePattern> routinePattern) {

    public static RoutineDetail from(Routine routine) {
        return new RoutineDetail(routine.getRoutineId(),
                routine.getGoal(),
                routine.getTotalDuration(),
                routine.getContinuousCount(),
                routine.getCumulativeCount(),
                routine.getRoutinePattern().stream().toList());
    }
}
