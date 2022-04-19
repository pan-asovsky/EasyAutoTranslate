package space.panasovsky.easytranslate;

import java.util.List;
import java.util.ArrayList;


public class RunApplication {

    public static void main(String[] args) {
        process();
    }

    private static void process() {

        final FileHandler f = new FileHandler();

        final List<String> keys = f.getKeysList();
        final List<String> values = f.getValuesList();

        final List<String> translated = new ArrayList<>(values.size());
        for (String v : values) translated.add(TranslateConnector.getTranslatedString(v));

        final List<String> result = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) result.add(keys.get(i) + "=" + translated.get(i));

        for (String s : result) FileHandler.save(s);
    }

}