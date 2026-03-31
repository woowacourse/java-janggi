package domain.board;

import domain.coordination.Coordination;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public abstract class PlacementStrategy {

    public Map<Coordination, Piece> place() {
        Map<Coordination, Piece> map = new HashMap<>();
        placeCommon(map);
        placeHorseAndElephant(map);
        return map;
    }

    protected abstract void placeCommon(Map<Coordination, Piece> map);

    protected abstract void placeHorseAndElephant(Map<Coordination, Piece> map);
}
