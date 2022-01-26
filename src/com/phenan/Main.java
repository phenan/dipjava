package com.phenan;

import com.phenan.application.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.fetchUserName(1).thenAccept(nameOpt -> {
            System.out.println(nameOpt.orElse("not found"));
        });
    }
}
