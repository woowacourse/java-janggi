package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;

public class Finished extends Started {
    private final static String GAME_HAS_BEEN_FINISHED = "게임이 끝나 더 이상 진행할 수 없습니다.";

    public Finished(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        throw new IllegalStateException(GAME_HAS_BEEN_FINISHED);
    }

    @Override
    public JanggiGame pass() {
        throw new IllegalStateException(GAME_HAS_BEEN_FINISHED);
    }

    @Override
    public Team judgeWinner() {
        return board.judgeResult();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
