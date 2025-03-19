package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Knight;
import janggi.domain.piece.PointPiece;
import java.util.Set;

public enum ChuBoardSetUp implements BoardSetUp {
    INNER_ELEPHANT(Set.of(
            new PointPiece(new ChuPoint(10, 2), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 3), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 7), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 8), Knight.newInstance(), Dynasty.CHU)
    )),
    OUTER_ELEPHANT(Set.of(
            new PointPiece(new ChuPoint(10, 2), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 3), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 7), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 8), Elephant.newInstance(), Dynasty.CHU)
    )),
    RIGHT_ELEPHANT(Set.of(
            new PointPiece(new ChuPoint(10, 2), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 3), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 7), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 8), Elephant.newInstance(), Dynasty.CHU)
    )),
    LEFT_ELEPHANT(Set.of(
            new PointPiece(new ChuPoint(10, 2), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 3), Knight.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 7), Elephant.newInstance(), Dynasty.CHU),
            new PointPiece(new ChuPoint(10, 8), Knight.newInstance(), Dynasty.CHU)
    ));

    private final Set<PointPiece> piecePositions;

    ChuBoardSetUp(Set<PointPiece> piecePositions) {
        this.piecePositions = piecePositions;
    }

    @Override
    public Set<PointPiece> getPiecePositions() {
        return piecePositions;
    }
}
