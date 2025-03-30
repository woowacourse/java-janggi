package janggiGame.state;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import java.util.Map;

public interface State {
    State arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy);

    State takeTurn(Position origin, Position destination);

    State skipTurn();

    boolean isFinished();

    GameResult getGameResult();

    GameScore getGameScore();

    Map<Position, Piece> getPieces();

    Dynasty getCurrentDynasty();

    boolean wasLastTurnPassed();
}
