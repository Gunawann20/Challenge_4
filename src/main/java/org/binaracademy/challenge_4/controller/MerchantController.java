package org.binaracademy.challenge_4.controller;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.*;
import org.binaracademy.challenge_4.service.AdminService;
import org.binaracademy.challenge_4.service.MerchantService;
import org.binaracademy.challenge_4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class MerchantController {
    Scanner scanner = new Scanner(System.in);
    private int PAGE = 0;

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private OrderService orderService;

    public void menuAllMerchantView(User sessionuser){

        Page<Merchant> merchants = merchantService.getAllMerchant(PAGE);
        List<Long> merchantCodes = merchants.stream().map(Merchant::getId).collect(Collectors.toList());
        System.out.println(
                "====================================================\n" +
                        "                   SELAMAT DATANG\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "    silahkan ketik kode merchant yang ingin pilih\n"
        );
        if (merchants.getTotalElements() != 0){
            merchants.stream().forEach(merchant -> System.out.println(merchant.getId()+" | "+merchant.getName()+" | "+ merchant.getLocation()));
            System.out.println();
            System.out.println("Page : "+(merchants.getNumber()+1)+" Dari "+merchants.getTotalPages()+" Pages");
            System.out.println();

            if (merchants.getNumber() == 0 && (merchants.getNumber() + 1) != merchants.getTotalPages()){
                System.out.println(
                        "97. Next\n" +
                                "98. Pesanan saya\n" +
                                "99. Merchant saya\n" +
                                "0. Keluar"
                );
            } else if ((merchants.getNumber() + 1) == merchants.getTotalPages()) {
                System.out.println(
                        "96. Prev\n" +
                                "98. Pesanan saya\n" +
                                "99. Merchant saya\n" +
                                "0. Keluar"
                );
            } else if (merchants.getNumber() > 0){
                System.out.println(
                        "97. Next\n" +
                                "96. Prev\n" +
                                "98. Pesanan saya\n" +
                                "99. Merchant saya\n" +
                                "0. Keluar"
                );
            }else{
                System.out.println(
                        "98. Pesanan saya\n" +
                                "99. Merchant saya\n" +
                                "0. Keluar"
                );
            }
        }else {
            System.out.println("Tidak ada merchant yang tersedia.");
            System.out.println();
            System.out.println("99. Merchant saya");
            System.out.println("0. Keluar");
        }
        try {
            System.out.println();
            System.out.print("Masukan pilihan => ");
            int pilihan = scanner.nextInt();
            if (merchantCodes.contains((long) pilihan)){
                this.merchantDetailView(sessionuser, (long) pilihan);
            }else {
                switch (pilihan){
                    case 96:
                        PAGE--;
                        menuAllMerchantView(sessionuser);
                        break;
                    case 97:
                        PAGE++;
                        menuAllMerchantView(sessionuser);
                        break;
                    case 98:
                        this.showMyOrder(sessionuser);
                        break;
                    case 99:
                        myMerchantView(sessionuser);
                        break;
                    case 0:
                        System.out.println("Aplikasi keluar...");
                        Runtime.getRuntime().halt(0);
                    default:
                        System.out.println("Maaf, pilihan tidak tersedia");
                        menuAllMerchantView(sessionuser);

                }
            }
        }catch (InputMismatchException e){
            System.out.println("Modon masukan angka yang valid!");
            menuAllMerchantView(sessionuser);
        }

    }

    private void merchantDetailView(User sessionUser, Long id){
        Merchant merchant = merchantService.getDataMerchantById(id);
        List<Product> products = merchant.getProducts();
        if (merchant != null){
            System.out.println(
                    "====================================================\n" +
                            "                    BINARFUD APP\n" +
                            "====================================================\n" +
                            "Merchant : " + merchant.getName() +
                            "\nLokasi : " + merchant.getLocation() +
                            "\n" +
                            "    silahkan pilih kode produk yang ingin dipesan\n"
            );
            if (products.isEmpty()){
                System.out.println("Merchant belum memiliki produk");
            }else {
                products.forEach(product -> System.out.println(product.getId()+" | "+product.getName()+" | "+product.getPrice()));
            }
        }else {
            System.out.println("Merchant tidak ditemukan");
            menuAllMerchantView(sessionUser);
        }
        System.out.println();
        System.out.println();
        System.out.println(
                "99. Kembali\n" +
                "0. Keluar\n"
        );
        List<Long> productsCode = products.stream().map(Product::getId).collect(Collectors.toList());
        System.out.print(" masukan pilihan => ");
        int pilihan = scanner.nextInt();
        if (productsCode.contains((long) pilihan)){
            Product product = products.stream().filter(product1 -> product1.getId() == (long) pilihan).findFirst().orElse(null);
            if (product != null){
                this.detailProduct(sessionUser, product, merchant, id);
            }
        }else {
            switch (pilihan){
                case 99:
                    this.menuAllMerchantView(sessionUser);
                    break;
                case 0:
                    System.out.println("Aplikasi keluar...");
                    Runtime.getRuntime().halt(0);
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia");
                    this.merchantDetailView(sessionUser, id);
            }
        }
    }

    private void detailProduct(User sessionUser, Product product, Merchant merchant, Long id){
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Merchant : " + merchant.getName() +
                        "\nLokasi : " + merchant.getLocation() +
                        "\n"
        );
        System.out.println(product.getName() + " | "+product.getPrice());
        System.out.println();
        System.out.println(
                "Masukan detail pesanan anda: \n" +
                        "(ketik 0 untuk kembali)"
        );
        System.out.println();
        System.out.println(product.getName()+"  | "+product.getPrice());
        System.out.print("Masukan jumlah : ");
        int jumlah = scanner.nextInt();
        if (jumlah == 0){
            this.merchantDetailView(sessionUser, id);
        }else {
            System.out.print("Masukan alamat : ");
            scanner.nextLine();
            String alamat = scanner.nextLine();
            boolean succes = orderService.addOrder(sessionUser, product, alamat, jumlah);
            if (succes){
                System.out.println("Berhasil membuat pesanan");
                this.merchantDetailView(sessionUser, merchant.getId());
            }else {
                System.out.println("Gagal menambahkan pesanan");
                this.merchantDetailView(sessionUser, merchant.getId());
            }
        }
    }

    private void showMyOrder(User sessionUser){
        int page = 0;
        Page<Order> orders = orderService.showOrderByuserIdIsNotCompleted(sessionUser, page);
        List<Long> orderCode = orders.stream().map(Order::getId).collect(Collectors.toList());
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Berikut merupakan daftar pesanan anda. Ketik kode untuk melakukan pembayaran\n"
        );
        if (orders.getTotalElements() == 0){
            System.out.println("Anda belum melakukan pemesanan");
        }else {
            orders.forEach(order -> {
                if (!order.getIsCompleted()){
                    System.out.println(order.getId()+" - "+order.getOrderDetails().getProduct().getName()+" | waktu pesanan: "+ order.getTime()+" | status: Belum bayar");
                }
            });
        }
        System.out.println();
        System.out.println("Page : "+(orders.getNumber()+1)+" Dari "+orders.getTotalPages()+" Pages");
        System.out.println();
        System.out.println(
                "96. Pembayaran\n"+
                "97. Riwayat pemesanan\n" +
                        "99. Kembali\n" +
                        "0. Keluar\n"
        );

        try {
            System.out.print("Masukan pilihan => ");
            int pilihan = scanner.nextInt();
            if (orderCode.contains((long) pilihan)){

            }else {
                switch (pilihan){
                    case 96:
                        if (orders.getTotalElements() == 0){
                            System.out.println("Anda tidak memiliki pesanan yang harus dibayar");
                        }else {
                            this.pembayaran(orders, sessionUser);
                        }
                        break;
                    case 97:
                        this.showOrderCompleted(sessionUser);
                        break;
                    case 99:
                        this.menuAllMerchantView(sessionUser);
                        break;
                    case 0:
                        Runtime.getRuntime().halt(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia");
                        this.showMyOrder(sessionUser);
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Harap masukan inputan yang valid");
            this.showMyOrder(sessionUser);
        }
    }

    private void showOrderCompleted(User user){
        int page = 0;
        Page<Order> orders = orderService.showOrderByUserIdIsCompleted(user, page);
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Berikut merupakan daftar riwayat pesanan anda\n"
        );
        if (orders.getTotalElements() == 0){
            System.out.println("Tidak ada riwayat pemesanan");
        }else {
            orders.forEach(order -> {
                if (order.getIsCompleted()){
                    System.out.println(order.getId()+" | "+order.getOrderDetails().getProduct().getMerchant().getName()+" | waktu pesanan: "+ order.getTime()+" | status: Lunas");
                }
            });
        }
        System.out.println();
        System.out.println("Page : "+(orders.getNumber()+1)+" Dari "+orders.getTotalPages()+" Pages");
        System.out.println();
        System.out.println(
                        "99. Kembali\n" +
                        "0. Keluar\n"
        );
        try {
            System.out.print("Masukan pilihan : ");
            int pilihan = scanner.nextInt();
            switch (pilihan){
                case 99:
                    this.showMyOrder(user);
                    break;
                case 0:
                    Runtime.getRuntime().halt(0);
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia");
                    this.showOrderCompleted(user);
            }
        }catch (InputMismatchException e){
            System.out.println("Harap masukan inputan yang valid");
            this.showOrderCompleted(user);
        }
    }

    private void pembayaran(Page<Order> orders, User user){
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Berikut merupakan total pesanan anda\n"
        );
        System.out.println();
        AtomicInteger totalQty = new AtomicInteger();
        AtomicLong totalPrice = new AtomicLong();
        orders.forEach(order -> {
            System.out.println(order.getOrderDetails().getProduct().getName()+ "   "+ order.getOrderDetails().getQuantity()+ "  "+order.getOrderDetails().getTotalPrice());
            totalQty.addAndGet(order.getOrderDetails().getQuantity());
            totalPrice.addAndGet(order.getOrderDetails().getTotalPrice());
        });
        System.out.println("----------------------------------------------------+");
        System.out.println("    Total         "+ totalQty+ "   "+ totalPrice);

        System.out.println(
                "1. Konfirmasi dan bayar\n" +
                        "2. Kembali\n" +
                        "0. Keluar\n"
        );
        try {
            System.out.print("masukan pilihan : ");
            int pilihan = scanner.nextInt();
            switch (pilihan){
                case 1:
                    try {
                        orders.forEach(order -> {
                            order.setIsCompleted(true);
                            orderService.updateOrder(order);
                        });
                        printStruk(orders);
                    }catch (Exception e){
                        log.error(e.getMessage());
                        System.out.println("Pembayaran gagal"+ e.getMessage());
                    }
                    break;
                case 2:
                    this.showMyOrder(user);
                    break;
                case 0:
                    Runtime.getRuntime().halt(0);
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia");
                    this.pembayaran(orders, user);
            }
        }catch (InputMismatchException e){
            System.out.println("Input tidak valid");
            this.pembayaran(orders, user);
        }

    }

    private void myMerchantView(User sessionUser){
        if (adminService.checkIsAdmin(sessionUser)){
            Admin admin = adminService.getAdmin(sessionUser);
            List<Merchant> merchants = adminService.getAllMerchant(sessionUser);
            List<Long> merchantCode = merchants.stream().map(Merchant::getId).collect(Collectors.toList());
            System.out.println(
                    "====================================================\n" +
                            "                    BINARFUD APP\n" +
                            "====================================================\n" +
                            "Berikut Merupakan Daftar merchant anda\n" +
                            "ketik kode merchant untuk edit.\n"
            );
            merchants.forEach(merchant -> {
                boolean isOpen = merchant.getIsOpen();
                if (isOpen){
                    System.out.println(merchant.getId() +" | "+merchant.getName()+ " | "+ merchant.getLocation()+ " | status : Buka");
                }else {
                    System.out.println(merchant.getId() +" | "+merchant.getName()+ " | "+ merchant.getLocation()+ " | status : Tutup");
                }

            });
            System.out.println();
            System.out.println();
            System.out.println(
                    "98. Tambah merchant\n" +
                            "99. Kembali\n" +
                            "0. Keluar aplikasi\n"
            );
            System.out.println();
            System.out.print("Masukan pilihan => ");
            int pilihan  = scanner.nextInt();
            if (merchantCode.contains((long) pilihan)){
                merchantDetailViewAdmin(sessionUser, (long) pilihan);
            }else {
                try {
                    switch (pilihan){
                        case 98:
                            addMerchant(admin, sessionUser);
                            break;
                        case 99:
                            this.menuAllMerchantView(sessionUser);
                            break;
                        case 0:
                            Runtime.getRuntime().halt(0);
                            break;
                        default:
                            System.out.println("Pilihan tidak tersedia");
                            this.myMerchantView(sessionUser);
                    }
                }catch (InputMismatchException e){
                    System.out.println("Harap masukan inputan yang valid");
                    this.myMerchantView(sessionUser);
                }
            }
        }else {
            System.out.println("Anda belum memiliki merchant");
            System.out.println();
            System.out.println(
                    "98. Tambah merchant\n" +
                            "99. Kembali\n" +
                            "0. Keluar aplikasi\n"
            );
            System.out.println();
            try {
                System.out.print("Masukan pilihan => ");
                int pilihan  = scanner.nextInt();
                switch (pilihan){
                    case 98:
                        bukaMerchantViewNew(new User());
                        break;
                    case 99:
                        this.menuAllMerchantView(sessionUser);
                        break;
                    case 0:
                        Runtime.getRuntime().halt(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia");
                        this.myMerchantView(sessionUser);
                }
            }catch (InputMismatchException e){
                System.out.println("Harap masukan inputan yang valid");
                this.myMerchantView(sessionUser);
            }
        }
    }

    private void merchantDetailViewAdmin(User sessionUser, Long id){
        Merchant merchant = merchantService.getDataMerchantById(id);
        String status;
        String nStatus;
        if (merchant.getIsOpen()){
            status = "Buka";
            nStatus = "Tutup";
        }else {
            status = "Tutup";
            nStatus = "Buka";
        }
        List<Product> products = merchant.getProducts();
        if (merchant != null){
            System.out.println(
                    "====================================================\n" +
                            "                    BINARFUD APP\n" +
                            "====================================================\n" +
                            "Merchant : " + merchant.getName() +
                            "\nLokasi : " + merchant.getLocation() +
                            "\nStatus : " + status +
                            "\n"
            );
            if (products.isEmpty()){
                System.out.println("Merchant belum memiliki produk");
            }else {
                products.forEach(product -> System.out.println(product.getId()+" | "+product.getName()+" | "+product.getPrice()));
            }
        }else {
            System.out.println("Merchant tidak ditemukan");
            menuAllMerchantView(sessionUser);
        }
        System.out.println();
        System.out.println();
        System.out.println(
                "96. "+nStatus+" Merchant\n" +
                        "97. Tambah produk\n" +
                        "98. Hapus Merchant\n" +
                        "99. Kembali\n" +
                        "0. Keluar\n"
        );
        List<Long> productsCode = products.stream().map(Product::getId).collect(Collectors.toList());
        System.out.print(" masukan pilihan => ");
        int pilihan = scanner.nextInt();
        if (productsCode.contains((long) pilihan)){
            Product product = products.stream().filter(product1 -> product1.getId() == (long) pilihan).findFirst().orElse(null);
            if (product != null){
                this.detailProduct(sessionUser, product, merchant, id);
            }
        }else {
            switch (pilihan){
                case 96:
                    if (merchant.getIsOpen()){
                        merchant.setIsOpen(false);
                    }else {
                        merchant.setIsOpen(true);
                    }
                    if (merchantService.updateMerchantOpen(merchant)){
                        System.out.println("Berhasil!");
                    }else {
                        System.out.println("Gagal");
                    }
                    this.merchantDetailViewAdmin(sessionUser, id);
                    break;
                case 97:
                    tambahProdukViewAdmin(sessionUser, merchant);
                    break;
                case 98:
                    if (merchantService.deleteMerchantById(merchant)){
                        System.out.println("berhasil hapus merchant");
                    }else {
                        System.out.println("Gagal hapus merchant");
                    }
                    this.myMerchantView(sessionUser);
                    break;
                case 99:
                    this.myMerchantView(sessionUser);
                    break;
                case 0:
                    System.out.println("Aplikasi keluar...");
                    Runtime.getRuntime().halt(0);
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia");
                    this.myMerchantView(sessionUser);
            }
        }
    }

    private void tambahProdukViewAdmin(User user, Merchant merchant){
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Merchant : " + merchant.getName() +
                        "\nTambah produk"
        );

        try {
            scanner.nextLine();
            System.out.print("Nama produk : ");
            String nama = scanner.nextLine();
            System.out.print("Harga : ");
            int harga = scanner.nextInt();
            boolean success = merchantService.addProduct(merchant, nama, harga);
            if (success){
                System.out.println("Berhasil menambah produk baru");
            }else {
                System.out.println("Gagal menambah produk");
            }
            this.merchantDetailViewAdmin(user, merchant.getId());
        }catch (InputMismatchException e){
            System.out.println("Masukan inputan yang valid");
            this.tambahProdukViewAdmin(user, merchant);
        }
    }

    private void bukaMerchantViewNew(User user){
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Buka Merchant\n"
        );
        try{
            scanner.nextLine();
            System.out.print("Nama Merchant : ");
            String name = scanner.nextLine();
            System.out.print("Lokasi Merchant : ");
            String lokasi = scanner.nextLine();
            boolean isSucces = adminService.createAdmin(user, name, lokasi);
            if (isSucces){
                System.out.println("Berhasil membuat merchant");
                this.myMerchantView(user);
            }else {
                System.out.println("Gagal membuat merchant");
                this.myMerchantView(user);
            }
        }catch (InputMismatchException e){
            System.out.println("Input tidak valid");
            this.bukaMerchantViewNew(user);
        }
    }

    private void addMerchant(Admin admin, User user){
        System.out.println(
                "====================================================\n" +
                        "                    BINARFUD APP\n" +
                        "====================================================\n" +
                        "Buka Merchant Baru\n"
        );
        try{
            scanner.nextLine();
            System.out.print("Nama Merchant : ");
            String name = scanner.nextLine();
            System.out.print("Lokasi Merchant : ");
            String lokasi = scanner.nextLine();

            Merchant merchant = new Merchant();
            merchant.setAdmin(admin);
            merchant.setName(name);
            merchant.setIsOpen(false);
            merchant.setLocation(lokasi);

            admin.getMerchants().add(merchant);

            boolean isSucces = adminService.updateAdmin(admin);
            if (isSucces){
                System.out.println("Berhasil membuat merchant");
                this.myMerchantView(user);
            }else {
                System.out.println("Gagal membuat merchant");
                this.myMerchantView(user);
            }
        }catch (InputMismatchException e){
            System.out.println("Input tidak valid");
            this.bukaMerchantViewNew(user);
        }
    }

    public static void printStruk(Page<Order> orders) throws IOException {
        File file = new File(getFilename());
        if (file.createNewFile()) {
            System.out.println("Mencetak struk..");
            System.out.println("Struk berhasil dicetak.");
        }

        try(FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bwr = new BufferedWriter(fileWriter)) {

            bwr.write("===========================================\n");
            bwr.write("BinarFud\n");
            bwr.write("===========================================\n\n");
            bwr.write("Terima kasih sudah memesan di BinarFud\n\n");
            bwr.write("Dibawah ini adalah pesanan anda\n\n");

            AtomicInteger totalQty = new AtomicInteger();
            AtomicLong totalPrice = new AtomicLong();
            orders.forEach(order -> {
                try {
                    bwr.write(order.getOrderDetails().getProduct().getName()+ "   "+ order.getOrderDetails().getQuantity()+ "  "+order.getOrderDetails().getTotalPrice()+"\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                totalQty.addAndGet(order.getOrderDetails().getQuantity());
                totalPrice.addAndGet(order.getOrderDetails().getTotalPrice());
            });
            bwr.write("----------------------------------------+\n");
            bwr.write("Total"+"           "+totalQty+"  "+totalPrice+"\n\n\n");
            bwr.write("Pembayaran: BinarCash\n\n");
            bwr.write("===========================================\n");
            bwr.write("Simpan struk ini sebagai bukti pembayaran\n");
            bwr.write("===========================================");
            bwr.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("Terjadi kesalahan. gagal mencetak struk.");
        }
    }

    public static String getFilename() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime + " - Struk pembayaran.txt";
    }

}
