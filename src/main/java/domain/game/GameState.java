package domain.game;

import domain.JanggiPosition;
import domain.Score;
import domain.piece.Piece;
import java.util.Map;

public interface GameState {
    GameState start();

    GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition);

    boolean isEnd();

    Map<JanggiPosition, Piece> getBoard();

    Player getCurrentPlayer();

    Score getScore();
}
