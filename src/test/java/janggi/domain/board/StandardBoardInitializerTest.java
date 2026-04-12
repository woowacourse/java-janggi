package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class StandardBoardInitializerTest {

    @Test
    void 두_진영의_상차림이_모두_존재하지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new StandardBoardInitializer(
                Map.of(Camp.HAN, ElephantSetUp.LEFT_ELEPHANT)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 진영별 상차림은 각각 하나씩만 존재해야 합니다.");
    }

    @Test
    void 한_상마상마_초_마상마상_으로_보드를_초기화한다() {
        // given
        BoardInitializer initializer = new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.LEFT_ELEPHANT,
                Camp.CHO, ElephantSetUp.RIGHT_ELEPHANT
        ));
        Map<Position, Piece> expectedBoard = createExpectedBoard();
        expectedBoard.putAll(createChoLeftHanRightBoard());
        // when
        Map<Position, Piece> board = initializer.initialize();
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(board).hasSize(32);
            assertSoftly.assertThat(board).isEqualTo(expectedBoard);
        });
    }

    @Test
    void 한_상마마상_초_마상상마_으로_보드를_초기화한다() {
        // given
        BoardInitializer initializer = new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.OUTER_ELEPHANT,
                Camp.CHO, ElephantSetUp.INNER_ELEPHANT
        ));
        Map<Position, Piece> expectedBoard = createExpectedBoard();
        expectedBoard.putAll(createChoInnerHanOuterBoard());
        // when
        Map<Position, Piece> board = initializer.initialize();
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(board).hasSize(32);
            assertSoftly.assertThat(board).isEqualTo(expectedBoard);
        });
    }

    private Map<Position, Piece> createExpectedBoard() {
        Map<Position, Piece> expectedBoard = new HashMap<>();

        putPieces(expectedBoard, PieceType.CHARIOT, Camp.CHO, createPosition(0, 0), createPosition(0, 8));
        putPieces(expectedBoard, PieceType.GUARD, Camp.CHO, createPosition(0, 3), createPosition(0, 5));
        putPieces(expectedBoard, PieceType.GENERAL, Camp.CHO, createPosition(1, 4));
        putPieces(expectedBoard, PieceType.CANNON, Camp.CHO, createPosition(2, 1), createPosition(2, 7));
        putPieces(expectedBoard, PieceType.SOLDIER, Camp.CHO,
                createPosition(3, 0), createPosition(3, 2),
                createPosition(3, 4), createPosition(3, 6),
                createPosition(3, 8));

        putPieces(expectedBoard, PieceType.CHARIOT, Camp.HAN, createPosition(9, 0), createPosition(9, 8));
        putPieces(expectedBoard, PieceType.GUARD, Camp.HAN, createPosition(9, 3), createPosition(9, 5));
        putPieces(expectedBoard, PieceType.GENERAL, Camp.HAN, createPosition(8, 4));
        putPieces(expectedBoard, PieceType.CANNON, Camp.HAN, createPosition(7, 1), createPosition(7, 7));
        putPieces(expectedBoard, PieceType.SOLDIER, Camp.HAN,
                createPosition(6, 0), createPosition(6, 2),
                createPosition(6, 4), createPosition(6, 6),
                createPosition(6, 8));

        return expectedBoard;
    }

    private Map<Position, Piece> createChoLeftHanRightBoard() {
        Map<Position, Piece> board = new HashMap<>();

        putPieces(board, PieceType.ELEPHANT, Camp.CHO, createPosition(0, 1), createPosition(0, 6));
        putPieces(board, PieceType.HORSE, Camp.CHO, createPosition(0, 2), createPosition(0, 7));

        putPieces(board, PieceType.ELEPHANT, Camp.HAN, createPosition(9, 1), createPosition(9, 6));
        putPieces(board, PieceType.HORSE, Camp.HAN, createPosition(9, 2), createPosition(9, 7));

        return board;
    }

    private Map<Position, Piece> createChoInnerHanOuterBoard() {
        Map<Position, Piece> board = new HashMap<>();

        putPieces(board, PieceType.ELEPHANT, Camp.CHO, createPosition(0, 2), createPosition(0, 6));
        putPieces(board, PieceType.HORSE, Camp.CHO, createPosition(0, 1), createPosition(0, 7));

        putPieces(board, PieceType.ELEPHANT, Camp.HAN, createPosition(9, 1), createPosition(9, 7));
        putPieces(board, PieceType.HORSE, Camp.HAN, createPosition(9, 2), createPosition(9, 6));

        return board;
    }

    private void putPieces(Map<Position, Piece> board, PieceType pieceType, Camp camp,
                           Position... positions) {
        for (Position position : positions) {
            board.put(position, new Piece(camp, pieceType));
        }
    }

    private Position createPosition(int row, int column) {
        return new Position(row, column);
    }
}
