package dorun.project.routineapp.oauth.service;

import dorun.project.routineapp.oauth.entity.ProviderType;
import dorun.project.routineapp.oauth.entity.RoleType;
import dorun.project.routineapp.oauth.entity.UserPrincipal;
import dorun.project.routineapp.oauth.exception.OAuth2ProviderException;
import dorun.project.routineapp.oauth.info.OAuth2UserInfo;
import dorun.project.routineapp.oauth.info.OAuth2UserInfoFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dorun.project.routineapp.user.domain.User;
import dorun.project.routineapp.user.domain.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ProviderType providerType = ProviderType.valueOf(userRequest
                .getClientRegistration()
                .getRegistrationId()
                .toUpperCase());

        Map<String, Object> userAttributes = getAttributes(userRequest, providerType);

        try {
            OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, userAttributes);
            User savedUser = userRepository.findByUserIdAndProviderType(userInfo.getId(), providerType)
                    .orElse(createUser(userInfo, providerType));

            if (savedUser != null) {
                if (providerType != savedUser.getProviderType()) {
                    throw new OAuth2ProviderException(savedUser.getProviderType() + "계정으로 가입하셨습니다."
                            + "해당 플랫폼을 통해 다시 로그인 해 주세요.");
                } // 연동을 시켜버릴까?
                updateUser(savedUser, userInfo);
            } else {
                savedUser = createUser(userInfo, providerType);
            }

            return UserPrincipal.create(savedUser, userAttributes);
//        } catch (AuthenticationException e) {
//            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        return User.builder()
                .userId(userInfo.getId())
                .nickname(userInfo.getName())
//                .password("NO_PASS")
                .email(userInfo.getEmail())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .build();
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getNickname().equals(userInfo.getName())) {
            user.updateNickname(userInfo.getName());
        }
        return user;
    }

    private Map<String, Object> getAttributes(OAuth2UserRequest request, ProviderType providerType) {
        if (providerType.equals(ProviderType.APPLE)) {
            return decodeAppleJwtTokenPayload(request.getAdditionalParameters().get("id_token").toString());
        }
        OAuth2User user = super.loadUser(request);
        return user.getAttributes();
    }

    private Map<String, Object> decodeAppleJwtTokenPayload(String appleIdToken) {
        Map<String, Object> jwtClaims = new HashMap<>();
        try {
            String[] parts = appleIdToken.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(decodedString, Map.class);
            jwtClaims.putAll(map);
            jwtClaims.put("id_token", appleIdToken);
        } catch (JsonProcessingException e) {
            logger.error("decodeJwtToken: {}-{} / jwtToken : {}", e.getMessage(), e.getCause(), appleIdToken);
        }
        return jwtClaims;
    }
}
