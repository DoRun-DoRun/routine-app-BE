package dorun.project.routineapp.routine.domain.model;

import lombok.Getter;

@Getter
public enum SubRoutineStatus {
    NOT_STARTED,
    RUNNING,
    STOPPED,
    COMPLETE;
}
