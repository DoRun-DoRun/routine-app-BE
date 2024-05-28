package dorun.project.routineapp.routine.service;

import dorun.project.routineapp.common.exception.ErrorCode;
import dorun.project.routineapp.common.exception.NotFoundException;
import dorun.project.routineapp.routine.controller.dto.RoutineRecordResponse;
import dorun.project.routineapp.routine.controller.dto.RoutineStatusRecord;
import dorun.project.routineapp.routine.controller.dto.SubRoutineSummary;
import dorun.project.routineapp.routine.domain.model.Routine;
import dorun.project.routineapp.routine.domain.model.RoutinePattern;
import dorun.project.routineapp.routine.domain.model.RoutineRecord;
import dorun.project.routineapp.routine.domain.repository.RoutineRecordRepository;
import dorun.project.routineapp.routine.domain.repository.RoutineRepository;
import dorun.project.routineapp.routine.domain.model.RoutineStatus;
import dorun.project.routineapp.routine.domain.model.SubRoutine;
import dorun.project.routineapp.routine.domain.repository.SubRoutineRepository;
import dorun.project.routineapp.routine.domain.vo.WeekPeriod;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineRecordRepository routineRecordRepository;
    private final SubRoutineRepository subRoutineRepository;

    @Transactional
    public Routine createRoutine(String userId, String goal, List<String> routinePatterns,
                                 boolean notificationEnabled, Long notificationTime) {
        Routine routine = Routine.builder()
                .userId(userId)
                .goal(goal)
                .routinePattern(routinePatterns.stream()
                        .map(RoutinePattern::fromString)
                        .collect(Collectors.toSet()))
                .notificationEnabled(notificationEnabled)
                .notificationTime(LocalTime.ofSecondOfDay(notificationTime))
                .build();
        return routineRepository.save(routine);
    }

    // todo : TransactionEventListener
    @Transactional
    public Routine complete(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROUTINE_NOT_FOUND));

        routineRecordRepository.save(RoutineRecord.builder()
                .routineId(routineId)
                .date(LocalDate.now())
                .routineStatus(RoutineStatus.COMPLETED)
                .build());

        RoutineRecord mock = RoutineRecord.builder()
                .routineStatus(RoutineStatus.COMPLETED)
                .routineId(1L)
                .date(LocalDate.of(2024, 5, 20))
                .build();

        routineRecordRepository.save(mock);

        routine.complete();
        return routine;
    }

    @Transactional
    public Routine fail(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROUTINE_NOT_FOUND));

        routineRecordRepository.save(RoutineRecord.builder()
                .routineId(routineId)
                .date(LocalDate.now())
                .routineStatus(RoutineStatus.FAILED)
                .build());

        routine.fail();
        return routine;
    }

    @Transactional
    public SubRoutine createSubRoutine(Long routineId, String content) {
        routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROUTINE_NOT_FOUND));

        SubRoutine subRoutine = SubRoutine.builder()
                .routineId(routineId)
                .content(content)
                .build();

        return subRoutineRepository.save(subRoutine);
    }

    public Routine findById(Long routineId) {
        return routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROUTINE_NOT_FOUND));
    }

    public List<Routine> findAllRoutinesByUserId(String userId) {
        return routineRepository.findAllByUserId(userId);
    }

    public List<RoutineRecordResponse> findAllRoutineByUserIdAndBetweenPeriod(String userId,
                                                                              LocalDate startDate, LocalDate endDate) {
        List<Routine> routines = routineRepository.findAllByUserId(userId);

        return routines.stream()
                .map(routine -> getRoutineRecordResponse(routine, startDate, endDate))
                .toList();
    }

    public List<RoutineRecordResponse> findAllRoutineWeekRecordsByUserId(String userId) {
        List<Routine> routines = routineRepository.findAllByUserId(userId);
        WeekPeriod weekPeriod = WeekPeriod.calculateCurrentWeekPeriod();

        return routines.stream()
                .map(route -> getRoutineRecordResponse(route, weekPeriod.startDate(), weekPeriod.endDate()))
                .toList();
    }

    public List<RoutineRecordResponse> findAllRoutinePreviousWeekRecordsByUserId(String userId) {
        List<Routine> routines = routineRepository.findAllByUserId(userId);
        WeekPeriod weekPeriod = WeekPeriod.calculatePreviousWeekPeriod();

        return routines.stream()
                .map(route -> getRoutineRecordResponse(route, weekPeriod.startDate(), weekPeriod.endDate()))
                .toList();
    }

    public List<SubRoutine> findAllSubRoutinesByRoutineId(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROUTINE_NOT_FOUND));

        return subRoutineRepository.findAllByRoutineId(routineId);
    }

    private RoutineRecordResponse getRoutineRecordResponse(Routine routine, LocalDate startDate, LocalDate endDate) {

        // 주어진 기간 내의 루틴의 기록을 가져옴
        List<RoutineRecord> routineRecords = getRoutineRecordsForWeek(routine.getRoutineId(), startDate, endDate);

        Map<RoutineStatus, List<RoutineRecord>> groupedRecords = routineRecords.stream()
                .collect(Collectors.groupingBy(RoutineRecord::getRoutineStatus));

        RoutineStatusRecord completedRecord = createRoutineStatusRecord(RoutineStatus.COMPLETED,
                groupedRecords.getOrDefault(RoutineStatus.COMPLETED, new ArrayList<>()));

        RoutineStatusRecord failedRecord = createRoutineStatusRecord(RoutineStatus.FAILED,
                groupedRecords.getOrDefault(RoutineStatus.FAILED, new ArrayList<>()));

        RoutineStatusRecord skippedRecord = createRoutineStatusRecord(RoutineStatus.SKIPPED,
                groupedRecords.getOrDefault(RoutineStatus.SKIPPED, new ArrayList<>()));

        return new RoutineRecordResponse(routine.getRoutineId(), routine.getGoal(),
                completedRecord, failedRecord, skippedRecord);
    }

    private List<RoutineRecord> getRoutineRecordsForWeek(Long routineId, LocalDate startDate, LocalDate endDate) {
        return routineRecordRepository.findAllByRoutineIdAndDateBetween(routineId, startDate, endDate);
    }

    private RoutineStatusRecord createRoutineStatusRecord(RoutineStatus status, List<RoutineRecord> records) {
        Set<LocalDate> dates = records.stream()
                .map(RoutineRecord::getDate)
                .collect(Collectors.toSet());

        return new RoutineStatusRecord(status, records.size(), new ArrayList<>(dates));
    }
}
