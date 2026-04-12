package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Team;
import java.util.List;

public class ChoPlayingState extends PlayingState {

    private static final Team CURRENT_PLAYING_TEAM = Team.CHO;

    @Override
    public List<Position> getPiecePositions(final Board board) {
        return board.getPositionsByTeam(CURRENT_PLAYING_TEAM);
    }

    @Override
    public GameState nextTurn() {
        return new HanPlayingState();
    }

    @Override
    public Team getTeam() {
        return CURRENT_PLAYING_TEAM;
    }
}
