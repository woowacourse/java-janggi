package domain.janggiboard.customstrategy;

import domain.piece.JanggiPiece;
import domain.position.JanggiPosition;
import java.util.Map;

public interface BoardArrangementStrategy {

    Map<JanggiPosition, JanggiPiece> setUp();
}
