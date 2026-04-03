package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;

public class Running extends Started {
    private static final String GAME_DOSE_NOT_FINISHED = "게임이 아직 끝나지 않아 결과를 집계할 수 없습니다.";

    protected Running(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);
        if (board.isFinished()) {
            return new Finished(board, turn.changeTeam());
        }
        return new Running(board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Running(board, turn.changeTeam());
    }

    @Override
    public Team judgeWinner() {
        throw new IllegalArgumentException(GAME_DOSE_NOT_FINISHED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
