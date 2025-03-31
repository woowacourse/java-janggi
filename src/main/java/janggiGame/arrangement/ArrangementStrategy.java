package janggiGame.arrangement;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class ArrangementStrategy {
    public Map<Position, Piece> arrangeHan(Dynasty dynasty) {
        Map<Position, Piece> oppositeResult = arrangeCho(dynasty);
        Map<Position, Piece> result = new HashMap<>();
        oppositeResult.keySet()
                .forEach(dot -> result.put(dot.getReverse(), oppositeResult.get(dot)));
        return result;
    }

    public abstract Map<Position, Piece> arrangeCho(Dynasty dynasty);

}
