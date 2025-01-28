package io.github.mateuszuran.offernest.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersService {
    // TO JEST TYLKO DO TESTOW

    private static final List<String> allLinks = Collections.synchronizedList(new ArrayList<>());

    public static List<String> gatherLinks(String link) {
        allLinks.add(link);
        return new ArrayList<>(allLinks); // Zwraca kopię, aby uniknąć problemów z synchronizacją
    }

    public static List<String> getLinks() {
        return new ArrayList<>(allLinks); // Zwraca kopię
    }
}
