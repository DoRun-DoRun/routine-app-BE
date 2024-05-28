package dorun.project.routineapp.routine.domain.repository;

import dorun.project.routineapp.routine.domain.model.RoutineRecord;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoutineRecordRepository extends JpaRepository<RoutineRecord, Long> {
    List<RoutineRecord> findAllByRoutineId(@Param("routineId") Long routineId);

    @Query("SELECT r FROM RoutineRecord r WHERE r.routineId = :routineId AND r.date BETWEEN :startDate AND :endDate")
    List<RoutineRecord> findAllByRoutineIdAndDateBetween(@Param("routineId") Long routineId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);
}
