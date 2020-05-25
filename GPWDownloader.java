import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GPWDownloader {
    static final int minimal_char_count_in_csv = 3;

    public static String getGpwData(String date) {
        String html = "https://www.gpw.pl/archiwum-notowan?fetch=0&type=10&instrument=&date=" + date;
        String return_string = "";
        try {
            Document doc = Jsoup.connect(html).get();
            Elements tableElements = doc.select("table");

            Elements tableHeaderEles = tableElements.select("thead tr th");
            int headerSize = tableHeaderEles.size();
            for (int i = 0; i < tableHeaderEles.size(); i++) {
                return_string += tableHeaderEles.get(i).text();
                return_string += ";";
            }
            return_string += "\n";

            Elements tableRowElements = tableElements.select(":not(thead) tr");

            for (int i = 0; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                Elements rowItems = row.select("td");
                if (headerSize == rowItems.size()) {
                    for (int j = 0; j < rowItems.size(); j++) {
                        return_string += rowItems.get(j).text().replace(',', '.').replace(" ", "");
                        return_string += ";";
                    }
                    return_string += "\n";
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return return_string;
    }


    public static File createFile(String name) {
        File myObj = null;
        try {
            myObj = new File(name);
            if (myObj.createNewFile()) {

            } else {
                System.out.println("File already exists.");
            }
        } catch (

        IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return myObj;
    }

    static void writeToFile(String filename, String data) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(data);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void download(String s, String en) {

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //String s = "2005-05-01";
        //String en = "2020-05-23";
        LocalDate start = LocalDate.parse(s);
        LocalDate end = LocalDate.parse(en);

        while (!start.isAfter(end)) {
            String csv = getGpwData(start.format(formater));
            if (csv.length() > minimal_char_count_in_csv) {
                String filename = "gpw/" + start + ".csv";
                createFile(filename);
                writeToFile(filename, csv);
                System.out.println("created data for: " + start.format(formater));
            } else {
                System.out.println("no data for: " + start.format(formater));
            }
            start = start.plusDays(1);
        }
    }
}