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
            pieces.add(General.defaultOf(team));
            pieces.addAll(Guard.defaultsOf(team));
            pieces.addAll(Soldier.defaultsOf(team));
            pieces.addAll(Horse.defaultsOf(team));
            pieces.addAll(Elephant.defaultsOf(team));
            pieces.addAll(Chariot.defaultsOf(team));
            pieces.addAll(Cannon.defaultsOf(team));
        }

        // TODO move to JanggiGame Class
        Board.from(pieces);
    }
}
