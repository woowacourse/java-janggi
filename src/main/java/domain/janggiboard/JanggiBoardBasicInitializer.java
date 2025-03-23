package domain.janggiboard;

import domain.piece.JanggiPieceType;
import domain.position.JanggiPosition;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JanggiBoardBasicInitializer implements JanggiBoardInitializer {

    private static final List<Integer> janggiBoardRanks = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> janggiBoardFiles = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Override
    public Map<JanggiPosition, JanggiPiece> initializeJanggiBoard() {
        final Map<JanggiPosition, JanggiPiece> janggiBoard = new HashMap<>();
        for (Integer rank : janggiBoardRanks) {
            for (Integer file : janggiBoardFiles) {
                janggiBoard.put(new JanggiPosition(rank, file), new Empty());
            }
        }
        initChoJanggiBoard(janggiBoard);
        initHanJanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void initChoJanggiBoard(final Map<JanggiPosition, JanggiPiece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(9, 5), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.궁));
        janggiBoard.put(new JanggiPosition(0, 1), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.차));
        janggiBoard.put(new JanggiPosition(0, 9), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.차));
        janggiBoard.put(new JanggiPosition(8, 2), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.포));
        janggiBoard.put(new JanggiPosition(8, 8), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.포));
        janggiBoard.put(new JanggiPosition(7, 1), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.졸));
        janggiBoard.put(new JanggiPosition(7, 3), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.졸));
        janggiBoard.put(new JanggiPosition(7, 5), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.졸));
        janggiBoard.put(new JanggiPosition(7, 7), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.졸));
        janggiBoard.put(new JanggiPosition(7, 9), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.졸));
        janggiBoard.put(new JanggiPosition(0, 4), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.사));
        janggiBoard.put(new JanggiPosition(0, 6), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.사));
        janggiBoard.put(new JanggiPosition(0, 2), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.마));
        janggiBoard.put(new JanggiPosition(0, 8), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.마));
        janggiBoard.put(new JanggiPosition(0, 3), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.상));
        janggiBoard.put(new JanggiPosition(0, 7), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.상));
    }

    private static void initHanJanggiBoard(final Map<JanggiPosition, JanggiPiece> janggiBoard) {
        janggiBoard.put(new JanggiPosition(2, 5), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.궁));
        janggiBoard.put(new JanggiPosition(1, 1), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.차));
        janggiBoard.put(new JanggiPosition(1, 9), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.차));
        janggiBoard.put(new JanggiPosition(3, 2), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.포));
        janggiBoard.put(new JanggiPosition(3, 8), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.포));
        janggiBoard.put(new JanggiPosition(4, 1), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.병));
        janggiBoard.put(new JanggiPosition(4, 3), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.병));
        janggiBoard.put(new JanggiPosition(4, 5), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.병));
        janggiBoard.put(new JanggiPosition(4, 7), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.병));
        janggiBoard.put(new JanggiPosition(4, 9), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.병));
        janggiBoard.put(new JanggiPosition(1, 4), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.사));
        janggiBoard.put(new JanggiPosition(1, 6), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.사));
        janggiBoard.put(new JanggiPosition(1, 2), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.마));
        janggiBoard.put(new JanggiPosition(1, 8), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.마));
        janggiBoard.put(new JanggiPosition(1, 3), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.상));
        janggiBoard.put(new JanggiPosition(1, 7), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.상));
    }
}
