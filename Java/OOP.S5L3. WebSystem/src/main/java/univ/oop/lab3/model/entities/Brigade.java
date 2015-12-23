package univ.oop.lab3.model.entities;

import univ.oop.lab3.model.database.DBPoolCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by flystyle on 20.12.15.
 */
public class Brigade {

    private int id;
    private String name;
    private static int currentId = 0;

    public Brigade(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public static boolean AddBrigade(String brigadeName) {
        boolean result = false;
        Connection connect = null;

        try {
            connect = DBPoolCache.getInstance().getConnection();
            String getMax = "SELECT MAX(id) FROM lab3oop.work_list";
            PreparedStatement st = connect.prepareStatement(getMax);

            ResultSet set = st.executeQuery();
            if(set.next())
                currentId = set.getInt(1) + 1;

            String sql = "INSERT INTO lab3oop.brigade (id, name) VALUES (?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, currentId);
            statement.setString(2, brigadeName);
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
