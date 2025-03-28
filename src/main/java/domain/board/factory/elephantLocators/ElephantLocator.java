package domain.board.factory.elephantLocators;

import domain.board.Point;
import domain.pieces.Piece;
import domain.player.Team;
import java.util.Map;

public interface ElephantLocator {

    Map<Point, Piece> setupElephant(final Team team);

    Map<Point, Piece> setupHorse(final Team team);

}
