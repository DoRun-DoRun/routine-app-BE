package dorun.project.routineapp.routine.domain.repository;

import dorun.project.routineapp.routine.domain.model.Routine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findAllByUserId(String userId);
}
