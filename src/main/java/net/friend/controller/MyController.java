package net.friend.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.friend.model.ArticleWithCategory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.topicanative.generic.api.output.ApiPageResponse;
import vn.edu.topicanative.generic.api.output.ApiPageResponseBuilder;
import vn.edu.topicanative.util.ExceptionUtil;

@RestController
@Slf4j
@RequestMapping("/api")
public class MyController {

  @RequestMapping(value = "/my/article", method = RequestMethod.GET)
  public ArticleWithCategory article(
      Authentication authentication) {
    try {
      ArticleWithCategory articleWithCategory = new ArticleWithCategory("cat name", "article title", "article body");
      return articleWithCategory;
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }

  @RequestMapping(value = "/my/articles", method = RequestMethod.GET)
  public ApiPageResponse articles(
      Authentication authentication) {
    try {
      ArticleWithCategory articleWithCategory1 = new ArticleWithCategory("cat name", "article title", "article body");
      ArticleWithCategory articleWithCategory2 = new ArticleWithCategory("cat name", "article title", "article body");
      List<ArticleWithCategory> articles = new ArrayList<>();
      articles.add(articleWithCategory1);
      articles.add(articleWithCategory2);

      ApiPageResponseBuilder<ArticleWithCategory> builder = new ApiPageResponseBuilder<>(articles, "articles");
      return builder.buildList();
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }
}
