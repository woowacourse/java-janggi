package domain.board.factory.elephantLocators;

import domain.board.Point;
import domain.pieces.Piece;
import domain.player.Player;
import java.util.Map;

public interface ElephantLocator {

    Map<Point, Piece> setupElephant(final Player player);

    Map<Point, Piece> setupHorse(final Player player);

}
