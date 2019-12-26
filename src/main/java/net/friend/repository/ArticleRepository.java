package net.friend.repository;

import java.util.List;
import net.friend.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>,
    JpaSpecificationExecutor<Article> {

  /**
   * ActionValidator function
   */
  @RestResource(exported = false)
  @Query("SELECT obj FROM Article obj WHERE obj.id = :id AND createdBy = :createdBy")
  Article findByIdAndCreatedBy(
      @Param("id") long id,
      @Param("createdBy") long createdBy);

  /**
   * admin-on-rest function which get multiple User by arrays of ids
   */
  @Query("SELECT obj FROM Article obj WHERE obj.id IN :ids")
  @RestResource(exported = false)
  List<Article> findByIds(
      @Param("ids") Long[] ids);

  @Query("SELECT obj FROM Article obj WHERE obj.id IN :ids AND obj.createdBy = :createdBy")
  @RestResource(exported = false)
  List<Article> findByIdsAndCreatedBy(
      @Param("ids") Long[] ids,
      @Param("createdBy") long createdBy);

  @PreAuthorize("@actionValidatorService.isAdmin()")
  Page<Article> findAll(Pageable pageable);
}
