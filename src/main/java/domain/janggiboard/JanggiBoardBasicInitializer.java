package domain.janggiboard;

import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.piece.JanggiPieceType;
import domain.position.JanggiPosition;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JanggiBoardBasicInitializer implements JanggiBoardInitializer {

    private static final List<Integer> janggiBoardRanks = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> janggiBoardFiles = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private final BoardArrangementStrategy strategyOfCho;
    private final BoardArrangementStrategy strategyOfHan;

    public JanggiBoardBasicInitializer(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        this.strategyOfCho = strategyOfCho;
        this.strategyOfHan = strategyOfHan;
    }

    @Override
    public Map<JanggiPosition, JanggiPiece> initializeJanggiBoard() {
        final Map<JanggiPosition, JanggiPiece> janggiBoard = new HashMap<>();
        for (Integer rank : janggiBoardRanks) {
            for (Integer file : janggiBoardFiles) {
                janggiBoard.put(new JanggiPosition(rank, file), new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY));
            }
        }

        initChoJanggiBoard(janggiBoard);
        initHanJanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private void initChoJanggiBoard(final Map<JanggiPosition, JanggiPiece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(9, 5), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.KING));
        janggiBoard.put(new JanggiPosition(0, 1), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CHARIOT));
        janggiBoard.put(new JanggiPosition(0, 9), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CHARIOT));
        janggiBoard.put(new JanggiPosition(8, 2), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CANNON));
        janggiBoard.put(new JanggiPosition(8, 8), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CANNON));
        janggiBoard.put(new JanggiPosition(7, 1), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO));
        janggiBoard.put(new JanggiPosition(7, 3), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO));
        janggiBoard.put(new JanggiPosition(7, 5), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO));
        janggiBoard.put(new JanggiPosition(7, 7), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO));
        janggiBoard.put(new JanggiPosition(7, 9), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO));
        janggiBoard.put(new JanggiPosition(0, 4), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ADVISOR));
        janggiBoard.put(new JanggiPosition(0, 6), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ADVISOR));

        Map<JanggiPosition, JanggiPiece> army = strategyOfCho.setUp();
        army.keySet()
                .forEach((position) -> janggiBoard.put(position, army.get(position)));
    }

    private void initHanJanggiBoard(final Map<JanggiPosition, JanggiPiece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(2, 5), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.KING));
        janggiBoard.put(new JanggiPosition(1, 1), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CHARIOT));
        janggiBoard.put(new JanggiPosition(1, 9), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CHARIOT));
        janggiBoard.put(new JanggiPosition(3, 2), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CANNON));
        janggiBoard.put(new JanggiPosition(3, 8), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CANNON));
        janggiBoard.put(new JanggiPosition(4, 1), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN));
        janggiBoard.put(new JanggiPosition(4, 3), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN));
        janggiBoard.put(new JanggiPosition(4, 5), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN));
        janggiBoard.put(new JanggiPosition(4, 7), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN));
        janggiBoard.put(new JanggiPosition(4, 9), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN));
        janggiBoard.put(new JanggiPosition(1, 4), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ADVISOR));
        janggiBoard.put(new JanggiPosition(1, 6), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ADVISOR));

        Map<JanggiPosition, JanggiPiece> army = strategyOfHan.setUp();
        army.keySet()
                .forEach((position) -> janggiBoard.put(position, army.get(position)));
    }
}
