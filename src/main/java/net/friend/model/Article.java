package net.friend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.edu.topica.authentication.model.BaseObject;

@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "sample_room")
public class Article extends BaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String body;

  @ManyToOne
  private Category category;


  public Article(String title, String body, Category category) {
    this.title = title;
    this.body = body;
    this.category = category;
  }

  public Long getCategoryId() {
    if (category != null) {
      return category.getId();
    }

    return null;
  }
}
