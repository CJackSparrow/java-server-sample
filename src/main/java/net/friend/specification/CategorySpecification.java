package net.friend.specification;

import static vn.edu.topicanative.generic.specification.SearchOperation.EQUALITY;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import net.friend.model.Category;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.topicanative.generic.specification.GenericSpecification;
import vn.edu.topicanative.generic.specification.SearchCriteria;

@AllArgsConstructor
public class CategorySpecification implements Specification<Category> {

  private SearchCriteria criteria;

  @Override
  public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (criteria.getOperation().equals(EQUALITY)) {
      if (criteria.getKey().equals("q")) {
        return criteriaBuilder
            .like(root.get("name"), "%" + criteria.getValue().toString().trim() + "%");
      }
    }

    return GenericSpecification
        .getInstance(criteria).toPredicate(root, criteriaQuery, criteriaBuilder, criteria);
  }
}
