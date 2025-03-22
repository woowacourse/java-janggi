package domain.JanggiBoard;

import domain.position.JanggiPosition;
import domain.piece.JanggiPiece;
import java.util.Map;

@FunctionalInterface
public interface JanggiBoardInitializer {

    Map<JanggiPosition, JanggiPiece> initializeJanggiBoard();
}
