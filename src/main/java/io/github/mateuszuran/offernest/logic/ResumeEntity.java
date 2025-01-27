package main.java.io.github.mateuszuran.offernest.logic;

import java.util.List;

public class ResumeEntity {
    private final String resumePath;
    private final List<String> offers;

    public ResumeEntity(String resumePath, List<String> offers) {
        this.resumePath = resumePath;
        this.offers = offers;
    }

    public String getResumePath() {
        return resumePath;
    }

    public List<String> getOffers() {
        return offers;
    }

    @Override
    public String toString() {
        return "ResumeEntity{ ResumePath: " + getResumePath() + " for offers: " + offers + " }";
    }
}
