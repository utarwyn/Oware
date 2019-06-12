package fr.ensicaen.oware.server.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ensicaen.oware.server.packets.datas.IData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Packet {

    public static final Gson GSON = new GsonBuilder().create();

    private int id;

    private String serializedData;

    public Packet(IData data) {
        this.id = data.getID();
        this.serializedData = GSON.toJson(data, data.getClass());
    }

    public String serialize() {
        return GSON.toJson(this, Packet.class);
    }

    public <T extends IData> T deserializeData(Class<T> classType) {
        return GSON.fromJson(this.serializedData, classType);
    }

    @Override
    public String toString() {
        return this.serialize();
    }
}
