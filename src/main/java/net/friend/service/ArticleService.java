package net.friend.service;

import java.util.List;
import net.friend.model.Article;
import net.friend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

  Article create(String title, String body, Category category);

  Article findByIdAndCreatedBy(long id, long createdBy);

  /**
   * admin-on-rest function
   */
  List<Article> findByIds(Long[] ids);

  /**
   * admin-on-rest function
   */
  List<Article> findByIdsAndCreatedBy(Long[] ids, long createdBy);

  /**
   * admin-on-rest function
   */
  Page<Article> filter(String search, Long userId, Pageable pageable);
}
