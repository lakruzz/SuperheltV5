package com.example.superheltv5.repositories.util;

import com.example.superheltv5.services.SuperheroException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {

  @PostConstruct
  public void init() throws SQLException {
    System.out.println("Initialization of Connection Manager static attributes");
    URL_STATIC = url;
    USER_STATIC = user;
    PASSWORD_STATIC = password;
    conn = DriverManager.getConnection(URL_STATIC, USER_STATIC,PASSWORD_STATIC);
  }

  private static String URL_STATIC;
  private static String USER_STATIC;
  private static String PASSWORD_STATIC;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;


  // https://www.baeldung.com/spring-inject-static-field
  private static Connection conn;

  /*public void setUrl(String url) {
    this.url = url;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }*/

  /*@Value("${spring.datasource.url}")
  public void setUrlStatic(String url){
    ConnectionManager.URL_STATIC = url;
  }

  @Value("${spring.datasource.username}")
  public void setUserStatic(String username){
    ConnectionManager.USER_STATIC = username;
  }

  @Value("${spring.datasource.username}")
  public void setPasswordStatic(String password){
    ConnectionManager.PASSWORD_STATIC = password;
  }
*/
 /* @Value("${spring.datasource.url}, ${spring.datasource.username}, ${spring.datasource.password}")
  public void setConn(String url, String user, String password) throws SQLException {
    conn = DriverManager.getConnection(url, user, password);
    System.out.println("her er jeg");
  }*/



  public static Connection getConnection() throws SuperheroException {

  //public static Connection getConnection(String url, String user, String password) throws SuperheroException {

    if (conn != null) return conn;

   try {
      conn = DriverManager.getConnection(URL_STATIC, USER_STATIC, PASSWORD_STATIC);
     //conn = DriverManager.getConnection(url, user, password);

    } catch (SQLException e) {
      System.out.println("Couldn't connect to db");
      e.printStackTrace();throw new SuperheroException("Cannot connect to database");
    }
    return conn;
  }

}
