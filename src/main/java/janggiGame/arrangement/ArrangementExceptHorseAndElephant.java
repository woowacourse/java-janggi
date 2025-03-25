package janggiGame.arrangement;

import janggiGame.Position;
import janggiGame.piece.Advisor;
import janggiGame.piece.Cannon;
import janggiGame.piece.Chariot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.King;
import janggiGame.piece.Pawn;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class ArrangementExceptHorseAndElephant implements ArrangementStrategy {
    @Override
    public Map<Position, Piece> arrange(Dynasty dynasty) {
        Map<Position, Piece> result = new HashMap<>();

        result.put(Position.of(0, 0), new Chariot(dynasty));
        result.put(Position.of(8, 0), new Chariot(dynasty));

        result.put(Position.of(3, 0), new Advisor(dynasty));
        result.put(Position.of(5, 0), new Advisor(dynasty));

        result.put(Position.of(4, 1), new King(dynasty));

        result.put(Position.of(1, 2), new Cannon(dynasty));
        result.put(Position.of(7, 2), new Cannon(dynasty));

        result.put(Position.of(0, 3), new Pawn(dynasty));
        result.put(Position.of(2, 3), new Pawn(dynasty));
        result.put(Position.of(4, 3), new Pawn(dynasty));
        result.put(Position.of(6, 3), new Pawn(dynasty));
        result.put(Position.of(8, 3), new Pawn(dynasty));

        return result;
    }
}
