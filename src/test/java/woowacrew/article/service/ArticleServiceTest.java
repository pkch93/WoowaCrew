package woowacrew.article.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.domain.Article;
import woowacrew.article.domain.ArticleResponse;
import woowacrew.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleInternalService articleInternalService;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void 원하는_게시글을_ArticleResponse로_변환해준다() {
        Article article = new Article("title", "content", new User("userId", "url"));
        Long articleId = 1L;
        when(articleInternalService.findById(articleId)).thenReturn(article);

        ArticleResponse articleResponse = articleService.findById(articleId);

        assertThat(articleResponse.getTitle()).isEqualTo("title");
        assertThat(articleResponse.getContent()).isEqualTo("content");
        assertThat(articleResponse.getUserDto().getUserId()).isEqualTo("userId");
        assertThat(articleResponse.getUserDto().getUrl()).isEqualTo("url");
    }

    @Test
    void 전체_게시글을_ArticleResponse로_변환해준다() {
        List<Article> articles = createArticles(10);
        when(articleInternalService.findAll()).thenReturn(articles);

        List<ArticleResponse> articleResponses = articleService.findAll();

        assertThat(articleResponses.size()).isEqualTo(10);
    }

    private List<Article> createArticles(int numberOfArticle) {
        User user = new User("userId", "url");
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticle; i++) {
            articles.add(new Article(String.valueOf(i), String.valueOf(i), user));
        }
        return articles;
    }
}