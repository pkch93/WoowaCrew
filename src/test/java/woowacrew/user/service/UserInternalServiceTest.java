package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInternalServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserInternalService userInternalService;

    @Test
    void findByUserId() {
        String userId = "userId";
        String url = "url";
        User user = new User(userId, url);
        when(userRepository.findByUserId(userId)).thenReturn(user);

        User expectUser = userInternalService.findByUserId(userId);

        assertThat(expectUser.getUserId()).isEqualTo(userId);
        assertThat(expectUser.getUrl()).isEqualTo(url);
    }
}