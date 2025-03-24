package position;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Set;
import piece.Blank;
import piece.Piece;
import piece.PieceType;

public final class Board {
    Map<Position, Piece> pieceOfPosition;

    public Board(Set<Piece> pieces) {
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Piece get(final Position position) {
        return pieceOfPosition.getOrDefault(position, new Blank(position));
    }

    public boolean isBlank(Position position) {
        return get(position).type() == PieceType.BLANK;
    }
}
