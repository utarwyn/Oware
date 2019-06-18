package fr.ensicaen.oware.server.net.packets;

import fr.ensicaen.oware.server.net.Packet;

public class HoleActionPacket extends Packet {

    private int position;

    @Override
    public void onReceive() {
        this.server.getGame().play(this.capitalizer.getPlayer(), this.position);
    }

}
