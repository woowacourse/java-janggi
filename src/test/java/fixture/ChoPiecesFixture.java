package fixture;

import direction.Point;
import java.util.List;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.Soldier;
import team.Team;

public class ChoPiecesFixture {

    public static final List<Piece> pieces = List.of(
            new Chariot("c", new Point(1, 10)),
            new Chariot("c", new Point(9, 10)),
            new Elephant("e", new Point(2, 10)),
            new Elephant("e", new Point(7, 10)),
            new Horse("h", new Point(3, 10)),
            new Horse("h", new Point(8, 10)),
            new Guard("r", new Point(4, 10)),
            new Guard("r", new Point(6, 10)),
            new General("g", new Point(5, 9)),
            new Cannon("n", new Point(2, 8)),
            new Cannon("n", new Point(8, 8)),
            new Soldier("s", new Point(1, 7), Team.CHO),
            new Soldier("s", new Point(3, 7), Team.CHO),
            new Soldier("s", new Point(5, 7), Team.CHO),
            new Soldier("s", new Point(7, 7), Team.CHO),
            new Soldier("s", new Point(9, 7), Team.CHO)
    );
}
