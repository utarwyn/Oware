package fr.ensicaen.oware.server;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@AllArgsConstructor
public class Capitalizer implements Runnable {

    private Socket socket;

    @Override
    public void run() {
        System.out.println(this.socket + " connected!");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());
        } catch (Exception e) {
            System.out.println("Error:" + this.socket);
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Closed: " + this.socket);
        }
    }
}
