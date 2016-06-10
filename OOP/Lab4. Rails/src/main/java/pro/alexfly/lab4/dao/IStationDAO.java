package pro.alexfly.lab4.dao;

import pro.alexfly.lab4.model.Station;

/**
 * @Author is flystyle
 * Created on 05.06.16.
 */
public interface IStationDAO {
    Station getStationById (final int id);
    Station getStationByName (final String name);
    void setName(Station station);
}
