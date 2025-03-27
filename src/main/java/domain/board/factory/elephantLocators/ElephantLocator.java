package domain.board.factory.elephantLocators;

import domain.board.Point;
import domain.pieces.Piece;
import domain.player.TeamType;
import java.util.Map;

public interface ElephantLocator {

    Map<Point, Piece> setupElephant(final TeamType teamType);

    Map<Point, Piece> setupHorse(final TeamType teamType);

}
