package univ.oop.lab3.model.entities;

import univ.oop.lab3.exeptions.NoFoundRightScaleExeption;
import univ.oop.lab3.model.database.DBPoolCache;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by flystyle on 19.12.15.
 */
public class WorkList {

    public int requestID, brigadeID;
    public Timestamp endTime;
    private static int currentId = 0;

    private static List<WorkList> workingPlan = new ArrayList<>();

    public WorkList() {}

    public WorkList(int brigadeID, int requestID, Timestamp endTime) {
        this.brigadeID = brigadeID;
        this.requestID = requestID;
        this.endTime = endTime;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getBrigadeID() {
        return brigadeID;
    }

    public static boolean SyncWithBase() {
        Connection connect = null;
        boolean result = false;

        try {
            connect = DBPoolCache.getInstance().getConnection();

            String sql = "SELECT * FROM lab3oop.work_list";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                WorkList temp =
                        new WorkList(set.getInt("brigade_id"), set.getInt("request_id"), set.getTimestamp("end_time"));

                if(workingPlan.isEmpty())
                    workingPlan.add(temp);

                for (int i = 0; i < workingPlan.size(); i++) {

                    if (!((temp.getBrigadeID() == workingPlan.get(i).getBrigadeID()) && (temp.getRequestID() == workingPlan.get(i).getRequestID()))) {
                        workingPlan.add(temp);
                        System.out.println("+");
                    }
                }

            }
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean AddToList(Request request, int brigadeID) {
        Connection connect = null;
        boolean result = false;
        try {
            connect = DBPoolCache.getInstance().getConnection();

            Timestamp current = request.getTime();
            if (request.getScale().equals("BIG")) {
                current.setTime(current.getTime() + 21600000);      // 6 hours
            }
            else if (request.getScale().equals("MID")) {
                current.setTime(current.getTime() + 14400000);      // 4 hours
            }
            else if (request.getScale().equals("")) {
                current.setTime(current.getTime() + 7200000);
            }
            else throw new NoFoundRightScaleExeption("Wrong scale");

            String getMax = "SELECT MAX(id) FROM lab3oop.work_list";
            PreparedStatement st = connect.prepareStatement(getMax);

            ResultSet set = st.executeQuery();
            if(set.next())
                currentId = set.getInt(1) + 1;

            String sql = "INSERT INTO lab3oop.work_list (id, request_id, brigade_id, end_time) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, currentId);
            statement.setInt(2, request.getId());
            statement.setInt(3, brigadeID);
            statement.setTimestamp(4, current);
            statement.executeQuery();

            workingPlan.add(new WorkList(request.getId(), brigadeID, current));

            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoFoundRightScaleExeption noFoundRightScaleExeption) {
            noFoundRightScaleExeption.printStackTrace();
        }
        return result;
    }

    public static boolean DeleteFromList (int id) {
        Connection connect = null;

        boolean result = false;
        try {
            connect = DBPoolCache.getInstance().getConnection();
            String sql = "DELETE FROM lab3oop.work_list WHERE lab3oop.work_list.id = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeQuery();

            //workingPlan.remove(list);

            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<WorkList> GetWorkList () {
        System.out.println(workingPlan.size());
        return workingPlan;
    }


}
