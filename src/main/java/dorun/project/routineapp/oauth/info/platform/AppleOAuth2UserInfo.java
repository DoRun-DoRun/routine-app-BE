package dorun.project.routineapp.oauth.info.platform;

import dorun.project.routineapp.oauth.info.OAuth2UserInfo;
import java.util.Map;

public class AppleOAuth2UserInfo extends OAuth2UserInfo {
    public AppleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return "id";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}