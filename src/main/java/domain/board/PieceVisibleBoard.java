package domain.board;

import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Path;
import domain.point.Point;

public interface PieceVisibleBoard {

    boolean existsPiece(Point point);

    boolean existsNextPoint(Point point, Direction direction);

    Point getNextPoint(Point point, Direction direction);

    boolean existsPo(Point point);

    boolean canMoveByPath(Point point, Path path);

    Point getPointMovedByPath(Point point, Path path);

    boolean matchTeam(Point point, Team team);
}
