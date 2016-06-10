package pro.alexfly.lab4.dao;

import pro.alexfly.lab4.model.Request;
import pro.alexfly.lab4.model.Station;
import pro.alexfly.lab4.model.User;

import java.sql.Date;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 05.06.16.
 */
public interface IRequestDAO {
    Integer createRequest(final Request source);
    List<Request> list(final Date date);
    List<Request> list();
    List<Request> list(final Station begin, final Station end);
    Request getRequestById (final int id);
    List<Request> getRequestsByUserId (final int userId);

    void removeRequestById(final int id);
    void removeRequestByUser(final User user);

    @Deprecated
    void removeAllRequests();

}
