package net.friend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vn.edu.topica.authentication.model.BaseObject;

@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "r4r_article", indexes = {
    @Index(name = "idx_is_active", columnList = "is_active")
})
@ToString(exclude = "articles")
public class Category extends BaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(name = "is_active")
  private Boolean isActive;


  @JsonIgnore
  @JoinColumn(name = "category_id")
  @OneToMany
  private Set<Article> articles;


  public Category(String name, boolean isActive) {
    this.name = name;
    this.isActive = isActive;
  }

  /**
   * For Specification
   */
  public Category(Long id) {
    this.id = id;
  }
}
