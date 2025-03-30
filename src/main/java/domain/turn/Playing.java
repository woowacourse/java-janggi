package domain.turn;

import domain.piece.Board;
import domain.TeamType;
import domain.position.Position;
import java.util.Map;

public class Playing extends Turn {

    public Playing(Board board, TurnState turnState) {
        super(board, turnState);
    }

    @Override
    public Turn undo() {
        if (turnState.undoLast()) {
            return new ScoreFinished(board, new TurnState(true, getNextPlayerTeam()));
        }
        return new Playing(board, new TurnState(true, getNextPlayerTeam()));
    }

    @Override
    public Turn movePiece(Position moveFrom, Position moveTo) {
        board.movePiece(moveFrom, moveTo, turnState.playerTeam());
        if (board.isInProgress()) {
            return new Playing(board, new TurnState(false, getNextPlayerTeam()));
        }
        return new CheckmateFinished(board, new TurnState(false, getNextPlayerTeam()));
    }

    @Override
    public Map<TeamType, Double> calculateTeamScore() {
        throw new IllegalStateException("현재 게임이 진행중입니다. 점수를 계산할 수 없습니다.");
    }

    @Override
    public TeamType findWinTeam() {
        throw new IllegalStateException("현재 게임이 진행중입니다. 우승자를 판별할 수 없습니다.");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public GameState getGameState() {
        return GameState.IN_PROGRESS;
    }

    @Override
    public Finished getFinished() {
        throw new IllegalStateException("현재 게임이 진행중입니다.");
    }
}
