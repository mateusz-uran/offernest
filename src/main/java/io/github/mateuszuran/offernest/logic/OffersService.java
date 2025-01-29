package io.github.mateuszuran.offernest.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersService {

    private static final List<String> allLinks = Collections.synchronizedList(new ArrayList<>());

    public static List<String> gatherLinks(String link) {
        allLinks.add(link);
        return new ArrayList<>(allLinks);
    }

    public static List<String> getLinks() {
        return new ArrayList<>(allLinks);
    }

    public static boolean checkListSize() {
        return allLinks.isEmpty();
    }
}
