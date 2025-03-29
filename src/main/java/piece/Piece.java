package piece;

import game.Team;
import location.Position;

public interface Piece {
    void validateDestination(Position destination);

    void validatePaths(Pieces pieces, Position destination);

    void move(Position destination);

    void catchByOpponent();

    boolean isPlacedAt(Position targetPosition);

    int getId();

    Team getTeam();

    Position getCurrentPosition();

    PieceType getPieceType();

    int getScore();

    boolean isCatch();
}
