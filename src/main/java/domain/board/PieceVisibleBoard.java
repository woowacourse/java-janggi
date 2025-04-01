package domain.board;

import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Path;
import domain.point.Point;

public interface PieceVisibleBoard {

    boolean existsPiece(final Point point);

    boolean existsNextPoint(final Point point, final Direction direction);

    Point getNextPoint(final Point point, final Direction direction);

    boolean existsPo(final Point point);

    boolean canMoveByPath(final Point point, final Path path);

    Point getPointMovedByPath(final Point point, final Path path);

    boolean matchTeam(final Point point, final Team team);
}
