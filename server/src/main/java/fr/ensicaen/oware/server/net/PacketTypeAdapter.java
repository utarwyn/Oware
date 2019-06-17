package fr.ensicaen.oware.server.net;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PacketTypeAdapter implements JsonSerializer<Packet>, JsonDeserializer<Packet> {

    private static final String PACKET = "packet";

    private static final String DATA = "data";

    @Override
    public JsonElement serialize(Packet packet, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add(PACKET, new JsonPrimitive(packet.getClass().getSimpleName()));
        result.add(DATA, context.serialize(packet, packet.getClass()));

        return result;
    }

    @Override
    public Packet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        String packetName = jsonObject.get(PACKET).getAsString();

        try {
            return context.deserialize(jsonObject.get(DATA), Class.forName(Packet.class.getPackage().getName() + ".packets." + packetName));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + packetName, cnfe);
        }
    }

}
