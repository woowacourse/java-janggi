package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;

public interface GameState {
    GameState start();

    GameState playSingleTurn(JanggiPosition beforePosition, JanggiPosition afterPosition);

    boolean isEnd();

    Map<JanggiPosition, Piece> getBoard();

    Player getCurrentPlayer();

    int getChoScore();

    int getHanScore();
}
