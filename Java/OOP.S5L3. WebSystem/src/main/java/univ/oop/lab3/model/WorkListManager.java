package univ.oop.lab3.model;

import univ.oop.lab3.controller.WorkListController;
import univ.oop.lab3.model.database.DBPoolCache;
import univ.oop.lab3.model.entities.Request;
import univ.oop.lab3.model.entities.WorkList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by flystyle on 21.12.15.
 */
public class WorkListManager {
    public static List<Integer> idBusy = new ArrayList<Integer>();

    public static boolean CheckList() {
        boolean result = false;

        Connection connect = null;

        try {
            connect = DBPoolCache.getInstance().getConnection();
            Timestamp current = new Timestamp(Calendar.getInstance().getTimeInMillis());
            WorkList.SyncWithBase();
            String sql = "SELECT work_list.id FROM lab3oop.work_list WHERE work_list.end_time < current_timestamp";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                while (set.next()) {
                    EndWork(set.getInt(1));
                    Request req = WorkListManager.GetNextRequest();
                    WorkList.AddToList(req, set.getInt(1));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Request GetNextRequest () {
        Request request = null;
        Timestamp current = new Timestamp(Calendar.getInstance().getTimeInMillis());

        Connection connect = null;

        try {
            connect = DBPoolCache.getInstance().getConnection();
            String sql = "SELECT * FROM lab3oop.request WHERE request.work_status=FALSE ";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                request = new Request(
                        resultSet.getInt("id"),
                        resultSet.getInt("owner_id"),
                        resultSet.getString("scale"),
                        resultSet.getTimestamp("work_time"),
                        resultSet.getString("work_object"),
                        false);
                    RequestManager.ChangeStatus(resultSet.getInt("id"), true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return request;
    }

    public static boolean SetBrigadeID(int id, int requestId) {
        boolean result = false;

        Connection connection = null;
        try {
            connection = DBPoolCache.getInstance().getConnection();
            String prequery = "SELECT DISTINCT request.id FROM request" +
                    " INNER JOIN work_list ON request_id=request.id WHERE  request.work_status=FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(prequery);
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()) {
                if (!idBusy.contains(set.getInt("id")))
                    idBusy.add(set.getInt("id"));
            }

            String sql = "UPDATE lab3oop.work_list SET work_list.brigade_id = ? WHERE lab3oop.request.id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
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

    public static boolean EndWork(int id) {
        return WorkList.DeleteFromList(id);
    }

}
