package com.example.superheltv5.repositories;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.repositories.util.ConnectionManager;
import com.example.superheltv5.services.SuperheroException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("real_database")
public class SuperheroRepository {

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  public SuperheroRepository() {} //TODO kan vist godt slettes

  public List<Superhero> getAll() {
    List<Superhero> superheroList = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      System.out.println("Connection object without singleton: " + conn);
      String SQL = "select hero_id, hero_name, real_name, creation_year from superhero order by hero_name;";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);

      while (rs.next()) {
        // table columns retrieved by name
        int heroId = rs.getInt("hero_id");
        String heroName = rs.getString("hero_name");
        String realName = rs.getString("real_name");
        int creationYear = rs.getInt("creation_year");
        superheroList.add(new Superhero(heroId, heroName, realName, creationYear));
      }
      return superheroList; //TODO align error handling in repo classes
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Superhero> getAll2() throws SuperheroException {
    List<Superhero> superheroList = new ArrayList<>();

    try {
      //Connection conn = ConnectionManager.getConnection(url, user, password);
      Connection conn = ConnectionManager.getConnection();
      System.out.println("Connection object  singleton: " + conn);
      PreparedStatement psts = conn.prepareStatement("SELECT * from superhero");
      ResultSet resultSet = psts.executeQuery();

      while (resultSet.next()){
        // table columns retrieved by position
        int id = resultSet.getInt(1);
        String heroName = resultSet.getString(2);
        String realName = resultSet.getString(3);
       // String superPower = resultSet.getString(4);
        int creationYear = resultSet.getInt(5);
        //superheltList.add(new Superhero(id, heroName, realName, superPower, creationYear));
        superheroList.add(new Superhero(id, heroName, realName, creationYear));
      }
    }
    catch (SQLException e)
    {
      System.out.println("Something wrong with query");
      e.printStackTrace();
      throw new SuperheroException("Something wrong with query");
    }

    return superheroList;
  }

  public Superhero getSuperheroByName(String heroName) {
    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      String SQL = "select hero_id, hero_name, real_name, creation_year from superhero where hero_name = ?";
      PreparedStatement pstmt = conn.prepareStatement(SQL);
      pstmt.setString(1, heroName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        int hero_id = rs.getInt("hero_id");
        String hero_name = rs.getString("hero_name");
        String real_name = rs.getString("real_name");
        int creation_year = rs.getInt("creation_year");
        return new Superhero(hero_id, hero_name, real_name, creation_year);
      }
      //else return null;
      else return new Superhero(0, "N/A","N/A",0); //TODO change to null
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
