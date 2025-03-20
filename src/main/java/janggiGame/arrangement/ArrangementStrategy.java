package janggiGame.arrangement;

import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;

public interface ArrangementStrategy {
    Map<Dot, Piece> arrange(Dynasty dynasty);
}
