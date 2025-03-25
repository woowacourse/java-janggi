package domain.piece;

import static domain.board.Path.DOWN_DOWN_LEFT_PATH;
import static domain.board.Path.DOWN_DOWN_RIGHT_PATH;
import static domain.board.Path.DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_UP_PATH;
import static domain.board.Path.LEFT_PATH;
import static domain.board.Path.RIGHT_PATH;
import static domain.board.Path.RIGHT_RIGHT_DOWN_PATH;
import static domain.board.Path.RIGHT_RIGHT_UP_PATH;
import static domain.board.Path.UP_PATH;
import static domain.board.Path.UP_UP_LEFT_PATH;
import static domain.board.Path.UP_UP_RIGHT_PATH;

import domain.board.Movement;
import java.util.List;

public class Ma extends ObstacleSensitivePiece {

    public Ma(Team team) {
        super(team);
    }

    @Override
    public List<Movement> movements() {
        return List.of(
                new Movement(List.of(UP_PATH), UP_UP_LEFT_PATH),
                new Movement(List.of(UP_PATH), UP_UP_RIGHT_PATH),
                new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_UP_PATH),
                new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_DOWN_PATH),
                new Movement(List.of(DOWN_PATH), DOWN_DOWN_RIGHT_PATH),
                new Movement(List.of(DOWN_PATH), DOWN_DOWN_LEFT_PATH),
                new Movement(List.of(LEFT_PATH), LEFT_LEFT_DOWN_PATH),
                new Movement(List.of(LEFT_PATH), LEFT_LEFT_UP_PATH)
        );
    }

    @Override
    public PieceType type() {
        return PieceType.MA;
    }

    @Override
    public int score() {
        return 5;
    }
}
