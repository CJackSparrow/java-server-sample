package net.friend.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.friend.model.Category;
import net.friend.service.CategoryService;
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
public class CategoryController extends BaseController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/filter/categories", method = RequestMethod.GET)
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

      Page<Category> categories = categoryService
          .filter(search, userId, getPageRequest(page, size, sort, order));
      ApiPageResponseBuilder<Category> builder = new ApiPageResponseBuilder<>(categories, "categories");
      return builder.build();
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }

  @RequestMapping(value = "/list/categories", method = RequestMethod.GET)
  public ApiPageResponse categoriesFilter(
      Authentication authentication,
      @RequestParam("ids") String ids) {
    try {
      ApiInputIds apiInputIds = JsonUtil.convertInputIds(ids);

      List<Category> categories;
      if (!isAdmin()) {
        Long userId = Long.parseLong(authentication.getName());
        categories = categoryService.findByIdsAndCreatedBy(apiInputIds.getId(), userId);
      } else {
        categories = categoryService.findByIds(apiInputIds.getId());
      }

      ApiPageResponseBuilder<Category> builder = new ApiPageResponseBuilder<>(categories, "categories");
      return builder.buildList();
    } catch (Exception ex) {
      log.error(ExceptionUtil.getStackTrace(ex));
      return null;
    }
  }
}
