package fr.ensicaen.oware.client.model.packets;

import fr.ensicaen.oware.client.applications.Main;
import fr.ensicaen.oware.client.game.GameBoard;
import fr.ensicaen.oware.client.model.packets.datas.ActionData;
import fr.ensicaen.oware.client.model.packets.datas.GameBoardData;

import java.util.Arrays;

public class PacketHandler {

    private Main main;

    public PacketHandler(Main main) {
        this.main = main;
    }

    public void handle(String serializedPacket) {
        Packet packet = Packet.GSON.fromJson(serializedPacket, Packet.class);
        if (packet.getId() == ActionData.ID) {
            ActionData actionData = packet.deserializeData(ActionData.class);
            if (actionData.getAction() == ActionData.Action.PLAY) {
                System.out.println("PLAY");
            }
        } else if (packet.getId() == GameBoardData.ID) {
            GameBoardData gameBoardData = packet.deserializeData(GameBoardData.class);
            GameBoard gameBoard = new GameBoard(gameBoardData.getCurrentPlayerHoles(), gameBoardData.getOpponentHoles());
            System.out.println("Player board : " + Arrays.toString(gameBoard.getPlayerHoles()));
            System.out.println("Opponent player board : " + Arrays.toString(gameBoard.getOpponentHoles()));
        }
    }

}
