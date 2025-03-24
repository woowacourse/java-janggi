package janggiGame.arrangement;

import janggiGame.board.Board;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public abstract class ArrangementStrategy {
    public Map<Dot, Piece> arrangeHan(Dynasty dynasty) {
            Map<Dot, Piece> oppositeResult = arrangeCho(dynasty);
            Map<Dot, Piece> result = new HashMap<>();
            oppositeResult.keySet()
                    .forEach(dot -> result.put(Board.getReverse(dot), oppositeResult.get(dot)));
            return result;
        }

    public abstract Map<Dot, Piece> arrangeCho(Dynasty dynasty);

}

