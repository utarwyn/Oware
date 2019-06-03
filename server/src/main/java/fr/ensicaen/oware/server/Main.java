package fr.ensicaen.oware.server;

import fr.ensicaen.oware.server.sockets.CapitalizeServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Server is running!");
        new CapitalizeServer(59898).listen();
    }

}