package univ.oop.lab3.model.entities;

import univ.oop.lab3.model.database.DBPoolCache;

import java.sql.*;

/**
 * Created by flystyle on 19.12.15.
 */
public class Request {
    protected int id, ownerID;
    private String workObject, scale;
    private Timestamp time;
    private boolean workStatus;

    public static int currentId = 0;

    public Request(String workObject, Timestamp time, String scale, int ownerID) {
        this.workObject = workObject;
        this.time = time;
        this.scale = scale;
        this.ownerID = ownerID;

        workStatus = false;
    }

    public Request(int id, int ownerID, String scale, Timestamp time, String workObject, boolean workStatus) {
        this.id = id;
        this.ownerID = ownerID;
        this.scale = scale;
        this.time = time;
        this.workObject = workObject;
        this.workStatus = workStatus;
    }

    public static boolean AddRequest(Request newRequest) throws SQLException {
        Connection connect = null;
        boolean result = false;

        connect = DBPoolCache.getInstance().getConnection();
        String getMax = "SELECT MAX(id) FROM lab3oop.request";
        PreparedStatement st = connect.prepareStatement(getMax);

        ResultSet set = st.executeQuery();
        if(set.next())
             currentId = set.getInt(1) + 1;

        String sql = "INSERT INTO lab3oop.request (id, work_object, scale, work_time, work_status, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setInt(1, currentId);
        statement.setString(2, newRequest.getWorkObject());
        statement.setString(3, newRequest.getScale());
        statement.setTimestamp(4, newRequest.getTime());
        statement.setBoolean(5, newRequest.isWorkStatus());
        statement.setInt(6, newRequest.getOwnerID());

        statement.executeUpdate();
        result = true;


        return result;
    }
    public int getOwnerID() {
        return ownerID;
    }

    public String getScale() {
        return scale;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getWorkObject() {
        return workObject;
    }

    public boolean isWorkStatus() {
        return workStatus;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setWorkObject(String workObject) {
        this.workObject = workObject;
    }

    public int getId() {
        return id;
    }
}
