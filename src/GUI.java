import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class GUI {
    //Login
    private JPanel LoginPanel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton LoginButton;

    //MainMenu
    private JPanel MainMenuPanel;
    private JPanel UserMenuPanel;
    private JLabel HelloText;
    private JButton showMenuButton;
    private JButton logoutButton;
    private JButton FinanceButton;
    private JButton editMenuButton;
    private JPanel GUIPanel;

    //Show Menu
    private JPanel ShowMenuPanel;
    private JPanel MenuPanel;
    private JTabbedPane tabbedPaneMenu;
    private JList listFoods;
    private JList listDrinks;
    private JPanel OrderPanel;
    private JList OrderList;
    private JPanel KetProdukPanel;
    private JTextField textHarga;
    private JTextField textPesan;
    private JTextField textHargaTot;
    private JPanel BottomProdukPanel;
    private JButton pesanButton;
    private JPanel BottomMenuPanel;
    private JButton backButton;
    private JPanel BottomOrderPanel;
    private JButton bayarButton;
    private JButton IncOrderButton;
    private JButton DecOrderButton;
    private JPanel OrderBerapaPanel;
    private JPanel HargaTotPanel;
    private JPanel HargaSatPanel;
    private JLabel textHargaSatuan;
    private JLabel textJumlahPesan;
    private JLabel textHargaTotal;
    private JButton deleteOrderButton;
    private JLabel TotalPriceText;
    private JPanel OrderButtonPanel;
    private JPanel TotalPricePanel;
    private JPanel FinancePanel;
    private JButton backButtonFinance;
    private JLabel TotalFinanceText;
    private JPanel topLogin;
    private JPanel centerLogin;
    private JPanel bottomLogin;
    private JPanel topFinancePanel;
    private JPanel bottomFinancePanel;
    private JPanel centerFinancePanel;
    private JList pendapatanList;
    private JButton deleteMenuButton;
    private JButton addMenuButton;
    private JPanel AddMenuPanel;
    private JPanel topAddMenu;
    private JPanel centerAddMenu;
    private JPanel bottomAddMenu;
    private JLabel titleAddMenu;
    private JRadioButton makananRadioButton;
    private JRadioButton minumanRadioButton;
    private JLabel textNamaMenu;
    private JLabel textHargaMenu;
    private JTextField inputNamaMenu;
    private JTextField inputHargaMenu;
    private JButton tambahButton;
    private JButton backButtonAddMenu;
    //Login
    int userMode = 6;

    //ShowMenu
    private DefaultListModel foodsModel;
    private DefaultListModel drinksModel;
    private DefaultListModel ordersModel;
    private DefaultListModel financeModel;
    private List<String[]> foods = new ArrayList<>();
    private List<String[]> drinks = new ArrayList<>();
    private List<String[]> orders = new ArrayList<>();
    private List<String[]> finance = new ArrayList<>();
    private int indexTab;
    private int totOrder;
    private int orderanKe;


    JFrame frame = new JFrame("D'Mas Cafe");

    public GUI() {
        LoginPanel.setVisible(true);
        MainMenuPanel.setVisible(false);
        ShowMenuPanel.setVisible(false);
        FinancePanel.setVisible(false);
        AddMenuPanel.setVisible(false);

        OrderFile fileor = new OrderFile(userMode);
        orderanKe = fileor.orderanKe();

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Pencet enter");
                    try {
                        loginAkun();
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginAkun();
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Listener buat MainMenu
        showMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuPanel.setVisible(true);
                ShowMenuPanel.setVisible(true);

                textHargaSatuan.setText("Harga Satuan");
                textHargaTotal.setText("Harga Total");
                textHarga.setEditable(false);
                textHargaTot.setEditable(false);
                textJumlahPesan.setVisible(true);
                OrderBerapaPanel.setVisible(true);
                pesanButton.setText("Pesan");
                deleteMenuButton.setVisible(false);

                OrderPanel.setVisible(true);
                BottomOrderPanel.setVisible(true);
                DecOrderButton.setEnabled(false);
                IncOrderButton.setEnabled(false);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuPanel.setVisible(false);
                ShowMenuPanel.setVisible(false);
                FinancePanel.setVisible(false);
                AddMenuPanel.setVisible(false);
                LoginPanel.setVisible(true);
                listFoods.clearSelection();
                listDrinks.clearSelection();
                orders.clear();
            }
        });

        FinanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowMenuPanel.setVisible(false);
                AddMenuPanel.setVisible(false);
                OrderFile fileop = new OrderFile(userMode);
                finance = fileop.read();
                int totalPendapatan = 0;

                financeModel.removeAllElements();

                for (int i = 0; i < finance.size(); i++) {
                    System.out.println("Menambahkan " + finance.get(i)[3] + " ke dalam list history orderan");
                    financeModel.addElement(finance.get(i)[0] + ". " + finance.get(i)[2] + " " + finance.get(i)[3] + " (Rp " + (Integer.valueOf(finance.get(i)[4]) * Integer.valueOf(finance.get(i)[5])) + ")");
                    totalPendapatan += (Integer.valueOf(finance.get(i)[4]) * Integer.valueOf(finance.get(i)[5]));
                }

                MainMenuPanel.setVisible(true);
                FinancePanel.setVisible(true);

                TotalFinanceText.setText("Total pendapatan: Rp" + String.valueOf(totalPendapatan));
            }
        });

        addMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuPanel.setVisible(true);
                ShowMenuPanel.setVisible(false);
                FinancePanel.setVisible(false);
                AddMenuPanel.setVisible(true);
            }
        });

        editMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuPanel.setVisible(true);
                ShowMenuPanel.setVisible(true);
                FinancePanel.setVisible(false);
                AddMenuPanel.setVisible(false);

                OrderPanel.setVisible(false);
                BottomOrderPanel.setVisible(false);

                textHargaSatuan.setText("Nama produk");
                textHargaTotal.setText("Harga Satuan");
                textHarga.setEditable(true);
                textHargaTot.setEditable(true);
                textJumlahPesan.setVisible(false);
                OrderBerapaPanel.setVisible(false);
                pesanButton.setText("Edit");
                deleteMenuButton.setVisible(true);
            }
        });
        //------

        //ShowMenu
        FoodFile fileop = new FoodFile(userMode);
        foods = fileop.read();

        DrinkFile fileod = new DrinkFile(userMode);
        drinks = fileod.read();

        foodsModel = new DefaultListModel<>();
        listFoods.setModel(foodsModel); //Ngeset list nya pake list model "foodsModel" biar bisa nampil

        drinksModel = new DefaultListModel<>();
        listDrinks.setModel(drinksModel); //Ngeset list nya pake list model "drinksModel" biar bisa nampil

        ordersModel = new DefaultListModel<>();
        OrderList.setModel(ordersModel); //Ngeset list nya pake list model "ordersModel" biar bisa nampil

        financeModel = new DefaultListModel<>();
        pendapatanList.setModel(financeModel); //Ngeset list nya pake list model "financeModel" biar bisa nampil

        listFoods.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = listFoods.getSelectedIndex();
                if (index >= 0) {
                    OrderList.clearSelection();
                    deleteOrderButton.setEnabled(false);
                    if (userMode == 5) {
                        textHarga.setText(foods.get(index)[2]);
                        textHargaTot.setText("0");
                        textPesan.setText("0");
                        pesanButton.setText("Pesan");
                        DecOrderButton.setEnabled(false);
                        pesanButton.setEnabled(false);
                    } else {
                        textHarga.setText(foods.get(index)[1]);
                        textHargaTot.setText(foods.get(index)[2]);
                        pesanButton.setEnabled(true);
                        deleteMenuButton.setEnabled(true);
                    }

                    totOrder = 0;
                    IncOrderButton.setEnabled(true);
                }
            }
        });

        refreshListFoods();
        refreshListDrinks();

        tabbedPaneMenu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                indexTab = tabbedPaneMenu.getSelectedIndex();
                textHarga.setText("");
                textHargaTot.setText("");
                textPesan.setText("");
                totOrder = 0;
                IncOrderButton.setEnabled(false);
                DecOrderButton.setEnabled(false);
                pesanButton.setEnabled(false);
                listFoods.clearSelection();
                listDrinks.clearSelection();
                OrderList.clearSelection();
            }
        });

        listDrinks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = listDrinks.getSelectedIndex();
                if (index >= 0) {
                    OrderList.clearSelection();
                    deleteOrderButton.setEnabled(false);
                    if (userMode == 5) {
                        textHarga.setText(drinks.get(index)[2]);
                        textHargaTot.setText("0");
                        textPesan.setText("0");
                        pesanButton.setText("Pesan");
                        DecOrderButton.setEnabled(false);
                        pesanButton.setEnabled(false);
                    } else {
                        textHarga.setText(drinks.get(index)[1]);
                        textHargaTot.setText(drinks.get(index)[2]);
                        pesanButton.setEnabled(true);
                    }
                    IncOrderButton.setEnabled(true);
                    totOrder = 0;
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textHarga.setText("");
                textHargaTot.setText("");
                textPesan.setText("");
                totOrder = 0;
                IncOrderButton.setEnabled(false);
                DecOrderButton.setEnabled(false);
                pesanButton.setEnabled(false);

                ShowMenuPanel.setVisible(false);
                MainMenuPanel.setVisible(true);

                listFoods.clearSelection(); //Reset selection item di list
                listDrinks.clearSelection(); //Reset selection item di list
                OrderList.clearSelection(); //Reset selection item di list
            }
        });

        DecOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totOrder > 0) {
                    textHargaTot.setText(String.valueOf(Integer.valueOf(textHarga.getText()) * --totOrder));
                    textPesan.setText(String.valueOf(totOrder));
                    if (totOrder == 0) {
                        DecOrderButton.setEnabled(false);
                        pesanButton.setEnabled(false);
                    }
                }
            }
        });

        IncOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textHargaTot.setText(String.valueOf(Integer.valueOf(textHarga.getText()) * ++totOrder));
                textPesan.setText(String.valueOf(totOrder));
                if (totOrder == 1) {
                    DecOrderButton.setEnabled(true);
                    pesanButton.setEnabled(true);
                }
            }
        });

        deleteMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index;
                String jenis, isi;
                if (indexTab == 0) {
                    index = listFoods.getSelectedIndex();
                    isi = foods.get(index)[1];
                    jenis = "makanan";
                } else {
                    index = listDrinks.getSelectedIndex();
                    isi = drinks.get(index)[1];
                    jenis = "minuman";
                }

                if (index >= 0) {
                    System.out.println("Menghapus " + isi + " dari list " + jenis);
                    if (indexTab == 0) {
                        foods.remove(index);
                    } else {
                        drinks.remove(index);
                    }
                    textHarga.setText("");
                    textHargaTot.setText("");
                    textPesan.setText("");
                    pesanButton.setEnabled(false);
                    deleteOrderButton.setEnabled(false);
                    DecOrderButton.setEnabled(false);
                    IncOrderButton.setEnabled(false);
                    if (indexTab == 0) {
                        FoodFile fileof = new FoodFile(userMode);
                        fileof.write(foods, 0);
                        refreshListFoods();
                    } else {
                        DrinkFile fileod = new DrinkFile(userMode);
                        fileod.write(drinks, 0);
                        refreshListDrinks();
                    }

                }
            }
        });

        pesanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index;
                String[] orderan = new String[8];

                if (pesanButton.getText().equals("Edit")) { //Jika tombolnya edit
                    if (textHarga.getText().equals("") || textHargaTot.getText().equals("")) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Input nama dan harga tidak boleh ada yang kosong!");
                    } else {
                        if (isNumeric(textHargaTot.getText())) {
                            if (Integer.valueOf(textHargaTot.getText()) < 0) {
                                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                        "Input harga tidak boleh kurang dari 0!");
                            } else {
                                String[] menuBaru = new String[3];
                                if (indexTab == 0) {
                                    menuBaru[0] = String.valueOf(1);
                                    index = listFoods.getSelectedIndex();
                                } else {
                                    menuBaru[0] = String.valueOf(2);
                                    index = listDrinks.getSelectedIndex();
                                }
                                menuBaru[1] = textHarga.getText();
                                menuBaru[2] = textHargaTot.getText();

                                if (indexTab == 0) {
                                    foods.set(index, menuBaru);
                                    FoodFile fileof = new FoodFile(userMode);
                                    fileof.write(foods, 0);
                                    refreshListFoods();
                                    listFoods.clearSelection();
                                } else {
                                    drinks.set(index, menuBaru);
                                    DrinkFile fileod = new DrinkFile(userMode);
                                    fileod.write(drinks, 0);
                                    refreshListDrinks();
                                    listDrinks.clearSelection();
                                }
                                deleteMenuButton.setEnabled(false);

                                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                        "Menu berhasil diubah");

                                textHarga.setText("");
                                textHargaTot.setText("");
                                pesanButton.setEnabled(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "Input harga harus berupa integer!");
                        }
                    }

                } else if (pesanButton.getText().equals("Edit pesanan")) { //Jika tombolnya edit pesanan
                    orderan[0] = String.valueOf(1);
                    index = OrderList.getSelectedIndex();
                    if (index >= 0) {
                        orderan[1] = orders.get(index)[1];
                        orderan[2] = orders.get(index)[2];
                        orderan[3] = orders.get(index)[3];
                        orderan[4] = orders.get(index)[4];
                        orderan[5] = String.valueOf(totOrder);
                        orderan[6] = "Order diterima";

                        orders.set(index, orderan);
                        OrderList.clearSelection();
                        refreshListOrders();

                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Pesanan berhasil diubah!");

                        textHarga.setText("");
                        textHargaTot.setText("");
                        textPesan.setText("");
                        DecOrderButton.setEnabled(false);
                        IncOrderButton.setEnabled(false);
                        pesanButton.setEnabled(false);
                    }

                } else {
                    orderan[0] = String.valueOf(orderanKe);
                    if (indexTab == 0) {
                        index = listFoods.getSelectedIndex();
                        orderan[1] = "Customer";
                        orderan[2] = String.valueOf(foods.get(index)[0]);
                        orderan[3] = foods.get(index)[1];
                        orderan[4] = foods.get(index)[2];
                    } else {
                        index = listDrinks.getSelectedIndex();
                        orderan[1] = "Customer";
                        orderan[2] = String.valueOf(drinks.get(index)[0]);
                        orderan[3] = drinks.get(index)[1];
                        orderan[4] = drinks.get(index)[2];
                    }
                    orderan[5] = String.valueOf(totOrder);
                    orderan[6] = "Order diterima";
                    orders.add(orderan);
                    if (indexTab == 0) {
                        System.out.println(foods.get(index)[1] + " added to cart");
                        listFoods.clearSelection();
                    } else {
                        System.out.println(drinks.get(index)[1] + " added to cart");
                        listDrinks.clearSelection();
                    }

                    bayarButton.setEnabled(true);

                    refreshListOrders();

                    textHarga.setText("");
                    textHargaTot.setText("");
                    textPesan.setText("");
                    DecOrderButton.setEnabled(false);
                    IncOrderButton.setEnabled(false);
                    pesanButton.setEnabled(false);
                }
            }
        });

        bayarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Write to file (orders.csv)
                OrderFile fileop = new OrderFile(userMode);
                fileop.write(orders, 0);

                orderanKe++;

                orders.clear();
                ordersModel.clear();

                textHarga.setText("");
                textHargaTot.setText("");
                textPesan.setText("");
                totOrder = 0;
                TotalPriceText.setText("Total: ");
                IncOrderButton.setEnabled(false);
                DecOrderButton.setEnabled(false);
                pesanButton.setEnabled(false);

                bayarButton.setEnabled(false);

                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Pesanan terbayar!");
            }
        });

        OrderList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = OrderList.getSelectedIndex();
                if (index >= 0) {
                    textHarga.setText(orders.get(index)[4]);
                    textHargaTot.setText(String.valueOf(Integer.valueOf(orders.get(index)[4]) * Integer.valueOf(orders.get(index)[5])));
                    totOrder = Integer.valueOf(orders.get(index)[5]);
                    textPesan.setText(String.valueOf(totOrder));
                    pesanButton.setText("Edit pesanan");
                    deleteOrderButton.setEnabled(true);
                    DecOrderButton.setEnabled(true);
                    IncOrderButton.setEnabled(true);
                    pesanButton.setEnabled(true);

                    if (indexTab == 0) listFoods.clearSelection();
                    else listDrinks.clearSelection();
                }
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = OrderList.getSelectedIndex();
                if (index >= 0) {
                    System.out.println("Menghapus " + orders.get(index)[3] + " dari list orderan");
                    orders.remove(index);
                    textHarga.setText("");
                    textHargaTot.setText("");
                    textPesan.setText("");
                    pesanButton.setEnabled(false);
                    deleteOrderButton.setEnabled(false);
                    DecOrderButton.setEnabled(false);
                    IncOrderButton.setEnabled(false);
                    refreshListOrders();
                }
            }
        });
        //ShowMenu End

        //Finance start
        backButtonFinance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinancePanel.setVisible(false);
                MainMenuPanel.setVisible(true);
            }
        });
        //Finance end

        //Add Menu Start
        backButtonAddMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMenuPanel.setVisible(false);
                inputNamaMenu.setText("");
                inputHargaMenu.setText("");
                MainMenuPanel.setVisible(true);
            }
        });
        //Add Menu end
        makananRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minumanRadioButton.setSelected(false);
                inputNamaMenu.setEnabled(true);
                inputHargaMenu.setEnabled(true);
                tambahButton.setEnabled(true);
            }
        });

        minumanRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makananRadioButton.setSelected(false);
                inputNamaMenu.setEnabled(true);
                inputHargaMenu.setEnabled(true);
                tambahButton.setEnabled(true);
            }
        });

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputHargaMenu.getText().equals("") || inputNamaMenu.getText().equals("")) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                            "Input nama menu ataupun harga tidak boleh ada yang kosong!");
                } else {
                    if (isNumeric(inputHargaMenu.getText())) {
                        if (Integer.valueOf(inputHargaMenu.getText()) > -1) {
                            String[] menu = new String[3];
                            menu[1] = inputNamaMenu.getText();
                            menu[2] = inputHargaMenu.getText();

                            if (makananRadioButton.isSelected()) {
                                menu[0] = String.valueOf(1);
                                foods.add(menu);
                                fileop.write(foods, 0);
                                makananRadioButton.setSelected(false);
                                refreshListFoods();
                            } else {
                                menu[0] = String.valueOf(2);
                                drinks.add(menu);
                                fileod.write(drinks, 0);
                                minumanRadioButton.setSelected(false);
                                refreshListDrinks();
                            }

                            inputNamaMenu.setText("");
                            inputHargaMenu.setText("");
                            inputNamaMenu.setEnabled(false);
                            inputHargaMenu.setEnabled(false);
                            tambahButton.setEnabled(false);

                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "Menu berhasil ditambahkan!");
                        } else {
                            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                    "Input harga tidak boleh kurang dari 0!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Input harga harus berupa integer!");
                    }
                }
            }
        });
    }

    public void refreshListFoods() {
        foodsModel.removeAllElements();
        System.out.println("Hapus semua isi list makanan");
        for (int i = 0; i < foods.size(); i++) {
            System.out.println("Menambahkan " + foods.get(i)[1] + " ke dalam list makanan");
            foodsModel.addElement(foods.get(i)[1]);
        }
    }

    public void refreshListDrinks() {
        drinksModel.removeAllElements();
        System.out.println("Hapus semua isi list minuman");
        for (int i = 0; i < drinks.size(); i++) {
            System.out.println("Menambahkan " + drinks.get(i)[1] + " ke dalam list minuman");
            drinksModel.addElement(drinks.get(i)[1]);
        }
    }

    public void refreshListOrders() {
        ordersModel.removeAllElements();
        System.out.println("Hapus semua isi list orderan");
        int totalPrice = 0;
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("Menambahkan " + orders.get(i)[3] + " ke dalam list orderan");
            ordersModel.addElement(orders.get(i)[5] + " " + orders.get(i)[3] + " (Rp " + (Integer.valueOf(orders.get(i)[4]) * Integer.valueOf(orders.get(i)[5])) + ")");
            totalPrice += (Integer.valueOf(orders.get(i)[4]) * Integer.valueOf(orders.get(i)[5]));
        }
        TotalPriceText.setText("Total: Rp" + String.valueOf(totalPrice));
    }

    public static boolean isNumeric(String string) {
        int val;

        if (string == null || string.equals("")) {
            System.out.println("Input kosong");
            return false;
        }

        try {
            val = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input bukan integer");
        }
        return false;
    }

    public void login() {
        frame.setContentPane(new GUI().GUIPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 400);
        frame.setVisible(true);
    }

    public void loginAkun() throws NoSuchAlgorithmException {
        String userInput = userField.getText();
        String passwordInput = valueOf(passwordField.getPassword());
        //MD5 Hashing
        String encryptPass = null;

        PasswordEncrypt konversi = new PasswordEncrypt();
        encryptPass = konversi.encrypt(passwordInput);

        if (!(userInput.isEmpty() || passwordInput.isEmpty())) {
            User user = new User(userInput, encryptPass);
            userMode = user.login();

            if (userMode == 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Wrong Password!");
                passwordField.setText("");
            } else if (userMode == 6) JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "User not found!");

            if (userMode != 6 && userMode != 0) {
                LoginPanel.setVisible(false);
                MainMenuPanel.setVisible(true);
                MainMenuPanel.setOpaque(false);

                if (userMode == 5) {
                    HelloText.setText("Hello, user!");
                    showMenuButton.setVisible(true);
                    addMenuButton.setVisible(false);
                    FinanceButton.setVisible(false);
                    editMenuButton.setVisible(false);
                } else {
                    HelloText.setText("Hello, admin!");
                    showMenuButton.setVisible(false);
                    addMenuButton.setVisible(true);
                    FinanceButton.setVisible(true);
                    editMenuButton.setVisible(true);
                }
                UserMenuPanel.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        GUI GUIobject = new GUI();
        GUIobject.login();
    }
}
