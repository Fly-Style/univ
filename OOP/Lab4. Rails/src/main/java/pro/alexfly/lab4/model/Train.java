package pro.alexfly.lab4.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */

@Entity
@Table(name = "rails_train")
public class Train implements Serializable, Comparable<Train>, Comparator<Train> {
    private int places;
    private int hoursInVoyage;
    private int id;
    private Time beginVoyage;
    private Time endVoyage;

    @Id
    @Column(name = "train_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "train_hours", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getHoursInVoyage() {
        return hoursInVoyage;
    }

    public void setHoursInVoyage(int hoursInVoyage) {
        this.hoursInVoyage = hoursInVoyage;
    }


    @Column(name = "train_places", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Column(name = "train_begin_time")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Time getBeginVoyage() {
        return beginVoyage;
    }

    public void setBeginVoyage(Time beginVoyage) {
        this.beginVoyage = beginVoyage;
    }

    @Column(name = "train_end_time")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Time getEndVoyage() {
        return endVoyage;
    }

    public void setEndVoyage(Time endVoyage) {
        this.endVoyage = endVoyage;
    }

    public int compareTo(Train o) {
        if (this.beginVoyage.compareTo(o.beginVoyage) < 0)
            return -1;
        else if (this.beginVoyage.compareTo(o.beginVoyage) > 0)
            return 1;
        else return 0;
    }

    public int compare(Train o1, Train o2) {
        if (o1.beginVoyage.compareTo(o2.beginVoyage) < 0)
            return -1;
        else if (o1.beginVoyage.compareTo(o2.beginVoyage) > 0)
            return 1;
        else return 0;
    }

    public Comparator<Train> reversed() {
        return null;
    }

    public Comparator<Train> thenComparing(Comparator<? super Train> other) {
        return null;
    }

    public <U> Comparator<Train> thenComparing(Function<? super Train, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    public <U extends Comparable<? super U>> Comparator<Train> thenComparing(Function<? super Train, ? extends U> keyExtractor) {
        return null;
    }

    public Comparator<Train> thenComparingInt(ToIntFunction<? super Train> keyExtractor) {
        return null;
    }

    public Comparator<Train> thenComparingLong(ToLongFunction<? super Train> keyExtractor) {
        return null;
    }

    public Comparator<Train> thenComparingDouble(ToDoubleFunction<? super Train> keyExtractor) {
        return null;
    }
}
