package dorun.project.routineapp.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private AuthService authService;

    @Nested
    class Login {

        @Test
        @DisplayName("소셜 로그인요청시 소셜 로그인 페이지로 리다이렉팅")
        void login_Success() throws Exception {

            // given
//            given(authService.login(any(CustomOAuth2User.class)))
//                    .willReturn(new AuthToken("accessToken", "refreshToken"));

            // when
            // then
            mockMvc.perform(get("/oauth2/authorization/kakao")
//                            .queryParam("code", "authorization code")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());
        }
    }

}