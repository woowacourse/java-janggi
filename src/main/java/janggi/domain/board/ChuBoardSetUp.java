package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Knight;
import java.util.Set;

public enum ChuBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Set.of(
            new BoardPiece(new ChuPoint(10, 2), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 3), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 7), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 8), new Knight(), Dynasty.CHU)
    )),
    OUTER_ELEPHANT(Set.of(
            new BoardPiece(new ChuPoint(10, 2), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 3), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 7), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 8), new Elephant(), Dynasty.CHU)
    )),
    RIGHT_ELEPHANT(Set.of(
            new BoardPiece(new ChuPoint(10, 2), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 3), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 7), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 8), new Elephant(), Dynasty.CHU)
    )),
    LEFT_ELEPHANT(Set.of(
            new BoardPiece(new ChuPoint(10, 2), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 3), new Knight(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 7), new Elephant(), Dynasty.CHU),
            new BoardPiece(new ChuPoint(10, 8), new Knight(), Dynasty.CHU)
    ));

    private final Set<BoardPiece> piecePositions;

    ChuBoardSetUp(Set<BoardPiece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Set<BoardPiece> getPiecePositions() {
        return piecePositions;
    }
}
