import janggi.Board;
import janggi.Team;
import janggi.piece.*;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Piece> pieces = new ArrayList<>();

        // TODO move to Players Class
        for (Team team : Team.values()) {
            pieces.add(General.Default(team));
            pieces.addAll(Guard.Default(team));
            pieces.addAll(Soldier.Default(team));
            pieces.addAll(Horse.Default(team));
            pieces.addAll(Elephant.Default(team));
            pieces.addAll(Chariot.Default(team));
            pieces.addAll(Cannon.Default(team));
        }

        // TODO move to JanggiGame Class
        Board.initialize(pieces);
    }
}
