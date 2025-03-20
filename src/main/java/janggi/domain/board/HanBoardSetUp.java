package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.board.point.HanPoint;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import java.util.Set;

public enum HanBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), new Horse(), Dynasty.HAN)
    )),
    OUTER_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), new Elephant(), Dynasty.HAN)
    )),
    RIGHT_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), new Elephant(), Dynasty.HAN)
    )),
    LEFT_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), new Horse(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), new Elephant(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), new Horse(), Dynasty.HAN)
    ));

    private final Set<BoardPiece> piecePositions;

    HanBoardSetUp(Set<BoardPiece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Set<BoardPiece> getPiecePositions() {
        return piecePositions;
    }
}
