package dorun.project.routineapp.routine.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SubRoutineRequest(@NotBlank String content) {
}
