package io.github.mateuszuran.offernest.v2.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResumeEntity {
    private String note;
    private final String pdfPath;
    private List<String> offers;
    private final List<Observer> observers = new ArrayList<>();

    @JsonCreator
    public ResumeEntity(@JsonProperty("note") String note,
                        @JsonProperty("pdfPath") String pdfPath,
                        @JsonProperty("offers") List<String> offers) {
        this.note = note;
        this.pdfPath = pdfPath;
        this.offers = offers;
    }

    public ResumeEntity(String pdfPath, List<String> offers) {
        this.pdfPath = pdfPath;
        this.offers = offers;
    }

    public String getNote() {
        return note;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
        notifyObservers();
    }


    public List<String> getOffers() {
        return offers;
    }

    public void addOffer(String offer) {
        offers.add(offer);
        notifyObservers();
    }

    public void removeOffer(String offer) {
        offers.remove(offer);
        notifyObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public String toString() {
        return "ResumeEntity{" +
                "note='" + note + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", offers=" + offers +
                '}';
    }
}
