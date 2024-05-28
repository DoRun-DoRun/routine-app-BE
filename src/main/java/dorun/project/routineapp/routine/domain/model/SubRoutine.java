package dorun.project.routineapp.routine.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "sub_routines")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE sub_routines SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SubRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_routine_id")
    private Long subRoutineId;

    @Column(name = "routine_id")
    @NotNull
    private Long routineId;

    @Column(name = "content")
    private String content;

    @Column(name = "duration")
    private int duration;

    @Builder.Default
    @Column(name = "sub_routine_status")
    @Enumerated(EnumType.STRING)
    private SubRoutineStatus subRoutineStatus = SubRoutineStatus.NOT_STARTED;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = Boolean.FALSE;
}
