package fr.ensicaen.oware.client.model.packets.datas;

import fr.ensicaen.oware.client.game.Hole;
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
