package domain.board;

import domain.piece.Team;

public interface PieceVisibleBoard {

    boolean existsPiece(Point point);

    boolean existsNextPoint(Point point, Direction direction);

    Point getNextPoint(Point point, Direction direction);

    boolean existsPo(Point point);

    boolean canMoveByPath(Point point, Path path);

    Point getPointMovedByPath(Point point, Path path);

    boolean matchTeam(Point point, Team team);
}
