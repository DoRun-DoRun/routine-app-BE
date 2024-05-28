package dorun.project.routineapp.oauth.service;

import dorun.project.routineapp.common.exception.ErrorCode;
import dorun.project.routineapp.common.exception.NotFoundException;
import dorun.project.routineapp.oauth.entity.UserPrincipal;
import dorun.project.routineapp.user.domain.User;
import dorun.project.routineapp.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        if (user == null) {
            throw new UsernameNotFoundException("사용자 이름을 찾을 수 없습니다.");
        }
        return UserPrincipal.create(user);
    }
}
