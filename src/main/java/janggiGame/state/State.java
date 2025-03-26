package janggiGame.state;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;

public interface State {
    State arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy);
    State takeTurn(Dot origin, Dot destination);
    State skipTurn();
    boolean isFinished();
    GameResult getGameResult();
    GameScore getGameScore();
    Map<Dot, Piece> getPieces();
    Dynasty getCurrentDynasty();
}
