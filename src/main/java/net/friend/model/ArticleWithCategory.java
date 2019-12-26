package net.friend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ArticleWithCategory {

  private String categoryName;

  private String articleTitle;

  private String articleBody;
}
