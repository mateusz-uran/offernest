package io.github.mateuszuran.offernest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersService {

    private final List<String> allLinks = Collections.synchronizedList(new ArrayList<>());

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
