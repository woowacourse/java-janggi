package domain.turn;

import domain.piece.Board;
import domain.TeamType;
import domain.position.Position;
import java.util.Map;

public abstract class Finished extends Turn {

    protected Finished(Board board, TurnState turnState) {
        super(board, new TurnState(turnState.undoLast(), turnState.playerTeam().otherTeam()));
    }

    public abstract boolean isFinishedByCheckmate();

    public abstract GameState getGameState();

    @Override
    public Turn undo() {
        throw new IllegalStateException("게임이 종료된 상태에서는 무르기를 요청할 수 없습니다.");
    }

    @Override
    public Turn movePiece(Position moveFrom, Position moveTo) {
        throw new IllegalStateException("게임이 종료된 상태에서는 기물을 이동할 수 없습니다.");
    }

    @Override
    public Map<TeamType, Double> calculateTeamScore() {
        return board.calculateTeamScore();
    }

    @Override
    public TeamType findWinTeam() {
        if (!board.isInProgress()) {
            return board.findWinTeam();
        }

        return findGreatestScoreTeam(calculateTeamScore());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Finished getFinished() {
        return this;
    }

    private TeamType findGreatestScoreTeam(Map<TeamType, Double> teamScore) {
        return teamScore.entrySet().stream()
                .reduce((e1, e2) -> e1.getValue() >= e2.getValue() ? e1 : e2)
                .orElseThrow(() -> new IllegalStateException("팀이 존재하지 않습니다."))
                .getKey();
    }
}
