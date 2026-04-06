package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class InitialPiecePlacementTest {

    @Test
    void 한_상마상마_초_마상마상_으로_보드를_초기화한다() {
        // given
        ElephantFormation hanElephantFormation = new ElephantFormation(CampType.HAN, ElephantSetUp.LEFT_ELEPHANT);
        ElephantFormation choElephantFormation = new ElephantFormation(CampType.CHO, ElephantSetUp.RIGHT_ELEPHANT);

        Map<Position, Piece> expectedBoard = createExpectedBoard();
        expectedBoard.putAll(createChoLeftHanRightBoard());
        // when
        Board board = InitialPiecePlacement.initialize(hanElephantFormation, choElephantFormation);
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(board.getBoard()).hasSize(32);
            assertSoftly.assertThat(board.getBoard()).isEqualTo(expectedBoard);
        });
    }

    @Test
    void 한_상마마상_초_마상상마_으로_보드를_초기화한다() {
        // given
        ElephantFormation hanElephantFormation = new ElephantFormation(CampType.HAN, ElephantSetUp.OUTER_ELEPHANT);
        ElephantFormation choElephantFormation = new ElephantFormation(CampType.CHO, ElephantSetUp.INNER_ELEPHANT);

        Map<Position, Piece> expectedBoard = createExpectedBoard();
        expectedBoard.putAll(createChoInnerHanOuterBoard());
        // when
        Board board = InitialPiecePlacement.initialize(hanElephantFormation, choElephantFormation);
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(board.getBoard()).hasSize(32);
            assertSoftly.assertThat(board.getBoard()).isEqualTo(expectedBoard);
        });
    }

    private Map<Position, Piece> createExpectedBoard() {
        Map<Position, Piece> expectedBoard = new HashMap<>();

        putPieces(expectedBoard, PieceRule.CHARIOT, CampType.CHO, createPosition(0, 0), createPosition(0, 8));
        putPieces(expectedBoard, PieceRule.GUARD, CampType.CHO, createPosition(0, 3), createPosition(0, 5));
        putPieces(expectedBoard, PieceRule.GENERAL, CampType.CHO, createPosition(1, 4));
        putPieces(expectedBoard, PieceRule.CANNON, CampType.CHO, createPosition(2, 1), createPosition(2, 7));
        putPieces(expectedBoard, PieceRule.SOLDIER, CampType.CHO,
                createPosition(3, 0), createPosition(3, 2),
                createPosition(3, 4), createPosition(3, 6),
                createPosition(3, 8));

        putPieces(expectedBoard, PieceRule.CHARIOT, CampType.HAN, createPosition(9, 0), createPosition(9, 8));
        putPieces(expectedBoard, PieceRule.GUARD, CampType.HAN, createPosition(9, 3), createPosition(9, 5));
        putPieces(expectedBoard, PieceRule.GENERAL, CampType.HAN, createPosition(8, 4));
        putPieces(expectedBoard, PieceRule.CANNON, CampType.HAN, createPosition(7, 1), createPosition(7, 7));
        putPieces(expectedBoard, PieceRule.SOLDIER, CampType.HAN,
                createPosition(6, 0), createPosition(6, 2),
                createPosition(6, 4), createPosition(6, 6),
                createPosition(6, 8));

        return expectedBoard;
    }

    private Map<Position, Piece> createChoLeftHanRightBoard() {
        Map<Position, Piece> board = new HashMap<>();

        putPieces(board, PieceRule.ELEPHANT, CampType.CHO, createPosition(0, 1), createPosition(0, 6));
        putPieces(board, PieceRule.HORSE, CampType.CHO, createPosition(0, 2), createPosition(0, 7));

        putPieces(board, PieceRule.ELEPHANT, CampType.HAN, createPosition(9, 1), createPosition(9, 6));
        putPieces(board, PieceRule.HORSE, CampType.HAN, createPosition(9, 2), createPosition(9, 7));

        return board;
    }

    private Map<Position, Piece> createChoInnerHanOuterBoard() {
        Map<Position, Piece> board = new HashMap<>();

        putPieces(board, PieceRule.ELEPHANT, CampType.CHO, createPosition(0, 2), createPosition(0, 6));
        putPieces(board, PieceRule.HORSE, CampType.CHO, createPosition(0, 1), createPosition(0, 7));

        putPieces(board, PieceRule.ELEPHANT, CampType.HAN, createPosition(9, 1), createPosition(9, 7));
        putPieces(board, PieceRule.HORSE, CampType.HAN, createPosition(9, 2), createPosition(9, 6));

        return board;
    }

    private void putPieces(Map<Position, Piece> board, PieceRule pieceRule, CampType campType,
                           Position... positions) {
        for (Position position : positions) {
            board.put(position, new Piece(pieceRule, campType));
        }
    }

    private Position createPosition(int row, int column) {
        return new Position(row, column);
    }
}
