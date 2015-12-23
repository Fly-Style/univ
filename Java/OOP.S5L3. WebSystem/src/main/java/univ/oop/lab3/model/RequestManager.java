package univ.oop.lab3.model;

import univ.oop.lab3.model.database.DBPoolCache;
import univ.oop.lab3.model.entities.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by flystyle on 19.12.15.
 */
public class RequestManager {

    public static List<Request> GetRequest() throws SQLException {
        Connection connect = null;
        List<Request> requestArrayList = null;

        connect = DBPoolCache.getInstance().getConnection();

        String sql = "SELECT lab3oop.request.*, lab3oop.citizen.id " +
                " FROM lab3oop.request LEFT JOIN lab3oop.citizen ON owner_id = citizen.id";
        PreparedStatement statement = connect.prepareStatement(sql);
        requestArrayList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            requestArrayList.add(new Request(
                    resultSet.getInt("id"),
                    resultSet.getInt("owner_id"),
                    resultSet.getString("scale"),
                    resultSet.getTimestamp("work_time"),
                    resultSet.getString("work_object"),
                    resultSet.getBoolean("work_status")
            ));
        }
        return requestArrayList;

    }

    public static List <Request> GetConcreteCitizenRequests(int requester) throws SQLException {
        Connection connect = null;
        List<Request> requestArrayList = null;

        connect = DBPoolCache.getInstance().getConnection();
        String sql = "SELECT lab3oop.request.*, lab3oop.citizen.id " +
                " FROM lab3oop.request LEFT JOIN lab3oop.citizen ON owner_id = citizen.id WHERE request.owner_id= ?";

        PreparedStatement statement = connect.prepareStatement(sql);
        requestArrayList = new ArrayList<>();
        statement.setInt(1, requester);
        ResultSet resultSet = statement.executeQuery();
        System.out.println(requester);


        while (resultSet.next()) {
            requestArrayList.add(new Request(
                    resultSet.getInt("id"),
                    resultSet.getInt("owner_id"),
                    resultSet.getString("scale"),
                    resultSet.getTimestamp("work_time"),
                    resultSet.getString("work_object"),
                    resultSet.getBoolean("work_status")
            ));
        }

        return requestArrayList;
    }

    public static boolean ChangeStatus(int requestId, boolean status) {
        boolean result = false;

        Connection connection = null;
        try {
            connection = DBPoolCache.getInstance().getConnection();
            String sql = "UPDATE lab3oop.request SET request.work_status=? WHERE request.id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, status);
            statement.setInt(2, requestId);
            statement.executeUpdate();
            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBPoolCache.getInstance().putConnection(connection);
            }
        }

        return result;
    }

    public static boolean CheckList () {
        Connection connection = null;
        boolean result = false;
        try {
            connection = DBPoolCache.getInstance().getConnection();
            String sql =  "UPDATE lab3oop.request SET work_status = ? WHERE request.work_time < current_timestamp";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.executeUpdate();

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static boolean RequestExists(String id) {
        Connection connection = null;
        boolean result = false;
        try {
            connection = DBPoolCache.getInstance().getConnection();
            String sql = "SELECT * FROM lab3oop.request WHERE lab3oop.request.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
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
