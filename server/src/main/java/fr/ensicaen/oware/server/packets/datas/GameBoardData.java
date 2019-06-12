package fr.ensicaen.oware.server.packets.datas;

import fr.ensicaen.oware.server.game.Hole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameBoardData implements IData {

    public static int ID = 2;

    private Hole[] currentPlayerHoles;
    private Hole[] opponentHoles;

    @Override
    public int getID() {
        return ID;
    }

}
