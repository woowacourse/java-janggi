package domain.state;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import java.util.Map;

public interface JanggiGame {
    JanggiGame move(Position start, Position destination);

    JanggiGame pass();

    Team judgeWinner();

    Map<Position, Piece> getBoard();

    Team getTurn();

    boolean isFinished();

    double getScoreByTeam(Team team);

    State getState();
}
