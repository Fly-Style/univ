package pro.alexfly.lab4.model;

import pro.alexfly.lab4.dao.TrainDAO;
import pro.alexfly.lab4.dao.UserDAO;

import java.util.Collections;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 07.06.16.
 */
public class AcceptedRequest {
    Train train;
    Request request;
    User user;

    public AcceptedRequest(Request request, Train train, User user) {
        this.request = request;
        this.train = train;
        this.user = user;
    }

    public AcceptedRequest(Request request) {
        this.request = request;
        acceptRequest();
    }

    private void acceptRequest() {
        TrainDAO trainDAO = new TrainDAO();
        UserDAO userDAO = new UserDAO();
        List<Train> trains = trainDAO.list(request.getStationBegin().getName(), request.getStationEnd().getName());
        Collections.sort(trains);
        train = trains.get(0);
        user = userDAO.getUserById(request.getUser().getId());
    }
}
