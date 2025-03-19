package domain.piece;

import domain.Point;
import domain.Team;
import execptions.JanggiArgumentException;
import java.util.List;

public final class EmptyPiece implements Piece {
    @Override
    public boolean hasEqualTeam(Team team) {
        throw new JanggiArgumentException("기물이 없습니다.");
    }

    @Override
    public boolean isAbleToArrive(Point startPoint, Point arrivalPoint) {
        throw new JanggiArgumentException("기물이 없습니다.");
    }

    @Override
    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint) {
        throw new JanggiArgumentException("기물이 없습니다.");
    }

    @Override
    public boolean isMovable(PieceOnRoute pieceOnRoute) {
        throw new JanggiArgumentException("기물이 없습니다.");
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyPiece;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
