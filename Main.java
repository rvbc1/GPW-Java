import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<GPWIndex> csvData = new LinkedList<GPWIndex>();

    public static void main(String[] args) {
        String start_string = "2005-05-01";
        String end_string = "2020-05-23";

        LocalDate start = LocalDate.parse(start_string);
        LocalDate end = LocalDate.parse(end_string);

        // GPWDownloader.download(start_string, end_string);
        loadAllData();
        System.out.println(csvData.size());

        // for(int i = 0; i < csvData.size(); i++){
        // // for(int j = 0; j < csvData.get(i).size(); j++){
        // if(csvData.get(i).nazwa.equals(name))
        // System.out.println(i);
        // // }
        // }

        // Create the list with duplicates.
        // List<String> listAll = Arrays.asList("CO2", "CH4", "SO2", "CO2", "CH4",
        // "SO2", "CO2", "CH4", "SO2");

        // // Create a list with the distinct elements using stream.
        List<GPWIndex> uniqueGPW = csvData.stream().distinct().sorted().collect(Collectors.toList());

        String name = "ZYWIEC";
        double sum = 0.0;
        int il = 0;
        for (int i = 0; i < uniqueGPW.size(); i++) {
            final int x = i;
            double avrg = csvData.stream().filter(gpw -> gpw.nazwa.equals(uniqueGPW.get(x).nazwa)).mapToDouble(GPWIndex::getOpenValue).average().orElse(Double.NaN);
            System.out.println("Avrg open value for " + uniqueGPW.get(i).nazwa + " is: " + avrg);
        }

        // for (int i = 0; i < csvData.size(); i++) {
        //     if (csvData.get(i).nazwa.equals(name)) {
        //         sum += csvData.get(i).getOpenValue();
        //         il++;
        //         System.out.println("mamy to " + il + " | " + i);
        //     }
        // }

        //System.out.println("vale for " + name + " is: " + (sum / il) + " " + il);

        // @Override
        // public int hashCode() {
        // final int prime = 31;
        // int result = 1;
        // result = prime * result + ((name == null) ? 0 : name.hashCode());
        // long temp;
        // temp = Double.doubleToLongBits(price);
        // result = prime * result + (int) (temp ^ (temp >>> 32));
        // return result;
        // }

        // Set<GPWIndex> uniqueGas = new HashSet<GPWIndex>(csvData);
        // System.out.println("Unique gas count: " + uniqueGas.size());

        // List<String> gasList = Arrays.asList("CO2", "CH4", "SO2", "CO2", "CH4",
        // "SO2", "CO2", "CH4", "SO2");
        // Set<String> uniqueGas = new HashSet<String>(gasList);
        // System.out.println("Unique gas count: " + uniqueGas.size());

        // for(int i = 0; i < listDistinct.size(); i++){
        // System.out.println(listDistinct.get(i).nazwa);
        // }

        // // Display them to terminal using stream::collect with a build in Collector.
        // String collectAll = listAll.stream().collect(Collectors.joining(", "));
        // System.out.println(collectAll); // => CO2, CH4, SO2, CO2, CH4 etc..
        // String collectDistinct = listDistinct.stream().collect(Collectors.joining(",
        // "));
        // System.out.println(collectDistinct); // => CO2, CH4, SO2

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