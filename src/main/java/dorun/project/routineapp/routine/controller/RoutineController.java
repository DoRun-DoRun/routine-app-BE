package dorun.project.routineapp.routine.controller;

import dorun.project.routineapp.common.ApiResponse;
import dorun.project.routineapp.common.ApiResponseMessage;
import dorun.project.routineapp.routine.controller.dto.PeriodRequest;
import dorun.project.routineapp.routine.controller.dto.RoutineDetail;
import dorun.project.routineapp.routine.controller.dto.RoutineRecordResponse;
import dorun.project.routineapp.routine.controller.dto.RoutineRequest;
import dorun.project.routineapp.routine.controller.dto.RoutineSummary;
import dorun.project.routineapp.routine.controller.dto.SubRoutineRequest;
import dorun.project.routineapp.routine.controller.dto.SubRoutineSummary;
import dorun.project.routineapp.routine.domain.model.Routine;
import dorun.project.routineapp.routine.domain.model.SubRoutine;
import dorun.project.routineapp.routine.service.RoutineService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping()
    public ApiResponse<List<RoutineSummary>> getAllRoutinesByUserId(@NotBlank @RequestParam("userId") String userId) {
        List<Routine> routines = routineService.findAllRoutinesByUserId(userId);

        List<RoutineSummary> response = routines.stream()
                .map(RoutineSummary::from)
                .toList();

        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @GetMapping("/{routineId}")
    public ApiResponse<RoutineDetail> getRoutineById(@NotNull @PathVariable("routineId") Long routineId) {
        Routine routine = routineService.findById(routineId);
        RoutineDetail response = RoutineDetail.from(routine);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @GetMapping("/records")
    public ApiResponse<List<RoutineRecordResponse>> getRoutineRecordsByUserIdBetweenDate(
            @RequestParam("userId") String userId, @RequestBody PeriodRequest request) {

        List<RoutineRecordResponse> response = routineService
                .findAllRoutineByUserIdAndBetweenPeriod(userId, request.startDate(), request.endDate());

        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @GetMapping("/records/week")
    public ApiResponse<List<RoutineRecordResponse>> getRoutineCurrentWeekRecordsByUserId(
            @RequestParam("userId") String userId) {

        List<RoutineRecordResponse> response = routineService.findAllRoutineWeekRecordsByUserId(userId);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @GetMapping("/records/previousWeek")
    public ApiResponse<List<RoutineRecordResponse>> getRoutinePreviousWeekRecordsByUserId(
            @RequestParam("userId") String userId) {

        List<RoutineRecordResponse> response = routineService.findAllRoutinePreviousWeekRecordsByUserId(userId);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @GetMapping("/subRoutines")
    public ApiResponse<List<SubRoutineSummary>> getSubRoutines(@RequestParam("routineId") Long routineId) {
        List<SubRoutine> subRoutines = routineService.findAllSubRoutinesByRoutineId(routineId);

        List<SubRoutineSummary> response = subRoutines.stream()
                .map(SubRoutineSummary::of)
                .toList();

        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }

    @PostMapping()
    public ApiResponse<Routine> createRoutine(@RequestParam("userId") String userId, @RequestBody RoutineRequest request) {
        Routine routine = routineService.createRoutine(
                userId,
                request.goal(),
                request.routinePatterns(),
                request.notificationEnabled(),
                request.notificationTime());

        return new ApiResponse<>(ApiResponseMessage.CREATE_ROUTINE, routine);
    }

    @PostMapping("/{routineId}/subRoutine")
    public ApiResponse<SubRoutine> createSubRoutine(@PathVariable("routineId") Long routineId,
                                                    @RequestBody SubRoutineRequest request) {
        SubRoutine subRoutine = routineService.createSubRoutine(routineId, request.content());

        return new ApiResponse<>(ApiResponseMessage.CREATE_ROUTINE, subRoutine);
    }

    @PatchMapping("/{routineId}/complete")
    public ApiResponse<Routine> completeRoutine(@PathVariable("routineId") Long routineId) {
        Routine routine = routineService.complete(routineId);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, routine);
    }

    @PatchMapping("/{routineId}/fail")
    public ApiResponse<Routine> failRoutine(@PathVariable("routineId") Long routineId) {
        Routine routine = routineService.fail(routineId);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, routine);
    }
}
