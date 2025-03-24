package domain.board.factory.elephantLocators;

import domain.Team;
import domain.board.Point;
import domain.pieces.Piece;
import java.util.Map;

public interface ElephantLocator {

    Map<Point, Piece> setupElephant(final Team team);

    Map<Point, Piece> setupHorse(final Team team);

}
