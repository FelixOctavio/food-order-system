import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Application {
    int userMode;
    int orderanKe = 1;
    String nama;

    List<String[]> foods = new ArrayList<>();
    List<String[]> orders = new ArrayList<>();
    List<Integer> indexMana = new ArrayList<Integer>();

    static <U> void noUser(U user) {
        System.out.println("User " + user + " not found!");
    }
    public void login() {
        System.out.println("Login ");
        System.out.println("1. Customer");
        System.out.println("2. Staff");

        int menu = -99;  // Read user input
        while (menu != 0) {
            Scanner input = new Scanner(System.in);
            System.out.print("Input : ");
            try {
                menu = input.nextInt();
                input.nextLine(); //Flush input
                switch (menu) {
                    case 1:
                        do {
                            System.out.print("Your name: ");
                            nama = input.nextLine();
                            User user = new User(nama, "keqing");
                            userMode = user.login();
                            if (userMode == 5) {
                                showMenu();
                                break;
                            } else System.out.println("Wrong Password");
                        } while (true);
                        break;
                    case 2:
                        do {
                            System.out.print("User: ");
                            String userInput = input.nextLine();
                            System.out.print("Password: ");
                            String passwordInput = input.nextLine();
                            User user = new User(userInput, passwordInput);
                            userMode = user.login();
                            if (userMode == 1) {
                                System.out.println("Menu:");
                                System.out.println("1. Terima Pesanan");
                                System.out.println("2. (Chef)");
                                System.out.println("3. Finansial");
                                System.out.println("4. Edit Menu");
                                System.out.println("5. Logout\n");

                                menu = -99;  // Read user input
                                while(menu != 0){
                                    System.out.print("Input : ");
                                    try{
                                        menu = input.nextInt();
                                        switch(menu) {
                                            case 1:
                                                waiter();
                                                break;
                                            case 2:
                                                chef();
                                                break;
                                            case 3:
                                                finance();
                                                break;
                                            case 4:
                                                showMenu();
                                                break;
                                            default:
                                                System.out.println("Menu doesn't exists!");
                                                break;
                                        }
                                    }
                                    catch(InputMismatchException e){
                                        System.out.println("Input must be number!");
                                        input.nextLine();
                                    }
                                }
                                break;
                            } else if (userMode == 0) System.out.println("Wrong Password!");
                            else if (userMode == 6) noUser(userInput);
                        } while (true);
                        break;
                    default:
                        System.out.println("Menu doesn't exists!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be number!");
                input.nextLine();
            }
        }
    }

    public void showMenu() {
            FoodFile fileop = new FoodFile(userMode);
            foods = fileop.read();

            System.out.println("Menu:");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            if (userMode == 5) {
                System.out.println("3. Pay");
                System.out.println("4. Logout\n");
            }

            else System.out.println("3. Logout\n");

            Scanner input = new Scanner(System.in);
            int menu = -99;  // Read user input
            while(menu != 0){
                System.out.print("Input : ");
                try{
                    menu = input.nextInt();
                    switch(menu) {
                        case 1:
                            foodMenu(1);
                            break;
                        case 2:
                            foodMenu(2);
                            break;
                        case 3:
                            if (userMode == 5) bayar();
                            else login();
                            break;
                        case 4:
                            if (userMode == 5) {
                                if (orders.size() > 0) {
                                    char option = ' ';
                                    while (!(option == 'N')){
                                        System.out.println("Do you want to cancel your order?(Y/N) : ");
                                        String optionS = input.nextLine();
                                        if (optionS.length() >= 1) option = Character.toUpperCase(optionS.charAt(0));
                                        if (option == 'Y') {
                                            orders.clear();
                                            login();
                                        }
                                        if (!(option == 'N')) System.out.println("Input invalid!");
                                    }
                                } else {
                                    login();
                                }

                                break;
                            }
                        default:
                            System.out.println("Menu doesn't exists!");
                            break;
                    }
                }
                catch(InputMismatchException e){
                    System.out.println("Input must be number!");
                    input.nextLine();
                }
            }
    }

    public void bayar() {
        if (orders.size() == 0) {
            System.out.println("No order.");
            System.out.println("Please pick your desired menu to be ordered");
            showMenu();
        }
        Scanner input = new Scanner(System.in);
        int total = 0;
        for (int i=0; i<orders.size(); i++) {
            System.out.print((i+1) + ". ");
            System.out.print(orders.get(i)[3]);
            System.out.print(" (Rp" + orders.get(i)[4] + ")  ");
            System.out.println(orders.get(i)[5] + "   " + (Integer.valueOf(orders.get(i)[4]) * Integer.valueOf(orders.get(i)[5])));
            total += (Integer.valueOf(orders.get(i)[4]) * Integer.valueOf(orders.get(i)[5]));
        }
        System.out.println("Total: Rp" + total);

        System.out.println(""); //Buat newline
        System.out.println("Payment method:");
        System.out.println("1. Gopay");
        System.out.println("2. Debit");
        System.out.println("3. Cash");
        System.out.println("4. Cancel / Back to main menu");
        System.out.print("Pick your desired payment method: ");

        try{
            int menu = input.nextInt();
            if ((menu > 4 || menu < 1)) System.out.println("Payment method doesn't exists!");
            else if (menu == 4) showMenu();
            else {
                OrderFile fileop = new OrderFile(userMode);
                fileop.write(orders, 0);
                orderanKe++;
                System.out.println("Payment Successful!");
            }
        }
        catch(InputMismatchException e){
            System.out.println("Input must be number!");
            input.nextLine();
        }
        orders.clear();
        System.out.println("Thank you for your order. Please wait approx. 15 minutes");
        login();
    }

    public void foodMenu(int jenis) {
        FoodFile fileop = new FoodFile(userMode);
        fileop.write(foods, 0);

        Scanner input = new Scanner(System.in);
        int menu = -99;  // Read user input

        indexMana.clear();
        int i, index = 0;
        for (i=0, index=0; i<foods.size(); i++) {
            if (jenis == Integer.valueOf(foods.get(i)[0])) {
                indexMana.add(i);
                System.out.println((++index) + ". " + foods.get(i)[1] + " (Price Rp" + foods.get(i)[2] + ")");
            }
        }
        if (userMode == 1) {
            System.out.println((index + 1) + ". Edit");
            System.out.println((index + 2) + ". Add");
            System.out.println((index + 3) + ". Remove");
            System.out.println((index + 4) + ". Back");
        } else {
            if (index == 0) System.out.println("Please ask waiter for the menu");
            System.out.println((index + 1) + ". Back");
        }

        System.out.println(""); //Agar ada spasi setelah tampilan list menu
        while(menu != 0){
            System.out.print("Input : ");
            try{
                menu = input.nextInt();
                if (userMode == 1) {
                    if (menu == (index + 1)) { // Edit
                        input.nextLine(); //Flush stdin
//                        if (index == 1) {
//                            System.out.println("Tidak ada menu yang bisa diedit! Silahkan tambah menu terlebih dahulu");
//                            foodMenu(jenis);
//                            break;
//                        }

                        while (true) {
                            System.out.print("Pick menu that want to be edited: ");
                            try {
                                menu = input.nextInt();
                                int temp = menu - 1;
                                index = indexMana.get(temp);
                                if (menu > index || menu < 0) System.out.println("Input invalid!");
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Input must be number!");
                                input.nextLine();
                            }
                        }

                        System.out.println(foods.get(index)[1] + " (Price Rp" + foods.get(index)[2] + ")");
                        System.out.println("1. Menu Name");
                        System.out.println("2. Price");
                        String[] menuBaru = new String[3];
                        menuBaru[0] = String.valueOf(jenis);

                        while (true) {
                            System.out.print("What do you want to edit?: ");
                            try{
                                menu = input.nextInt();
                                if (menu < 1 || menu > 2) System.out.println("Input invalid!");
                                menuBaru[2] = foods.get(index)[2];
                                if (menu == 1) {
                                    System.out.print("Input new name: ");
                                    try {
                                        input.nextLine();
                                        menuBaru[1] = input.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Input must be string!");
                                        input.nextLine();
                                    }

                                    System.out.println(foods.get(index)[1] + " renamed to " + menuBaru[1]);
                                    foods.set(index, menuBaru);
                                    break;
                                } else if (menu == 2){
                                    System.out.print("Input new price: ");
                                    try {
                                        input.nextLine();
                                        int harga = input.nextInt();
                                        if (harga < 0) System.out.println("Price can't be negative!");
                                        else menuBaru[2] = String.valueOf(harga);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Input must be number!");
                                        input.nextLine();
                                    }
                                    menuBaru[1] = foods.get(index)[1];
                                    System.out.println("Price updated to " + menuBaru[2]);
                                    foods.set(index, menuBaru);
                                    break;
                                }
                            } catch(InputMismatchException e){
                                System.out.println("Input must be number!");
                                input.nextLine();
                            }
                        }
                        foodMenu(jenis);
                    } else if (menu == (index + 2)) { // Tambah
                        input.nextLine(); //Flush stdin
                        String[] menuInput = new String[3];
                        menuInput[0] = String.valueOf(jenis);

                        while (true) {
                            try {
                                System.out.print("Menu name: ");
                                menuInput[1] = input.nextLine();
                                break;
                            } catch (Exception E) {
                                System.out.println("Input must be string!");
                                input.nextLine();
                            }
                        }

                        while (true) {
                            System.out.print("Price: ");
                            try {
                                int harga = input.nextInt();
                                if (harga < 0) System.out.println("Price can't be negative!");
                                else {
                                    menuInput[2] = String.valueOf(harga);
                                    break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Input must be number!");
                                input.nextLine();
                            }
                        }

                        foods.add(menuInput);
                        foodMenu(jenis);
                    } else if (menu == (index + 3)) { // Hapus
                        input.nextLine(); //Flush stdin
//                        if (foods.size() == 0) {
//                            System.out.println("Tidak ada menu yang bisa dihapus! Silahkan tambah menu terlebih dahulu");
//                            foodMenu(jenis);
//                        }
                        while (true) {
                            try {
                                System.out.print("Menu that want to be deleted: ");
                                menu = input.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Input must be number");
                                input.nextLine();
                            }
                        }

                        int temp = menu - 1;
                        int hapus = indexMana.get(temp);
                        foods.remove(hapus);
                        foodMenu(jenis);
                    } else if (menu == (index + 4)) showMenu(); //Kembali
                    else if ((menu > (index + 2) || menu < 1)) System.out.println("Menu not found!");
                } else { //Jika user adalah customer
                    if (menu == (index + 1)) showMenu(); //Kembali
                    else if ((menu > (index + 1) || menu < 1)) System.out.println("Menu not found!");
                    else {
                        int jumlah =-1;
                        input.nextLine();
                        while (jumlah < 0) {
                            try {
                                System.out.print("How much do you want to order?: ");
                                jumlah = input.nextInt();
                                if (jumlah < 0) System.out.println("Can't order less than 0!");
                                else break;
                            } catch (InputMismatchException e) {
                                System.out.println("Input must be number!");
                                input.nextLine();
                            }
                        }

                        int temp = indexMana.get(menu - 1);
                        if (jumlah > 0) {
                            String[] orderan = new String[8];
                            orderan[0] = String.valueOf(orderanKe);
                            orderan[1] = String.valueOf(nama);
                            orderan[2] = String.valueOf(jenis);
                            orderan[3] = foods.get(temp)[1];
                            orderan[4] = foods.get(temp)[2];
                            orderan[5] = String.valueOf(jumlah);
                            orderan[6] = "Order diterima";
                            orders.add(orderan);
                            System.out.println(foods.get(temp)[1] + " added to cart");
                        } else System.out.println("Back to main menu");
                        foodMenu(jenis);
                    }
                }
            }
            catch(InputMismatchException e){
                System.out.println("Input must be number!");
                input.nextLine();
            }
        }
    }

    public void waiter() {
        FoodFile fileop = new FoodFile(userMode);
        OrderFile fileor = new OrderFile(userMode);
        foods = fileop.read();
        orders = fileor.read();
        while (true) {
            int temp = 0;
            int tempIndex = 1;
            boolean kosong = true;
            List<Integer> indexPesanan = new ArrayList<Integer>();

            System.out.println("Ready to serve:");
            for(int i=0; i<orders.size(); i++) {
                if (orders.get(i)[6].equals("Siap disajikan")) {
                    indexPesanan.add(i);
                    if (temp != Integer.valueOf(orders.get(i)[0])) {
                        kosong = false;
                        temp = Integer.valueOf(orders.get(i)[0]);
                        System.out.println(tempIndex++ + ". Order Number: " + orders.get(i)[0]);
                        System.out.println("\tOrder: ");
                    }
                    System.out.println("\t\t- " + orders.get(i)[4] + " Quantity: " + orders.get(i)[5]);
                }
            }

            if (kosong) System.out.println(" Empty");
            Scanner input = new Scanner(System.in);

            while (true) {
                if (!(kosong)) System.out.print("Which order number do you want to take? (0 to logout): ");
                else System.out.print("Type 0 to logout: ");
                try {
                    temp = input.nextInt();
                    if (temp == 0) login();

                    else if (temp < 0 || temp > tempIndex - 1) System.out.println("Order with that index is not found!");
                    else break;
                } catch (InputMismatchException e) {
                    System.out.println("Input must be number!");
                    input.nextLine();
                }
            }

            temp--;
            int temp2 = indexPesanan.get(temp);
            String[] temporary = new String[7];
            for (int i=temp2; i<orders.size(); i++) {
                if (orders.get(i)[1] == orders.get(temp2)[1]) {
                    temporary[0] = orders.get(i)[0];
                    temporary[1] = orders.get(i)[1];
                    temporary[2] = orders.get(i)[2];
                    temporary[3] = orders.get(i)[3];
                    temporary[4] = orders.get(i)[4];
                    temporary[5] = orders.get(i)[5];
                    temporary[6] = "Selesai";
                    orders.set(i, temporary);
                    fileor.write(orders, 1);
                }
            }
        }
    }
    public void chef() {
        while (true) {
            FoodFile fileop = new FoodFile(userMode);
            OrderFile fileor = new OrderFile(userMode);
            foods = fileop.read();
            orders = fileor.read();
            int temp = 0;
            int tempIndex = 1;
            boolean kosong = true;
            List<Integer> indexPesanan = new ArrayList<Integer>();

            System.out.println("Ordered food list:");
            for(int i=0; i<orders.size(); i++) {
                if (orders.get(i)[6].equals("Order diterima")) {
                    kosong = false;
                    indexPesanan.add(i);
                    System.out.println(tempIndex++ + ". " + orders.get(i)[3] + " Sebanyak " + orders.get(i)[5]);
                }
            }
            if (kosong) System.out.println("Empty");
            Scanner input = new Scanner(System.in);

            while (true) {
                if (!(kosong)) System.out.print("Which order are ready to serve? (Type 0 to logout): ");
                else System.out.print("Type 0 to logout: ");
                try {
                    temp = input.nextInt();
                    if (temp == 0) login();
                    else if (temp < 0 || temp > tempIndex - 1) System.out.println("Order with that index is not found!");
                    else break;
                } catch (InputMismatchException e) {
                    System.out.println("Input must be number!");
                    input.nextLine();
                }
            }

            temp--;
            temp = indexPesanan.get(temp);
            String[] temporary = new String[8];
            temporary[0] = orders.get(temp)[0];
            temporary[1] = orders.get(temp)[1];
            temporary[2] = orders.get(temp)[2];
            temporary[3] = orders.get(temp)[3];
            temporary[4] = orders.get(temp)[4];
            temporary[5] = orders.get(temp)[5];
            temporary[6] = "Siap disajikan";
            orders.set(temp, temporary);
            fileor.write(orders, 1);
        }
    }

    public void finance() {
        OrderFile fileop = new OrderFile(userMode);
        orders = fileop.read();
        int totalPendapatan = 0;
        int temp = 1;

        for(int i=0; i<orders.size(); i++) {
            totalPendapatan += (Integer.valueOf(orders.get(i)[5]) * Integer.valueOf(orders.get(i)[6]));
        }

        System.out.println("Total income right now Rp" + totalPendapatan);
        Scanner input = new Scanner(System.in);

        while (temp != 0) {
            System.out.print("Type 0 to logout: ");
            try {
                temp = input.nextInt();
                if (temp != 0) System.out.println("Input invalid!");
            } catch (InputMismatchException e) {
                System.out.println("Input must be number!");
                input.nextLine();
            }
        }
        login();
    }

    public static void main(String[] args) {
        Application object = new Application();
        object.login();
    }
}