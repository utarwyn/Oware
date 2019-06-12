package fr.ensicaen.oware.server.packets.datas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActionData implements IData {

    public static int ID = 1;

    private Action action;

    @Override
    public int getID() {
        return ID;
    }

    public enum Action {
        PLAY
    }

}
