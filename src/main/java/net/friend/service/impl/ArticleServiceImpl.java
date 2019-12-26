package net.friend.service.impl;

import java.util.List;
import java.util.function.Function;
import net.friend.model.Article;
import net.friend.model.Category;
import net.friend.repository.ArticleRepository;
import net.friend.service.ArticleService;
import net.friend.specification.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.topicanative.generic.specification.GenericSpecificationsBuilder;
import vn.edu.topicanative.generic.specification.SearchCriteria;

@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public Article create(String title, String body, Category category) {
    Article article = new Article(title, body, category);
    return articleRepository.save(article);
  }

  @Override
  public Article findByIdAndCreatedBy(long id, long createdBy) {
    return articleRepository.findByIdAndCreatedBy(id, createdBy);
  }

  @Override
  public List<Article> findByIds(Long[] ids) {
    return articleRepository.findByIds(ids);
  }

  @Override
  public List<Article> findByIdsAndCreatedBy(Long[] ids, long createdBy) {
    return articleRepository.findByIdsAndCreatedBy(ids, createdBy);
  }

  @Override
  public Page<Article> filter(String search, Long userId, Pageable pageable) {
    GenericSpecificationsBuilder<Article> builder = new GenericSpecificationsBuilder<>();
    if (userId != null) {
      builder.with("createdBy", ":", userId, "", "");
    }

    Function<SearchCriteria, Specification<Article>> converter = ArticleSpecification::new;
    Specification<Article> spec = builder.build(converter, search);
    return articleRepository.findAll(spec, pageable);
  }
}
