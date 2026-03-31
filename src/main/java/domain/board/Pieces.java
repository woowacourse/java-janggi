package domain.board;

import domain.piece.Piece;
import domain.setup.Arrangements;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static Pieces of(Arrangements arrangements) {
        return new Pieces(PositionLayout.build(arrangements));
    }

    public Pieces move(Position from, Position to) {
        Piece piece = pieces.get(from);
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다: " + from);
        }
        Map<Position, Piece> newMap = new HashMap<>(pieces);
        newMap.remove(from);
        newMap.put(to, piece);
        return new Pieces(newMap);
    }

    public Optional<Piece> at(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }
}
