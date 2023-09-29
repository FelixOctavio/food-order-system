import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderFile extends FileOperation{
    File order = new File("orders.csv");

    int userMode;
    public OrderFile(int userMode) {
        this.userMode = userMode;
    }
    @Override
    public List<String[]> read() {
        List<String[]> orders = new ArrayList<>();
        try {
            if (order.createNewFile()) {
                if (userMode == 1) System.out.println("File orderan tidak ditemukan!");
            } else {
                try (BufferedReader readO = new BufferedReader(new FileReader("orders.csv"))) {
                    String line;
                    while ((line = readO.readLine()) != null) {
                        orders.add(line.split(","));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return orders;
    }

    public int orderanKe() {
        List<String[]> orders = new ArrayList<>();
        int count = 1;
        try {
            if (order.createNewFile()) {
                if (userMode == 1) System.out.println("File orderan tidak ditemukan!");
            } else {
                try (BufferedReader readO = new BufferedReader(new FileReader("orders.csv"))) {
                    String line;
                    int i = 0;
                    while ((line = readO.readLine()) != null) {
                        orders.add(line.split(","));
                        if (Integer.valueOf(orders.get(i++)[0]) == count) count++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void write(List<String[]> orders, int mode) {
        boolean append = true;
        if (mode == 0) append = true;
        else append = false;

        try {
            CSVWriter csvOrder = new CSVWriter(new FileWriter(order, append),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END);

            for (int i=0; i<orders.size(); i++) {
                csvOrder.writeNext(orders.get(i));
            }
            csvOrder.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
