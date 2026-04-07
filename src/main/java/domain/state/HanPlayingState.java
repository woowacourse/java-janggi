package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Team;
import java.util.List;

public class HanPlayingState implements GameState {

    private static final Team CURRENT_PLAYING_TEAM = Team.HAN;

    @Override
    public List<Position> getPiecePositions(Board board) {
        return board.getPositionsByTeam(CURRENT_PLAYING_TEAM);
    }

    @Override
    public GameState nextTurn() {
        return new ChoPlayingState();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Team getTeam() {
        return CURRENT_PLAYING_TEAM;
    }
}
