import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {

    public static List<GPWIndex> read(String filename) {
        String[] filename_path = filename.split("/");
        String file = filename_path[filename_path.length - 1];
        String dateFromFilename = file.replaceFirst("[.][^.]+$", "");

        String line = "";
        String cvsSplitBy = ";";

        List<GPWIndex> lista = new ArrayList<GPWIndex>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = line = br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                GPWIndex gpw = new GPWIndex(values, dateFromFilename);
                lista.add(gpw);          
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}