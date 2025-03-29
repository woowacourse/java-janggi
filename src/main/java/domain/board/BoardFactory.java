package domain.board;

import domain.piece.Piece;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board createInitialBoard() {
        final Map<BoardPosition, Piece> pieces = new HashMap<>();
        Arrays.stream(InitialBoardPositions.values())
            .forEach(initialBoardPositions -> {
                initialBoardPositions.getInitialPosition()
                    .forEach((team, positions) -> {
                        positions.forEach(position -> {
                            pieces.put(position, initialBoardPositions.createPiece(team));
                        });
                    });
            });

        return new Board(pieces);
    }
}
