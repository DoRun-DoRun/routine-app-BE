package dorun.project.routineapp.routine.domain.repository;

import dorun.project.routineapp.routine.domain.model.SubRoutine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRoutineRepository extends JpaRepository<SubRoutine, Long> {
    List<SubRoutine> findAllByRoutineId(Long routineId);
}
