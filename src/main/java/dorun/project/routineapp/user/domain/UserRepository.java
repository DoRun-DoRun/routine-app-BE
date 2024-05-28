package dorun.project.routineapp.user.domain;

import dorun.project.routineapp.oauth.entity.ProviderType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndProviderType(String userId, ProviderType providerType);
}
