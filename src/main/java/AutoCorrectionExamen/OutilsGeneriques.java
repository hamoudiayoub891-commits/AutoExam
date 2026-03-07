package AutoCorrectionExamen;

import java.util.Collections;
import java.util.List;

public class OutilsGeneriques {

    public static <T> void melangerListe(List<T> liste) {

        if (liste != null && !liste.isEmpty()) {

            Collections.shuffle(liste);
        }
    }
}