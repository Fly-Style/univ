package univ.oop.lab3.model;

import univ.oop.lab3.model.database.DBPoolCache;
import univ.oop.lab3.model.entities.Citizen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flystyle on 19.12.15.
 */
public class CitizenManager {
    public static List<Citizen> GetAllGoodCitizens() {
        Connection connection = null;
        List<Citizen> users = null;
        try {
            connection = DBPoolCache.getInstance().getConnection();
            String sql = "SELECT * FROM lab3oop.citizen";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            users = new LinkedList<>();
            while (resultSet.next()) {
                Citizen currentUser = new Citizen(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("district"),
                        resultSet.getString("street"),
                        resultSet.getInt("flat"),
                        resultSet.getInt("house"));
                users.add(currentUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBPoolCache.getInstance().putConnection(connection);
            }
        }

        return users;
    }

    public static boolean CitizenExists(String id) {
        Connection connection = null;
        boolean result = false;
        try {

            connection = DBPoolCache.getInstance().getConnection();
            String sql = "SELECT * FROM lab3oop.citizen WHERE lab3oop.citizen.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println(id);
            int intId = Integer.parseInt(id);
            System.out.println(intId);
            statement.setInt(1, intId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBPoolCache.getInstance().putConnection(connection);
            }
        }

        return result;
    }
}
