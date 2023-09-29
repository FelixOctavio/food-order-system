import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkFile extends FileOperation {
    File drink = new File("drinks.csv");
    int userMode;
    public DrinkFile(int userMode) {
        this.userMode = userMode;
    }

    @Override
    public List<String[]> read() {
        List<String[]> drinks = new ArrayList<>();
        try {
            if (drink.createNewFile()) {
                if (userMode == 1) System.out.println("File menu makanan tidak ditemukan!");
            } else {
                try (BufferedReader readF = new BufferedReader(new FileReader("drinks.csv"))) {
                    String line;
                    while ((line = readF.readLine()) != null) {
                        drinks.add(line.split(","));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return drinks;
    }

    @Override
    public void write(List<String[]> drinks, int mode) {
        try {
            CSVWriter csvdrink = new CSVWriter(new FileWriter(drink),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END);

            for (int i=0; i<drinks.size(); i++) {
                csvdrink.writeNext(drinks.get(i));
            }
            csvdrink.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
