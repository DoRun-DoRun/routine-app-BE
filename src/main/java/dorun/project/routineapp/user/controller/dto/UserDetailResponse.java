package dorun.project.routineapp.user.controller.dto;

import dorun.project.routineapp.user.domain.User;
import java.time.LocalDateTime;

public record UserDetailResponse(String userId, String email, String nickname, LocalDateTime createdAt) {
    public static UserDetailResponse of(User user) {
        return new UserDetailResponse(user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getCreatedAt());
    }
}