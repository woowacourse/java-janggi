package domain.piece;

import static domain.point.Path.DOWN_DOWN_DOWN_LEFT_LEFT_PATH;
import static domain.point.Path.DOWN_DOWN_DOWN_RIGHT_RIGHT_PATH;
import static domain.point.Path.DOWN_DOWN_LEFT_PATH;
import static domain.point.Path.DOWN_DOWN_RIGHT_PATH;
import static domain.point.Path.DOWN_PATH;
import static domain.point.Path.LEFT_LEFT_DOWN_PATH;
import static domain.point.Path.LEFT_LEFT_LEFT_DOWN_DOWN_PATH;
import static domain.point.Path.LEFT_LEFT_LEFT_UP_UP_PATH;
import static domain.point.Path.LEFT_LEFT_UP_PATH;
import static domain.point.Path.LEFT_PATH;
import static domain.point.Path.RIGHT_PATH;
import static domain.point.Path.RIGHT_RIGHT_DOWN_PATH;
import static domain.point.Path.RIGHT_RIGHT_RIGHT_DOWN_DOWN_PATH;
import static domain.point.Path.RIGHT_RIGHT_RIGHT_UP_UP_PATH;
import static domain.point.Path.RIGHT_RIGHT_UP_PATH;
import static domain.point.Path.UP_PATH;
import static domain.point.Path.UP_UP_LEFT_PATH;
import static domain.point.Path.UP_UP_RIGHT_PATH;
import static domain.point.Path.UP_UP_UP_LEFT_LEFT_PATH;
import static domain.point.Path.UP_UP_UP_RIGHT_RIGHT_PATH;

import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Movement;
import java.util.List;

public class Sang extends ObstacleSensitivePiece {

    public Sang(Team team) {
        super(team);
    }

    @Override
    public List<Movement> movements() {
        return List.of(
                new Movement(List.of(UP_PATH, UP_UP_LEFT_PATH), UP_UP_UP_LEFT_LEFT_PATH),
                new Movement(List.of(UP_PATH, UP_UP_RIGHT_PATH), UP_UP_UP_RIGHT_RIGHT_PATH),
                new Movement(List.of(RIGHT_PATH, RIGHT_RIGHT_UP_PATH), RIGHT_RIGHT_RIGHT_UP_UP_PATH),
                new Movement(List.of(RIGHT_PATH, RIGHT_RIGHT_DOWN_PATH), RIGHT_RIGHT_RIGHT_DOWN_DOWN_PATH),
                new Movement(List.of(DOWN_PATH, DOWN_DOWN_RIGHT_PATH), DOWN_DOWN_DOWN_RIGHT_RIGHT_PATH),
                new Movement(List.of(DOWN_PATH, DOWN_DOWN_LEFT_PATH), DOWN_DOWN_DOWN_LEFT_LEFT_PATH),
                new Movement(List.of(LEFT_PATH, LEFT_LEFT_DOWN_PATH), LEFT_LEFT_LEFT_DOWN_DOWN_PATH),
                new Movement(List.of(LEFT_PATH, LEFT_LEFT_UP_PATH), LEFT_LEFT_LEFT_UP_UP_PATH)
        );
    }

    @Override
    public boolean isOnlyMovableInPalace() {
        return false;
    }

    @Override
    public PieceType type() {
        return PieceType.SANG;
    }

    @Override
    public int score() {
        return 3;
    }
}
