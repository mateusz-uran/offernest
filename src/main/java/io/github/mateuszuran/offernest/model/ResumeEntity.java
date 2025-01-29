package io.github.mateuszuran.offernest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResumeEntity {
    private final String resumePath;
    private List<String> offers;

    @JsonCreator
    public ResumeEntity(@JsonProperty("resumePath") String resumePath,
                        @JsonProperty("offers") List<String> offers) {
        this.resumePath = resumePath;
        this.offers = offers;
    }

    public String getResumePath() {
        return resumePath;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "ResumeEntity{ ResumePath: " + getResumePath() + " for offers: " + offers + " }";
    }
}
