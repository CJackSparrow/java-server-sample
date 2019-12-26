package net.friend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.edu.topica.authentication.config.EnableTopicanAuthenticaion;
import vn.edu.topica.authentication.service.impl.PasswordEncoderImpl;

@EnableTopicanAuthenticaion
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public static void main(String[] args) {
    log.info("start application");
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    long countRoles = countRoles();
    if(countRoles == 0){
      jdbcTemplate.update(
          "INSERT INTO auth_role (name, created_date, modified_date) VALUES (?, ?, ?)",
          "ROLE_ADMIN", System.currentTimeMillis(), System.currentTimeMillis());
    }

    long countUser = countUsers();
    if(countUser == 0){
      PasswordEncoderImpl passwordEncoderImpl = new PasswordEncoderImpl();
      String queryInsertUser = "INSERT INTO auth_user (username, password, email, is_active, created_date, modified_date, created_by, modified_by) VALUES ("
          + "'admin', "
          + "'" + passwordEncoderImpl.encode("admin123") + "', "
          + "'huynq12@topica.edu.vn', "
          + "true, "
          + System.currentTimeMillis() + ", "
          + System.currentTimeMillis() + ", "
          + "1, "
          + "1)";
      jdbcTemplate.update(queryInsertUser);

      jdbcTemplate.update(
          "INSERT INTO auth_role_user (user_id, role_id) VALUES (?, ?)",
          1, 1);
    }
  }

  private long countRoles(){
    String queryCountRoles = "select count(*) from auth_role";
    return jdbcTemplate.queryForObject(queryCountRoles, Long.class);
  }

  private long countUsers(){
    String queryCountUsers = "select count(*) from auth_user";
    return jdbcTemplate.queryForObject(queryCountUsers, Long.class);
  }
}
