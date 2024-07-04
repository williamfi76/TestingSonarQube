
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class TestingQube {
    static class Identity {
        String username, password;

        public Identity(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    static class Membership {
        Identity identity;
        String noTelp;

        public Membership(Identity identity, String noTelp) {
            this.identity = identity;
            this.noTelp = noTelp;
        }
    }

    static class FoodAndBeverage {
        Identity identity;
        String namaFnB;
        double hargaFnB;

        public FoodAndBeverage(String namaFnB, double hargaFnB) {
            this.namaFnB = namaFnB;
            this.hargaFnB = hargaFnB;
        }

        public FoodAndBeverage(Identity identity, String namaFnB, double hargaFnB) {
            this.identity = identity;
            this.namaFnB = namaFnB;
            this.hargaFnB = hargaFnB;
        }
    }

    static class Pelanggan {
        Identity identity;
        int banyakPelanggan;

        public Pelanggan(Identity identity) {
            this.identity = identity;
        }

        public Pelanggan(int banyakPelanggan) {
            this.banyakPelanggan = banyakPelanggan;
        }
    }

    static class Reservasi {
        int banyakOrang;
        int tanggal;
        int bulan;
        String tahun;

        public Reservasi(int banyakOrang, int tanggal, int bulan, String tahun) {
            this.banyakOrang = banyakOrang;
            this.tanggal = tanggal;
            this.bulan = bulan;
            this.tahun = tahun;
        }
    }

    static Scanner input = new Scanner(System.in);
    static FoodAndBeverage[] menu = new FoodAndBeverage[19];
    static Membership[] arrMembership = new Membership[100];
    static int nomorAntrian = 1;
    static HashMap<String, Membership> membership = new HashMap<>(); // ID, (uname, pass)
    static ArrayList<String> uniqueItems = new ArrayList<>();
    static Queue<String> queueFnB = new LinkedList<String>();
    static ArrayList<FoodAndBeverage> tampungMenu = new ArrayList<>();
    static ArrayList<FoodAndBeverage> tampungPelanggan = new ArrayList<>();
    static ArrayList<Reservasi> daftarReservasi = new ArrayList<>();
    static int kapasitasReservasi = 50;
    static int banyakOrang = 0;
    static double totalBayarSemuanya = 0.0;
    static int delivery = 0;
    static int pilihan = 0;
    static double getDiskon = 0.0;

    static {
        // food and beverage
        menu[0] = new FoodAndBeverage("Tidak memilih menu", 0);
        menu[1] = new FoodAndBeverage("Ricebowl Ayam Geprek Sambal Special", 20000);
        menu[2] = new FoodAndBeverage("Ricebowl Chicken Sambal Matah", 24000);
        menu[3] = new FoodAndBeverage("Ricebowl Beef Teriyaki", 22000);
        menu[4] = new FoodAndBeverage("Dimsum Ayam", 16000);
        menu[5] = new FoodAndBeverage("Dimsum Udang", 16000);
        menu[6] = new FoodAndBeverage("Dimsum Smoke Beef", 16000);
        menu[7] = new FoodAndBeverage("Kopi Susu Coco Latte", 19000);
        menu[8] = new FoodAndBeverage("Kopi Susu Pandan", 19000);
        menu[9] = new FoodAndBeverage("Kopi susu Hazelnut", 19000);
        menu[10] = new FoodAndBeverage("Choco Classic", 17000);
        menu[11] = new FoodAndBeverage("Red Velvet Latte", 17000);
        menu[12] = new FoodAndBeverage("Lemon Fresh Cup", 10000);
        menu[13] = new FoodAndBeverage("Ice Cream Cone", 5500);
        menu[14] = new FoodAndBeverage("Croffle", 15000);
        menu[15] = new FoodAndBeverage("Cireng Bumbu Rujak", 15000);
        menu[16] = new FoodAndBeverage("Sosis Bakar", 16500);
        menu[17] = new FoodAndBeverage("French Fries", 12000);
        menu[18] = new FoodAndBeverage("Crepes", 4000);

        // membership
        membership.put("001", new Membership(new Identity("grace", "hai123"), "081266781312"));
        membership.put("002", new Membership(new Identity("deaa", "iya123"), "081213176564"));
        membership.put("003", new Membership(new Identity("depott", "winter123"), "082354678321"));
        membership.put("004", new Membership(new Identity("tantan", "mobil123"), "083289657834"));
        membership.put("005", new Membership(new Identity("barbie", "cantik123"), "083487695432"));
    }

    public static void rolePelanggan() {
        int jawaban = 0;
        String username = "", password = "", namaPelanggan = "";
        do {
            System.out.println("Apakah kamu merupakan pelanggan \n"
                    + "1. Non-membership\n"
                    + "2. Membership");
            jawaban = input.nextInt();
            input.nextLine();

            if (jawaban == 1) {
                System.out.println("-----------------");
                System.out.println("Silakan masukkan nama Anda: ");
                namaPelanggan = input.nextLine();
                tampungPelanggan.add(new FoodAndBeverage(new Identity(namaPelanggan, null), null, 0.0));
                getDiskon = 0;
                System.out.println("apakah untuk reservasi event atau bukan? (1 for ya, 2 for no)");
                jawaban = input.nextInt();
                if (jawaban == 1) {
                    reservationEvent(namaPelanggan);
                } else {
                    purchaseMeal(namaPelanggan);
                }

            } else if (jawaban == 2) {
                System.out.println("-----------------");
                System.out.print("Masukkan username Anda: ");
                username = input.nextLine();
                System.out.print("Masukkan password Anda: ");
                password = input.nextLine();
                for (String key : membership.keySet()) {
                    if (membership.get(key).identity.username.equalsIgnoreCase(username)
                            && membership.get(key).identity.password.equalsIgnoreCase(password)) {
                        System.out.println("Anda termasuk dalam Membership.");
                        tampungPelanggan.add(new FoodAndBeverage(new Identity(username, password), null, 0.0));
                        getDiskon += 0.1;
                        System.out.println("apakah untuk reservasi event atau bukan? (1 for ya, 2 for no)");
                        jawaban = input.nextInt();
                        if (jawaban == 1) {
                            reservationEvent(namaPelanggan);
                        } else {
                            purchaseMeal(username);
                        }
                        return;
                    }
                }
                System.out.println("Username " + username + " dengan password " + password + " tidak ada dalam List Membership.");
            } else {
                System.out.println("Tidak ada pilihan menu tersebut.");
            }
        } while (jawaban >= 3);
    }

    public static void purchaseMeal(String namaPelanggan) {
        int banyakmenu = 0;
        int pilihmenu = 0;
        boolean jawaban = true;

        do {
            System.out.println("-- SILAHKAN PILIH HIDANGAN --");
            System.out.println("-- MENU --");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i) + ". " + menu[i].namaFnB + "\t\tRp. " + menu[i].hargaFnB);
            }
            System.out.println("Boleh kaka, mau pilih menu no berapa?");
            pilihmenu = input.nextInt();
            if (pilihmenu != 0 && pilihmenu < menu.length) {
                System.out.println("Yaudah boleh, mau berapa banyak?");
                banyakmenu = input.nextInt();
                for (int i = 0; i < banyakmenu; i++) {
                    FoodAndBeverage banyakFnBmenu = new FoodAndBeverage(new Identity(namaPelanggan, null),menu[pilihmenu].namaFnB, menu[pilihmenu].hargaFnB);
                    tampungMenu.add(banyakFnBmenu);
                }
                System.out.println("Menu berhasil di tambahkan ke pesanan");
            } else if (pilihmenu == 0) {
                System.out.println(menu[0].namaFnB);
                break;
            } else {
                System.out.println("Maaf, menu yang anda pilih tidak tersedia :)");
                break;
            }
            printPesananPelanggan(namaPelanggan);
            System.out.println("Ingin memesan lagi? (true/false)");
            jawaban = Boolean.parseBoolean(input.nextLine());
        } while (jawaban);
        if (!jawaban) {
            System.out.println("Metode pengantaran \n" +
                    "1. Take away \n" +
                    "2. Dine in?");
            delivery = Integer.parseInt(input.nextLine()); 
            double totalBayar = transaction(namaPelanggan, banyakmenu);
            paymentMethod(namaPelanggan, totalBayar, getDiskon); 
        }
    }

    public static double transaction(String namaPelanggan, int qty) {
        double total = 0;
        for (int i = 0; i < tampungMenu.size(); i++) {
            if (tampungMenu.get(i).identity.username.equals(namaPelanggan)) {
                total += tampungMenu.get(i).hargaFnB;
            }
        }
        if (delivery == 1) {
            double biayaPacking = 1000 * qty;
            total += biayaPacking;
        }
        totalBayarSemuanya += total - (total * getDiskon);
        return total;
    }

    public static void printPesananPelanggan(String namaPelanggan) { // nama pelanggan buat get nama si pelanggan
        int count = 0;
        double harga = 0.0;
        for (int i = 0; i < tampungPelanggan.size(); i++) {
            if (tampungPelanggan.get(i).identity.username.equals(namaPelanggan)) {
                System.out.println("Nama Pelanggan ke-" + (i + 1) + " :" + tampungPelanggan.get(i).identity.username);
            }
        }
        System.out.println("--> menu yang Dipesan <--");
        uniqueItems.clear();
        for (FoodAndBeverage item : tampungMenu) {
            if (!uniqueItems.contains(item.namaFnB) && item.identity.username.equals(namaPelanggan)) {
                uniqueItems.add(item.namaFnB);
            }
        }
        for (String item : uniqueItems) {
            count = 0;
            harga = 0.0;
            for (FoodAndBeverage a : tampungMenu) {
                if (a.namaFnB.equals(item) && a.identity.username.equals(namaPelanggan)) {
                    harga += a.hargaFnB;
                    count++;
                }
            }
            System.out.println(item + " (" + count + ")" + ", \ttotal = " + harga);
        }
    }

    public static void paymentMethod(String namaPelanggan, double totalBayar, double diskon) {
        int pembayaran = 0;
        do {
            System.out.println(
                    "Mau bayar pake apa? \n"
                            + "1. OVO \n"
                            + "2. Qris \n"
                            + "3. GoPay \n"
                            + "4. Dana \n"
                            + "5. Isaku \n"
                            + "6. Exit");
            pembayaran = input.nextInt();
            switch (pembayaran) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    printPesananPelanggan(namaPelanggan);
                    System.out.println("------------");
                    System.out.println("Total pembayaran sebelum diskon:\tRp. " + totalBayar);
                    totalBayar -= totalBayar * diskon;
                    System.out.println("Diskon 10%:\t\t\tRp. " + (totalBayar * diskon));
                    System.out.println("Total pembayaran setelah diskon:\tRp. " + totalBayar);
                    System.out.println("------------");
                    queueFnB.add(namaPelanggan);
                    System.out.println("Nama pelanggan : " + namaPelanggan);
                    System.out.println("Nomor antrian kamu : " + queueFnB.size());
                    break;
                default:
                    System.out.println("Pembayaran Anda gagal!");
                    break;
            }
        } while (pembayaran > 6);
    }

    public static int getTotalOrangReserved(int tanggal, int bulan, String tahun) {
        int totalOrang = 0;
        for (Reservasi reservasi : daftarReservasi) {
            if (reservasi.tanggal == tanggal && reservasi.bulan == bulan && reservasi.tahun.equals(tahun)) {
                totalOrang += reservasi.banyakOrang;
            }
        }
        return totalOrang;
    }

    public static void reservationEvent(String namaPelanggan) {
        System.out.println("-- RESERVATION MENU --");
        System.out.println("Mau berapa orang?");
        int jumlahOrang = input.nextInt();

        System.out.println("Masukkan tanggal reservasi (dd mm yyyy): ");
        int tanggal = input.nextInt();
        int bulan = input.nextInt();
        String tahun = input.next();

        int totalOrangReserved = getTotalOrangReserved(tanggal, bulan, tahun);
        int sisaKapasitas = kapasitasReservasi - totalOrangReserved;

        if (jumlahOrang <= sisaKapasitas) {
            daftarReservasi.add(new Reservasi(jumlahOrang, tanggal, bulan, tahun));
            System.out.println("Reservasi berhasil");
            int banyakmenu = 0;
            int pilihmenu = 0;
            boolean jawaban = true;

        do {
            System.out.println("-- SILAHKAN PILIH HIDANGAN --");
            System.out.println("-- MENU --");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i) + ". " + menu[i].namaFnB + "\t\tRp. " + menu[i].hargaFnB);
            }
            System.out.println("Boleh kaka, mau pilih menu no berapa?");
            pilihmenu = input.nextInt();
            if (pilihmenu != 0 && pilihmenu < menu.length) {
                System.out.println("Yaudah boleh, mau berapa banyak?");
                banyakmenu = input.nextInt();
                for (int i = 0; i < banyakmenu; i++) {
                    FoodAndBeverage banyakFnBmenu = new FoodAndBeverage(new Identity(namaPelanggan, null), menu[pilihmenu].namaFnB, menu[pilihmenu].hargaFnB);
                    tampungMenu.add(banyakFnBmenu);
                }
                System.out.println("Menu berhasil di tambahkan ke pesanan");
            } else if (pilihmenu == 0) {
                System.out.println(menu[0].namaFnB);
                break;
            } else {
                System.out.println("Maaf, menu yang anda pilih tidak tersedia :)");
                break;
            }
            printPesananPelanggan(namaPelanggan);
            System.out.println("Ingin memesan lagi? (true/false)");
            jawaban = input.nextBoolean();
            double totalBayar = transaction(namaPelanggan, banyakmenu);
            paymentMethod(namaPelanggan, totalBayar, getDiskon); 
        } while (jawaban);
        } else {
            System.out.println("Reservasi gagal, kapasitas penuh pada tanggal tersebut.");
            System.out.println("Sisa kapasitas pada tanggal tersebut: " + sisaKapasitas);

            // Coba reservasi pada tanggal berikutnya
            System.out.print("Coba reservasi pada tanggal berikutnya? (ya/tidak): ");
            String cobaTanggalBerikutnya = input.next();
            if (cobaTanggalBerikutnya.equalsIgnoreCase("ya")) {
                tanggal++;
                if (tanggal > 31) {
                    tanggal = 1;
                    bulan++;
                    if (bulan > 12) {
                        bulan = 1;
                        int tahunInt = Integer.parseInt(tahun);
                        tahunInt++;
                        tahun = String.valueOf(tahunInt);
                    }
                }
                reservationEvent(namaPelanggan); // reservasi kembali pada tanggal berikutnya
            }
        }
    }

    public static void listAntrianPelanggan() {
        String username = "degre", password = "halo123", inputUsername, inputPassword;
        do {
            System.out.println("-- ANDA MASUK SEBAGAI ADMIN --");
            System.out.println("Masukkan username admin : ");
            input.nextLine();
            inputUsername = input.nextLine();
            System.out.println("Masukkan password admin : ");
            inputPassword = input.nextLine();

            if (username.equalsIgnoreCase(inputUsername) && password.equalsIgnoreCase(inputPassword)) {
                System.out.println("Selamat datang kembali admin : " + username);
                nomorAntrian = 1;
                for (String pelanggan : queueFnB) {
                    System.out.println("Nama pelanggan : " + pelanggan);
                    System.out.println("Nomor antrian : " + nomorAntrian);
                    nomorAntrian++;
                }
            }
        } while (!inputUsername.equalsIgnoreCase(username) && !inputPassword.equalsIgnoreCase(password));
    }

    public static void rekapPembelianPelanggan() {
        String username = "degre", password = "halo123", inputUsername, inputPassword;
        int count = 0;
        double harga = 0.0;
        do {
            System.out.println("-- ANDA MASUK SEBAGAI ADMIN --");
            System.out.println("Masukkan username admin : ");
            input.nextLine();
            inputUsername = input.nextLine();
            System.out.println("Masukkan password admin : ");
            inputPassword = input.nextLine();

            if (username.equalsIgnoreCase(inputUsername) && password.equalsIgnoreCase(inputPassword)) {
                System.out.println("Selamat datang kembali admin : " + username);
                System.out.println("Berikut pelanggan beserta pesanan nya");
                System.out.println("-----------------");

                for (int i = 0; i < tampungPelanggan.size(); i++) {
                    FoodAndBeverage pelanggan = tampungPelanggan.get(i);
                    System.out.println("Nama : " + pelanggan.identity.username);
                    System.out.println("--> menu yang Dipesan <--");
                    uniqueItems.clear();
                    for (FoodAndBeverage item : tampungMenu) {
                        if (!uniqueItems.contains(item.namaFnB) && item.identity.username.equals(pelanggan.identity.username)) {
                            uniqueItems.add(item.namaFnB);
                        }
                    }
                    for (String items : uniqueItems) {
                        count = 0;
                        harga = 0.0;
                        for (FoodAndBeverage a : tampungMenu) {
                            if (a.namaFnB.equals(items) && a.identity.username.equals(pelanggan.identity.username)) {
                                harga += a.hargaFnB;
                                count++;
                            }
                        }
                        System.out.println(items + " (" + count + ")" + ", \t total = " + harga);
                    }
                }
            } else {
                System.out.println("Maaf username dan password yang Anda masukkan salah");
            }
        } while (!inputUsername.equalsIgnoreCase(username) && !inputPassword.equalsIgnoreCase(password));
    }

    public static void printPendapatan() {
        String username = "degre", password = "halo123", inputUsername, inputPassword;
        do {
            System.out.println("-- ANDA MASUK SEBAGAI ADMIN --");
            System.out.println("Masukkan username admin : ");
            input.nextLine();
            inputUsername = input.nextLine();
            System.out.println("Masukkan password admin : ");
            inputPassword = input.nextLine();

            if (inputUsername.equalsIgnoreCase(username) && inputPassword.equalsIgnoreCase(password)) {
                System.out.println("Total Pendapatan sudah termasuk diskon 10%: " + totalBayarSemuanya);
            } else {
                System.out.println("Maaf username dan password yang Anda masukkan salah");
            }
        } while (!inputUsername.equalsIgnoreCase(username) && !inputPassword.equalsIgnoreCase(password));
    }

    public static void addMembership() {
        String username = "degre", password = "halo123", inputUsername, inputPassword, noTelp, usn, pw;
        do {
            System.out.println("-- ANDA MASUK SEBAGAI ADMIN --");
            System.out.println("Masukkan username admin : ");
            usn = input.nextLine();
            System.out.println("Masukkan password admin : ");
            pw = input.nextLine();

            if (usn.equalsIgnoreCase(username) && pw.equalsIgnoreCase(password)) {
                System.out.println("Selamat datang kembali admin : " + username);
            } else {
                System.out.println("Maaf username dan password yang anda masukkan salah");
            }
        } while (!usn.equalsIgnoreCase(username) && !pw.equalsIgnoreCase(password));

        boolean next = true;
        while (next) {
            System.out.println("Masukkan ID : ");
            String id = input.next();
            input.nextLine();
            System.out.println("Berikan username : ");
            inputUsername = input.next();
            input.nextLine();
            System.out.println("Berikan password : ");
            inputPassword = input.next();
            input.nextLine();
            System.out.println("Masukkan nomor telepon Anda : ");
            noTelp = input.next();
            input.nextLine();

            Membership member = new Membership(new Identity(inputUsername, inputPassword), noTelp);
            System.out.println("Masih ingin melanjutkan data member? (iya/tidak)");
            String exit = input.nextLine();

            if (exit.equals("tidak")) {
                if (!membership.containsKey(id) && !membership.containsValue(member)) {
                    membership.put(id, member);
                } else {
                    System.out.println("Data member yang Anda isi sudah ada.");
                }

                for (String key : membership.keySet()) {
                    System.out.println("ID member : " + key + " nama : " + membership.get(key).identity.username
                            + " data membership berhasil ditambahkan");
                }
                next = false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("SELAMAT DATANG DI CAFE DE'GREE");
        System.out.println("Silakan pilih menu yang diinginkan:");
        System.out.println(
                "1. Menu Role Pelanggan \n"
                + "2. Menu Rekap Pembelian Pelangggan \n"
                + "3. Menu rekap pendapatan \n"
                + "4. Menu Add Membership \n"
                + "5. List Antrian Pelanggan \n"
                 + "6. Exit Program");
        pilihan = input.nextInt();
        input.nextLine();

        while (pilihan != 6) {
            System.out.println("============================================================");
            switch (pilihan) {
                case 1:
                    rolePelanggan();
                    break;
                case 2:
                    rekapPembelianPelanggan();
                    break;
                case 3:
                    printPendapatan();
                    break;
                case 4:
                    addMembership();
                    break;
                case 5:
                    listAntrianPelanggan();
                    break;
                default:
                    System.out.println("maaf nomor yang anda pilih tidak tersedia");
                    break;
            }
            System.out.println("============================================================");
            System.out.println("SELAMAT DATANG DI CAFE DE'GREE");
            System.out.println(
                "1. Menu Role Pelanggan \n"
                + "2. Menu Rekap Pembelian Pelangggan \n"
                + "3. Menu rekap pendapatan \n"
                + "4. Menu Add Membership \n"
                + "5. List Antrian Pelanggan \n"
                 + "6. Exit Program");
            pilihan = input.nextInt();
        }
    }
}