package board;

import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.PieceType;

public class Board {

    private final Map<Position, Piece> map;

    public Board(final Map<Position, Piece> map) {
        this.map = map;
    }

    public boolean existPieceByPosition(final Position existPosition) {
        return map.containsKey(existPosition);
    }

    public boolean isCannonByPosition(final Position position){
        if(map.containsKey(position)){
            final Piece piece = map.get(position);
            return piece.equalsPieceType(PieceType.CANNON);
        }
        return false;
    }

    public Map<Position, Piece> getMap() {
        return map;
    }
}
