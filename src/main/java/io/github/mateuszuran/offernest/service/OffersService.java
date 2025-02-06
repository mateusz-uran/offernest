package io.github.mateuszuran.offernest.service;

import io.github.mateuszuran.offernest.service.logic.PersistData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersService {

    private final List<String> allLinks = Collections.synchronizedList(new ArrayList<>());

    public static List<String> getAllOffers(String resumePath) {
        return PersistData.getAllOffers(resumePath);
    }

    public void gatherLinks(String link) {
        allLinks.add(link);
    }

    public List<String> getLinks() {
        return allLinks;
    }

    public boolean checkListSize() {
        return allLinks.isEmpty();
    }

    public void removeLink(String link) {
        allLinks.remove(link);
    }
}
