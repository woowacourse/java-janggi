package janggiGame.arrangement;

import janggiGame.Position;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;

public interface ArrangementStrategy {
    Map<Position, Piece> arrange(Dynasty dynasty);
}
