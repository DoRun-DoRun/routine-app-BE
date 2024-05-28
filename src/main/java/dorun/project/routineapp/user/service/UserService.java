package dorun.project.routineapp.user.service;

import dorun.project.routineapp.common.exception.ErrorCode;
import dorun.project.routineapp.common.exception.NotFoundException;
import dorun.project.routineapp.user.domain.User;
import dorun.project.routineapp.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
