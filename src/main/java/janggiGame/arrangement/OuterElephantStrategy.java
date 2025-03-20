package janggiGame.arrangement;

import janggiGame.board.Board;
import janggiGame.board.Dot;
import janggiGame.piece.Advisor;
import janggiGame.piece.Cannon;
import janggiGame.piece.Chariot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Elephant;
import janggiGame.piece.Horse;
import janggiGame.piece.King;
import janggiGame.piece.Pawn;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class OuterElephantStrategy implements ArrangementStrategy {
    @Override
    public Map<Dot, Piece> arrange(Dynasty dynasty) {
        Map<Dot, Piece> result = new HashMap<>();

        result.put(Board.findBy(0, 0), new Chariot(dynasty));
        result.put(Board.findBy(1, 0), new Elephant(dynasty));
        result.put(Board.findBy(2, 0), new Horse(dynasty));
        result.put(Board.findBy(3, 0), new Advisor(dynasty));

        result.put(Board.findBy(5, 0), new Advisor(dynasty));
        result.put(Board.findBy(6, 0), new Horse(dynasty));
        result.put(Board.findBy(7, 0), new Elephant(dynasty));
        result.put(Board.findBy(8, 0), new Chariot(dynasty));

        result.put(Board.findBy(4, 1), new King(dynasty));

        result.put(Board.findBy(1, 2), new Cannon(dynasty));
        result.put(Board.findBy(7, 2), new Cannon(dynasty));

        result.put(Board.findBy(0, 3), new Pawn(dynasty));
        result.put(Board.findBy(2, 3), new Pawn(dynasty));
        result.put(Board.findBy(4, 3), new Pawn(dynasty));
        result.put(Board.findBy(6, 3), new Pawn(dynasty));
        result.put(Board.findBy(8, 3), new Pawn(dynasty));

        return result;
    }
}
