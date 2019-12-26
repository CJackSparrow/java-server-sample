package net.friend.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.friend.model.Article;
import net.friend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.topica.authentication.controller.BaseController;
import vn.edu.topicanative.generic.api.input.ApiInputIds;
import vn.edu.topicanative.generic.api.output.ApiPageResponse;
import vn.edu.topicanative.generic.api.output.ApiPageResponseBuilder;
import vn.edu.topicanative.util.ExceptionUtil;
import vn.edu.topicanative.util.JsonUtil;

@RestController
@Slf4j
@RequestMapping("/api")
public class ArticleController extends BaseController {

  @Autowired
  private ArticleService articleService;

  @RequestMapping(value = "/filter/articles", method = RequestMethod.GET)
  public ApiPageResponse categoriesFilter(
      Authentication authentication,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size,
      @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
      @RequestParam(name = "order", required = false, defaultValue = "DESC") String order,
      @RequestParam("filter") String search) {
    try {
      Long userId = null;
      if (!isAdmin()) {
        userId = Long.parseLong(authentication.getName());
      }

      Page<Article> articles = articleService
          .filter(search, userId, getPageRequest(page, size, sort, order));
      ApiPageResponseBuilder<Article> builder = new ApiPageResponseBuilder<>(articles, "articles");
      return builder.build();
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }

  @RequestMapping(value = "/list/articles", method = RequestMethod.GET)
  public ApiPageResponse categoriesFilter(
      Authentication authentication,
      @RequestParam("ids") String ids) {
    try {
      ApiInputIds apiInputIds = JsonUtil.convertInputIds(ids);

      List<Article> articles;
      if (!isAdmin()) {
        Long userId = Long.parseLong(authentication.getName());
        articles = articleService.findByIdsAndCreatedBy(apiInputIds.getId(), userId);
      } else {
        articles = articleService.findByIds(apiInputIds.getId());
      }

      ApiPageResponseBuilder<Article> builder = new ApiPageResponseBuilder<>(articles, "articles");
      return builder.buildList();
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }
}
