package univ.oop.lab3.model;

import univ.oop.lab3.model.database.DBPoolCache;
import univ.oop.lab3.model.entities.Citizen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by flystyle on 19.12.15.
 */
public class LoginVerification {
        public static Citizen verify(String login, String password) {
            Citizen user = null;
            Connection connection = null;
            try {
                connection = DBPoolCache.getInstance().getConnection();
                String sql = "SELECT * FROM lab3oop.citizen " +
                             " WHERE citizen.login= " + "'" + login  +"'" + " AND citizen.password= "+ "'" + password  +"'";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int userID = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String district = resultSet.getString("district");
                    String street = resultSet.getString("street");
                    int flat = resultSet.getInt("flat");
                    int house = resultSet.getInt("house");

                    System.out.println(login + ", " + password);

                    user = new Citizen(userID, login, password, name, district, street, flat, house);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    DBPoolCache.getInstance().putConnection(connection);
                }
            }
            return user;
        }
    }
