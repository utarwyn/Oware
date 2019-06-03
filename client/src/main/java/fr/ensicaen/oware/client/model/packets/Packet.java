package fr.ensicaen.oware.client.model.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Packet {

    public static final Gson gson = new GsonBuilder().create();

}
