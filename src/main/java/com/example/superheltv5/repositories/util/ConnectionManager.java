package com.example.superheltv5.repositories.util;

import com.example.superheltv5.services.SuperheroException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {

  private static Connection conn;
  private static String URL_STATIC;
  private static String USER_STATIC;
  private static String PASSWORD_STATIC;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  @PostConstruct
  public void init() throws SuperheroException {
    System.out.println("Initialization of Connection Manager static attributes");
    URL_STATIC = url;
    USER_STATIC = user;
    PASSWORD_STATIC = password;
    try {
      conn = DriverManager.getConnection(URL_STATIC, USER_STATIC,PASSWORD_STATIC);
    } catch (SQLException e) {
      throw new SuperheroException("Could not connect to database");
    }
  }

  public static Connection getConnection()  {
    return conn;
  }
}
