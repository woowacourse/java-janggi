package game.arrangement;

import game.Dot;
import piece.Dynasty;
import piece.Piece;

import java.util.Map;

public interface ArrangementStrategy {
    Map<Dot, Piece> arrange(Dynasty dynasty);
}
