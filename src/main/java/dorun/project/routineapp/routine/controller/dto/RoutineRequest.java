package dorun.project.routineapp.routine.controller.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record RoutineRequest(@NotBlank String goal, List<String> routinePatterns,
                             boolean notificationEnabled, @Nullable Long notificationTime) {
    public RoutineRequest {
        if (notificationTime == null) {
            notificationTime = 0L;
        }
    }
}
