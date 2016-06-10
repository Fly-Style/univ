package pro.alexfly.lab4.controller;

import AOP.SystemAction;
import pro.alexfly.lab4.dao.IRequestDAO;
import pro.alexfly.lab4.dao.IStationDAO;
import pro.alexfly.lab4.dao.ITrainDAO;
import pro.alexfly.lab4.model.Request;
import pro.alexfly.lab4.model.Train;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Date;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 06.06.16.
 */
@ManagedBean(name = "rails_main")
@SessionScoped
public class MainPageController implements SystemAction {

    @EJB
    private IRequestDAO requestDAO;
    @EJB
    private IStationDAO stationDAO;
    @EJB
    private ITrainDAO trainDAO;

    private Date date;
    private String begin, end;

    private List<Train> listOfTrains;

    public String  makeRequest(int userId) {
        Request request = new Request();
        if (date != null && begin != null && end != null) {
            request.setDay(date);
            request.setStationBegin(stationDAO.getStationByName(begin));
            request.setStationEnd(stationDAO.getStationByName(end));
            if (requestDAO.createRequest(request) > 0)
                return "sucess_request";
            else return "main";
        }
        return "main";
    }


    public void showAllTrains() {
        listOfTrains.clear();
        listOfTrains = trainDAO.list();
    }

    public void showTrains() {
        listOfTrains.clear();
        listOfTrains = trainDAO.list(begin, end);
    }

    public List<Train> getListOfTrains() {
        return listOfTrains;
    }

    public void setListOfTrains(List<Train> listOfTrains) {
        this.listOfTrains = listOfTrains;
    }


    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
