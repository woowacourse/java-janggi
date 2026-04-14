package domain.board;

import domain.coordinate.Position;
import domain.coordinate.Topology;
import domain.Side;
import domain.piece.Piece;

import java.util.Map;

public interface BoardInitializer {

    Map<Position, Piece> initialize();

    Topology createTopology();

    Side getFirstTurnSide();
}
