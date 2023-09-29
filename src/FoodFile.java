import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FoodFile extends FileOperation {
    File food = new File("foods.csv");
    int userMode;
    public FoodFile(int userMode) {
        this.userMode = userMode;
    }

    @Override
    public List<String[]> read() {
        List<String[]> foods = new ArrayList<>();
        try {
            if (food.createNewFile()) {
                if (userMode == 1) System.out.println("File menu makanan tidak ditemukan!");
            } else {
                try (BufferedReader readF = new BufferedReader(new FileReader("foods.csv"))) {
                    String line;
                    while ((line = readF.readLine()) != null) {
                        foods.add(line.split(","));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return foods;
    }

    @Override
    public void write(List<String[]> foods, int mode) {
        try {
            CSVWriter csvFood = new CSVWriter(new FileWriter(food),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END);

            for (int i=0; i<foods.size(); i++) {
                csvFood.writeNext(foods.get(i));
            }
            csvFood.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
