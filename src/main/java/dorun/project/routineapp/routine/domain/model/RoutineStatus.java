package dorun.project.routineapp.routine.domain.model;

import lombok.Getter;

@Getter
public enum RoutineStatus {
    NONE,
    FAILED,
    SKIPPED,
    COMPLETED;
}
