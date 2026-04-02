package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoardInitializerTest {

    @ParameterizedTest
    @EnumSource(BoardSetting.class)
    void 상차림에_따라_모든_기물이_올바른_위치에_초기화된다(BoardSetting boardSetting) {
        Map<Position, Piece> pieces = BoardInitializer.init(boardSetting);
        assertThat(pieces).isEqualTo(createPieces(boardSetting));
    }

    private static Map<Position, Piece> createPieces(BoardSetting boardSetting) {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(4, 1), Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(new Position(4, 8), Piece.of(Camp.HAN, PieceType.GENERAL));

        pieces.put(new Position(3, 0), Piece.of(Camp.CHO, PieceType.GUARD));
        pieces.put(new Position(5, 0), Piece.of(Camp.CHO, PieceType.GUARD));
        pieces.put(new Position(3, 9), Piece.of(Camp.HAN, PieceType.GUARD));
        pieces.put(new Position(5, 9), Piece.of(Camp.HAN, PieceType.GUARD));

        pieces.put(new Position(8, 0), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(0, 0), Piece.of(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(0, 9), Piece.of(Camp.HAN, PieceType.CHARIOT));
        pieces.put(new Position(8, 9), Piece.of(Camp.HAN, PieceType.CHARIOT));

        pieces.put(new Position(1, 2), Piece.of(Camp.CHO, PieceType.CANNON));
        pieces.put(new Position(7, 2), Piece.of(Camp.CHO, PieceType.CANNON));
        pieces.put(new Position(1, 7), Piece.of(Camp.HAN, PieceType.CANNON));
        pieces.put(new Position(7, 7), Piece.of(Camp.HAN, PieceType.CANNON));

        List<PieceType> horseAndElephantArrangement = boardSetting.piecesArrangement();
        pieces.put(new Position(1, 0), Piece.of(Camp.CHO, horseAndElephantArrangement.get(0)));
        pieces.put(new Position(2, 0), Piece.of(Camp.CHO, horseAndElephantArrangement.get(1)));
        pieces.put(new Position(6, 0), Piece.of(Camp.CHO, horseAndElephantArrangement.get(2)));
        pieces.put(new Position(7, 0), Piece.of(Camp.CHO, horseAndElephantArrangement.get(3)));
        pieces.put(new Position(1, 9), Piece.of(Camp.HAN, horseAndElephantArrangement.get(0)));
        pieces.put(new Position(2, 9), Piece.of(Camp.HAN, horseAndElephantArrangement.get(1)));
        pieces.put(new Position(6, 9), Piece.of(Camp.HAN, horseAndElephantArrangement.get(2)));
        pieces.put(new Position(7, 9), Piece.of(Camp.HAN, horseAndElephantArrangement.get(3)));

        pieces.put(new Position(0, 3), Piece.of(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(2, 3), Piece.of(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(4, 3), Piece.of(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(6, 3), Piece.of(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(8, 3), Piece.of(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(0, 6), Piece.of(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(2, 6), Piece.of(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(4, 6), Piece.of(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(6, 6), Piece.of(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(8, 6), Piece.of(Camp.HAN, PieceType.SOLDIER));

        return pieces;
    }
}
