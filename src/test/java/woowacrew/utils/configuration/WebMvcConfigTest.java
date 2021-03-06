package woowacrew.utils.configuration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebMvcConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 로그인이_되어있지_않으면_로그인페이지로_리다이렉트() {
        webTestClient.get()
                .uri("/asdfasdf")
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    @Test
    void 인덱스_페이지는_인터셉터를_거치지_않는다() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void 정적파일_요청은_인터셉터를_거치지_않는다() {
        webTestClient.get()
                .uri("/css/index.css")
                .exchange()
                .expectStatus()
                .isOk();
    }
}