import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<GPWIndex> csvData = new LinkedList<GPWIndex>();

    public static void main(String[] args) {
        String start_string = "2015-05-01";
        String end_string = "2020-05-23";

        LocalDate start = LocalDate.parse(start_string);
        LocalDate end = LocalDate.parse(end_string);

        new File("gpw").mkdirs();
        GPWDownloader.download(start_string, end_string);
        loadAllData();
        System.out.println(csvData.size());

        List<GPWIndex> uniqueGPW = csvData.stream().distinct().sorted().collect(Collectors.toList());

        for (int i = 0; i < uniqueGPW.size(); i++) {
            final int x = i;
            double avrg = csvData.stream().filter(gpw -> gpw.nazwa.equals(uniqueGPW.get(x).nazwa)).mapToDouble(GPWIndex::getOpenValue).average().orElse(Double.NaN);
            System.out.println("Avrg open value for " + uniqueGPW.get(i).nazwa + " is: " + avrg);
        }

    }

    public static void loadAllData() {
        try (Stream<Path> walk = Files.walk(Paths.get("gpw/"))) {

            List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".csv")).collect(Collectors.toList());
            result.forEach((n) -> makeIndex(n));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeIndex(String n) {
        csvData.addAll(CSVReader.read(n));
    }

}