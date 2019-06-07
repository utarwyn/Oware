package fr.ensicaen.oware.server.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

@Getter
public class Packet {

    public static final Gson gson = new GsonBuilder().create();

    public String serialize() {
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return this.serialize();
    }
}
