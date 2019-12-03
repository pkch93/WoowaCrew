package woowacrew.security.provider;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import woowacrew.oauth.OauthService;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.domain.UserOauthDto;
import woowacrew.user.domain.UserRepository;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final OauthService oauthService;

    public SocialLoginAuthenticationProvider(UserRepository userRepository, OauthService oauthService) {
        this.userRepository = userRepository;
        this.oauthService = oauthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();
        String accessToken = oauthService.getAccessToken(code);
        UserOauthDto userOauthDto = oauthService.getUserInfo(accessToken);
        User user = userRepository.findByUserId(userOauthDto.getUserId())
                .orElseGet(() -> registerUser(userOauthDto));
        UserContext userContext = new ModelMapper().map(user, UserContext.class);
        return new SocialPostAuthorizationToken(userContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private User registerUser(UserOauthDto userOauthDto) {
        //Todo User 엔티티의 createUser() 사용 예정
        return userRepository.save(new User(userOauthDto.getUserId(), null));
    }
}