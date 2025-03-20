package util;

import domain.JanggiBoard.JanggiBoardInitializer;
import domain.JanggiPosition;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoardInitializerStub implements JanggiBoardInitializer {

    private static final List<Integer> rows = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private final Map<JanggiPosition, JanggiPiece> janggiBoard;

    public JanggiBoardInitializerStub(Map<JanggiPosition, JanggiPiece> janggiBoard) {
        this.janggiBoard = new HashMap<>();
        for (Integer column : columns) {
            for (Integer row : rows) {
                this.janggiBoard.put(new JanggiPosition(row, column), new Empty());
            }
        }

        for (JanggiPosition position : janggiBoard.keySet()) {
            this.janggiBoard.put(position, janggiBoard.get(position));
        }
    }

    @Override
    public Map<JanggiPosition, JanggiPiece> initializeJanggiBoard() {
        return janggiBoard;
    }
}
