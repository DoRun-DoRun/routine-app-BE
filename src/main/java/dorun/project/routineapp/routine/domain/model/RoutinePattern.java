package dorun.project.routineapp.routine.domain.model;

import lombok.Getter;

@Getter
public enum RoutinePattern {
    EVERY_DAY("매일"),
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일"),
    WEEKEND("주말");

    private final String value;

    private RoutinePattern(String value) {
        this.value = value;
    }

    public static RoutinePattern fromString(String patternString) {
        String uppercasePattern = patternString.toUpperCase();
        return RoutinePattern.valueOf(uppercasePattern);
    }

}
