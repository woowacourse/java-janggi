package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.board.point.HanPoint;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Knight;
import java.util.Set;

public enum HanBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), Knight.newInstance(), Dynasty.HAN)
    )),
    OUTER_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), Elephant.newInstance(), Dynasty.HAN)
    )),
    RIGHT_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), Elephant.newInstance(), Dynasty.HAN)
    )),
    LEFT_ELEPHANT(Set.of(
            new BoardPiece(new HanPoint(1, 2), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 3), Knight.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 7), Elephant.newInstance(), Dynasty.HAN),
            new BoardPiece(new HanPoint(1, 8), Knight.newInstance(), Dynasty.HAN)
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
