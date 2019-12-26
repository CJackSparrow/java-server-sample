package net.friend.service.impl;

import java.util.List;
import java.util.function.Function;
import net.friend.model.Category;
import net.friend.repository.CategoryRepository;
import net.friend.service.CategoryService;
import net.friend.specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.topicanative.generic.specification.GenericSpecificationsBuilder;
import vn.edu.topicanative.generic.specification.SearchCriteria;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Category> findByIds(Long[] ids) {
    return categoryRepository.findByIds(ids);
  }

  @Override
  public List<Category> findByIdsAndCreatedBy(Long[] ids, long createdBy) {
    return categoryRepository.findByIdsAndCreatedBy(ids, createdBy);
  }

  @Override
  public Page<Category> filter(String search, Long userId, Pageable pageable) {
    GenericSpecificationsBuilder<Category> builder = new GenericSpecificationsBuilder<>();
    if (userId != null) {
      builder.with("createdBy", ":", userId, "", "");
    }

    Function<SearchCriteria, Specification<Category>> converter = CategorySpecification::new;
    Specification<Category> spec = builder.build(converter, search);
    return categoryRepository.findAll(spec, pageable);
  }
}
