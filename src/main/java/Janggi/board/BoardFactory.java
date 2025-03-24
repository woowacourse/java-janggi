package Janggi.board;

import Janggi.piece.Country;
import Janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BoardFactory {

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();

        for (final PieceInitialPosition pieceType : PieceInitialPosition.values()) {
            initMap.putAll(initPosition(pieceType, Country.CHO));
            initMap.putAll(initPosition(pieceType, Country.HAN));
        }

        return new Board(initMap);
    }

    private static Map<Position, Piece> initPosition(final PieceInitialPosition pieceType, final Country country) {
        return pieceType.getInitPositions(country).stream()
                .collect(Collectors.toMap(
                        Function.identity(), position -> pieceType.createPiece(country)
                ));
    }
}
