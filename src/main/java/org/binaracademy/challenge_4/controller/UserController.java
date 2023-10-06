package org.binaracademy.challenge_4.controller;

import org.binaracademy.challenge_4.entity.User;
import org.binaracademy.challenge_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.InputMismatchException;
import java.util.Scanner;

@Controller
public class UserController {
    public User sessionUser;
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private UserService userService;

    public void authenticateView(){
        System.out.println(
                "=================================================\n" +
                        "                SELAMAT DATANG\n" +
                        "                 BINARFUD APP\n" +
                        "=================================================\n\n" +
                        "     1. Login\n" +
                        "     2. Register\n" +
                        "     0. Keluar\n"
        );

        try {
            System.out.print("  masukan pilihan => ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();
            switch (pilihan){
                case 1:
                    loginview();
                    break;
                case 2:
                    registerView();
                    break;
                case 0:
                    System.out.println("Aplikasi keluar...");
                    Runtime.getRuntime().halt(0);
                default:
                    System.out.println("Pilihan tidak ditemukan!");
                    authenticateView();
            }
        }catch (InputMismatchException e){
            System.out.println("Masukan inputan yang valid");
            authenticateView();
        }

    }

    public void loginview(){
        System.out.println(
                "=================================================\n" +
                        "                 BINARFUD APP\n" +
                        "        Silahkan login terlebih dahulu\n" +
                        "=================================================\n"
        );
        try {
            System.out.print("   Email : ");
            String email = scanner.nextLine();
            System.out.print("   Password : ");
            String password = scanner.nextLine();
            User user = login(email, password);
            if (user != null){
                sessionUser = user;
            }else {
                System.out.println("email atau password salah.");
                authenticateView();
            }
        }catch (InputMismatchException e){
            System.out.println("Silahkan inputkan data yang valid.");
            loginview();
        }
    }

    private User login(String email, String password){
        return userService.login(email, password);
    }

    public void registerView(){
        System.out.println(
                "=================================================\n" +
                        "                 BINARFUD APP\n" +
                        "        Silahkan register terlebih dahulu\n" +
                        "=================================================\n"
        );
        try {
            System.out.print("   Username : ");
            String username = scanner.nextLine();
            System.out.print("   Email : ");
            String email = scanner.nextLine();
            System.out.print("   Password : ");
            String password = scanner.nextLine();
            if (register(username, email, password)){
                loginview();
            }else {
                System.out.println("Email sudah digunakan.");
                authenticateView();
            }
        }catch (InputMismatchException e){
            System.out.println("Silahkan inputkan data yang valid.");
            registerView();
        }
    }

    private boolean register(String username, String email, String password){
        return userService.register(username, email, password);
    }


}
