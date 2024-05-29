package dorun.project.routineapp.routine.domain.model;

import dorun.project.routineapp.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "routine_review")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE routine_review SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoutineReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_review_id")
    private Long routineReviewId;

    @Column(name = "routine_id")
    @NotNull
    private Long routineId;

    @Column(name = "userId")
    @NotNull
    private Long userId;

    @Column(name = "review_content")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @JsonIgnore
    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
