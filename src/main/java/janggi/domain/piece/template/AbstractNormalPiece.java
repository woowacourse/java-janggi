package janggi.domain.piece.template;

import static java.lang.Math.abs;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.direction.CastleDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.List;

public abstract class AbstractNormalPiece implements Piece {

    private static final int MAX_DISTANCE = 1;

    private final Team team;
    private final PieceType type;
    private final int score;

    public AbstractNormalPiece(int score, Team team, PieceType type) {
        this.team = team;
        this.type = type;
        this.score = score;
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        if (!from.inSameCastle(to)) {
            throw new IllegalArgumentException("[ERROR] 궁성 밖으로 나갈 수 없습니다.");
        }
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int distanceCol = abs(pathCol);
        int distanceRow = abs(pathRow);
        validateDistance(distanceCol, distanceRow);
        CastleDirection direction = CastleDirection.find(from, pathCol, pathRow);
        Point point = Point.of(from.getColumn() + direction.getTargetCol(), from.getRow() + direction.getTargetRow());
        return new Points(List.of(point));
    }

    @Override
    public boolean canMove(Route route) {
        return !route.hasAlly(team);
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    @Override
    public PieceType getType() {
        return type;
    }

    @Override
    public int getScore() {
        return score;
    }

    private void validateDistance(int distanceCol, int distanceRow) {
        if (distanceCol > MAX_DISTANCE || distanceRow > MAX_DISTANCE || (distanceCol == 0 && distanceRow == 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
    }
}
