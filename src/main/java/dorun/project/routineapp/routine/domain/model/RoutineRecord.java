package dorun.project.routineapp.routine.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "routine_records", indexes = @Index(name = "idx_date", columnList = "date"))
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE routine_records SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoutineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_record_id")
    private Long routineRecordId;

    @Column(name = "routine_id")
    private Long routineId;

    @Column(name = "routine_status")
    @Enumerated(EnumType.STRING)
    private RoutineStatus routineStatus;

    @Column(name = "date")
    private LocalDate date;

    @JsonIgnore
    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
