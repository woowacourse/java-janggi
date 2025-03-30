package domain.board;

import domain.Coordinate;
import domain.piece.Piece;
import java.util.Map;

public interface HanSettingUpStrategy {

    Map<Coordinate, Piece> setUpHan();
}
