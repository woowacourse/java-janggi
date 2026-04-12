package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Team;
import java.util.List;

public class FinishedState implements GameState {

    private final Team winner;

    public FinishedState(final Team winner) {
        this.winner = winner;
    }

    @Override
    public List<Position> getPiecePositions(final Board board) {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public GameState nextTurn() {
        throw new UnsupportedOperationException("게임이 종료되어 다음 턴이 존재하지 않습니다.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Team getTeam() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public Team getWinner() {
        return winner;
    }
}
