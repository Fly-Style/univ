package pro.alexfly.lab4.dao;

import pro.alexfly.lab4.model.Train;
import pro.alexfly.lab4.model.Way;

import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
public interface ITrainDAO {
    Train getTrainById(int id);
    List list();
    List list(String beg, String  end);
    Way getWay(int trainId);
    List getWays();
}
