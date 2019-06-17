package fr.ensicaen.oware.client.net;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * A Gson type adapter for packet serialization/deserialization.
 * It is used by the Gson library to create packet instances at runtime or to serialize them.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class PacketTypeAdapter implements JsonSerializer<Packet>, JsonDeserializer<Packet> {

    /**
     * Json attribute name for storing the packet class name
     */
    private static final String PACKET = "packet";

    /**
     * Json attribute name for storing the packet data
     */
    private static final String DATA = "data";

    /**
     * Serialize a packet into a json object.
     *
     * @param packet  Packet to serialize
     * @param typeOf  Type of packet to serialize
     * @param context Json serialization context
     * @return Json object with the packet details
     */
    @Override
    public JsonElement serialize(Packet packet, Type typeOf, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add(PACKET, new JsonPrimitive(packet.getClass().getSimpleName()));
        result.add(DATA, context.serialize(packet, packet.getClass()));

        return result;
    }

    /**
     * Deserialize a json object into a Packet instance.
     *
     * @param json    Json element to convert into a Packet instance
     * @param typeOf  Type of object to be deserialized
     * @param context Json deserialization context
     * @return A packet instance created from the Json object
     */
    @Override
    public Packet deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        String packetName = jsonObject.get(PACKET).getAsString();

        try {
            return context.deserialize(jsonObject.get(DATA), Class.forName(Packet.class.getPackage().getName() + ".packets." + packetName));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + packetName, cnfe);
        }
    }

}
