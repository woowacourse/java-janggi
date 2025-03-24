package move;

import direction.Point;
import java.util.List;
import piece.Piece;
import piece.Pieces;
import team.Team;

public class TestFixture {

    List<Piece> greenPieces = List.of(
            new Piece("c", new Point(1, 10), new ChariotMovement()),
            new Piece("c", new Point(9, 10), new ChariotMovement()),
            new Piece("e", new Point(2, 10), new ElephantMovement(Team.GREEN.direction())),
            new Piece("e", new Point(7, 10), new ElephantMovement(Team.GREEN.direction())),
            new Piece("h",new Point(3, 10), new HorseMovement(Team.GREEN.direction())),
            new Piece("h",new Point(8, 10), new HorseMovement(Team.GREEN.direction())),
            new Piece("r",new Point(4, 10), new GuardMovement()),
            new Piece("r",new Point(6, 10), new GuardMovement()),
            new Piece("g",new Point(5, 9), new GeneralMovement()),
            new Piece("n", new Point(2, 8), new CannonMovement()),
            new Piece("n", new Point(8, 8), new CannonMovement()),
            new Piece("s", new Point(1, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(3, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(5, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(7, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(9, 7), new SoldierMovement(Team.GREEN.direction()))
    );

    Pieces pieces = new Pieces(greenPieces);
}
