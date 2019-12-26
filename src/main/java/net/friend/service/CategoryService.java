package net.friend.service;

import java.util.List;
import net.friend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

  /**
   * admin-on-rest function
   */
  List<Category> findByIds(Long[] ids);

  /**
   * admin-on-rest function
   */
  List<Category> findByIdsAndCreatedBy(Long[] ids, long createdBy);

  /**
   * admin-on-rest function
   */
  Page<Category> filter(String search, Long userId, Pageable pageable);
}
