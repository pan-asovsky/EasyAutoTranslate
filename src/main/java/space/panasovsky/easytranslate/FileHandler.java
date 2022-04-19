package space.panasovsky.easytranslate;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FileHandler.class);
    private static final File folder = new File("/home/pan-asovskiy/code/in");

    private final Pattern START = Pattern.compile("\\w.+=");
    private final Pattern END = Pattern.compile("=[a-zA-Zа-яА-Я].+");
    private final Pattern RESPONSE = Pattern.compile(":\"[а-яА-Я].+\"");


    public List<String> getKeysList() {return processBuffer(parseBuffer(readFile(), START));}

    public List<String> getValuesList() {
        return processBuffer(parseBuffer(readFile(), END));
    }

    public String parseResponse(final String text) {

        String result = "";
        final Matcher m = RESPONSE.matcher(text);
        while (m.find()) result = text.substring(m.start() + 2, m.end() - 11);
        return result;
    }


    private List<String> processBuffer(final List<String> list) {

        final String copy = "(Automatic Copy)";
        final String translated = "(Automatic Translation)";

        final List<String> result = new ArrayList<>();
        for (String s : list) {
            String fs = s;
            if (s.contains(copy)) fs = s.replace(copy, "");
            else if (s.contains(translated)) fs = s.replace(translated, "");
            result.add(fs.replace("=", ""));
        }
        return result;
    }

    private List<String> parseBuffer(final char[] buffer, final Pattern pattern) {

        final List<String> result = new ArrayList<>();
        final String text = String.valueOf(buffer);

        final Matcher m = pattern.matcher(text);
        while (m.find()) result.add(text.substring(m.start(), m.end()));

        return result;
    }


    private char[] readFile() {

        final File file = getInputFile();
        char[] buffer;

        try (final FileReader reader = new FileReader(file)) {
            buffer = new char[8192*24];
            int c;
            while ((c = reader.read(buffer)) > 0) {
                if (c < 8192) buffer = Arrays.copyOf(buffer, c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buffer;
    }

    private File getInputFile() {
        return new File("/home/pan-asovskiy/code/in", "Language_ru.properties");
    }

    protected static void save(final String str) {

        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true));
            writer.append(str);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("IO" + e);
        }
    }

    private static File getFile() {
        return new File(folder, "result.properties");
    }

}