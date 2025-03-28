package janggiGame.arrangement;

import janggiGame.position.Position;
import janggiGame.piece.oneMovePiece.Advisor;
import janggiGame.piece.straightMovePiece.Cannon;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.curveMovePiece.Elephant;
import janggiGame.piece.curveMovePiece.Horse;
import janggiGame.piece.oneMovePiece.King;
import janggiGame.piece.oneMovePiece.Pawn;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class RightElephantStrategy extends ArrangementStrategy {
    @Override
    public Map<Position, Piece> arrangeCho(Dynasty dynasty) {
        Map<Position, Piece> result = new HashMap<>();

        result.put(Position.getInstanceBy(0, 0), new Chariot(dynasty));
        result.put(Position.getInstanceBy(1, 0), new Horse(dynasty));
        result.put(Position.getInstanceBy(2, 0), new Elephant(dynasty));
        result.put(Position.getInstanceBy(3, 0), new Advisor(dynasty));

        result.put(Position.getInstanceBy(5, 0), new Advisor(dynasty));
        result.put(Position.getInstanceBy(6, 0), new Horse(dynasty));
        result.put(Position.getInstanceBy(7, 0), new Elephant(dynasty));
        result.put(Position.getInstanceBy(8, 0), new Chariot(dynasty));

        result.put(Position.getInstanceBy(4, 1), new King(dynasty));

        result.put(Position.getInstanceBy(1, 2), new Cannon(dynasty));
        result.put(Position.getInstanceBy(7, 2), new Cannon(dynasty));

        result.put(Position.getInstanceBy(0, 3), new Pawn(dynasty));
        result.put(Position.getInstanceBy(2, 3), new Pawn(dynasty));
        result.put(Position.getInstanceBy(4, 3), new Pawn(dynasty));
        result.put(Position.getInstanceBy(6, 3), new Pawn(dynasty));
        result.put(Position.getInstanceBy(8, 3), new Pawn(dynasty));

        return result;
    }
}
