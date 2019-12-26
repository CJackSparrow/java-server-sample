package net.friend.repository;

import java.util.List;
import net.friend.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>,
    JpaSpecificationExecutor<Category> {

  /**
   * ActionValidator function
   */
  @RestResource(exported = false)
  Category findByIdAndCreatedBy(
      @Param("id") long id,
      @Param("createdBy") long createdBy);

  /**
   * admin-on-rest function which get multiple Category by arrays of ids
   */
  @Query("SELECT obj FROM Category obj WHERE obj.id IN :ids")
  @RestResource(exported = false)
  List<Category> findByIds(
      @Param("ids") Long[] ids);

  /**
   * admin-on-rest function which get multiple Category by arrays of ids and createdBy
   */
  @Query("SELECT obj FROM Category obj WHERE obj.id IN :ids AND obj.createdBy = :createdBy")
  @RestResource(exported = false)
  List<Category> findByIdsAndCreatedBy(
      @Param("ids") Long[] ids,
      @Param("createdBy") long createdBy);

  /**
   * search with specification
   * @param pageable
   * @return
   */
  @PreAuthorize("@actionValidatorService.isAdmin()")
  Page<Category> findAll(Pageable pageable);
}
