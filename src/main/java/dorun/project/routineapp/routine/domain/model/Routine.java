package dorun.project.routineapp.routine.domain.model;

import dorun.project.routineapp.common.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "routines")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE routines SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long routineId;

    @Column(name = "user_id")
    @NotNull
    private String userId;

    @Column(name = "goal")
    private String goal;

    @Builder.Default
    @Column(name = "total_duration")
    private int totalDuration = 0;

    @Builder.Default
    @Column(name = "continuous_count")
    private int continuousCount = 0;

    @Builder.Default
    @Column(name = "cumulativeCount")
    private int cumulativeCount = 0;

    @Builder.Default
    @ElementCollection(targetClass = RoutinePattern.class)
    @CollectionTable(name = "routine_pattern", joinColumns = @JoinColumn(name = "routine_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoutinePattern> routinePattern = new HashSet<>();

    @Column(name = "notification_enabled")
    private Boolean notificationEnabled;

    @Column(name = "notification_time")
    private LocalTime notificationTime;

    @JsonIgnore
    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isDeleted = Boolean.FALSE;

    public void complete() {
        this.continuousCount += 1;
        this.cumulativeCount += 1;
    }

    // todo: 만약 실패 상태가 다시 성공으로 상태 변경이 되면?
    // 기존의 연속된 날짜가 복구되어야 할 것이다.
    // 이에 대한 처리가 필요.
    public void fail() {
        this.continuousCount = 0;
    }
}
