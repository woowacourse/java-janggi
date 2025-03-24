package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> janggiBoard = new HashMap<>();

    public Board(final List<Piece> pieces) {
        janggiBoard.putAll(
                pieces.stream()
                        .collect((Collectors.toMap(Piece::getBoardPosition, piece -> piece))
                        ));
    }

    public void pieceMove(final Position presentPosition, final Position futurePosition) {
        final Piece piece = janggiBoard.get(presentPosition);
        piece.isMove(futurePosition);
        piece.validateSameNation(janggiBoard.get(futurePosition));
        piece.checkObstacle(futurePosition, janggiBoard);
        updatePiecePosition(presentPosition, futurePosition, piece);
    }

    private void updatePiecePosition(final Position presentPosition, final Position futurePosition, final Piece piece) {
        janggiBoard.remove(presentPosition);
        janggiBoard.put(futurePosition, piece);
        piece.updatePiecePositionBy(futurePosition);
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

}
