package com.example.superheltv5.repositories.util;

import com.example.superheltv5.services.SuperheroException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
  private static Connection conn;

  // @Value kan ikke benyttes til at initialisere statiske attributter
  // benyt setters i stedet
  private static String URL;
  private static String USER;
  private static String PASSWORD;

  @Value("${spring.datasource.url}")
  public void setUrl(String url) {
    URL = url;
  }

  @Value("${spring.datasource.username}")
  public void setUser(String user) {
    USER = user;
  }

  @Value("${spring.datasource.password}")
  public void setPassword(String password) {
    PASSWORD = password;
  }

  public static Connection getConnection() throws SuperheroException {
    try {
      if (conn == null) conn = DriverManager.getConnection(URL,USER,PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SuperheroException("Could not connect to database");
    }
    return conn;
  }

}
