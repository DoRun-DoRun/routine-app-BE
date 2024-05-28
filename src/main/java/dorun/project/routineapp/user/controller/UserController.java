package dorun.project.routineapp.user.controller;

import dorun.project.routineapp.common.ApiResponse;
import dorun.project.routineapp.common.ApiResponseMessage;
import dorun.project.routineapp.user.controller.dto.UserDetailResponse;
import dorun.project.routineapp.user.domain.User;
import dorun.project.routineapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ApiResponse<UserDetailResponse> getUser(@PathVariable String userId) {
        User user = userService.findByUserId(userId);
        UserDetailResponse response = UserDetailResponse.of(user);
        return new ApiResponse<>(ApiResponseMessage.SUCCESS_REQUEST, response);
    }
}
