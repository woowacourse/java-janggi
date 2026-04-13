package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Team;
import java.util.List;

public interface GameState {

    List<Position> getPiecePositions(final Board board);

    GameState nextTurn();

    boolean isFinished();

    Team getTeam();

    Team getWinner();
}
