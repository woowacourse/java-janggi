package domain.board;

import domain.Coordinate;
import domain.piece.Piece;
import java.util.Map;

public interface ChoSettingUpStrategy {

    Map<Coordinate, Piece> setUpCho();
}
