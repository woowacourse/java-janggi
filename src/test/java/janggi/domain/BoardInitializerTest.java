package janggi.domain;

import janggi.domain.board.BoardInitializer;
import janggi.domain.board.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class BoardInitializerTest {

    @Test
    void 보드의_기물들을_초기화한다() {
        // given
        BoardInitializer initializer = new StandardBoardInitializer();
        Map<Position, Piece> expectedBoard = createExpectedBoard();
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

        putPieces(expectedBoard, Type.CHARIOT, Camp.CHO, createPosition(0, 0), createPosition(0, 8));
        putPieces(expectedBoard, Type.ELEPHANT, Camp.CHO, createPosition(0, 1), createPosition(0, 6));
        putPieces(expectedBoard, Type.HORSE, Camp.CHO, createPosition(0, 2), createPosition(0, 7));
        putPieces(expectedBoard, Type.GUARD, Camp.CHO, createPosition(0, 3), createPosition(0, 5));
        putPieces(expectedBoard, Type.GENERAL, Camp.CHO, createPosition(1, 4));
        putPieces(expectedBoard, Type.CANNON, Camp.CHO, createPosition(2, 1), createPosition(2, 7));
        putPieces(expectedBoard, Type.SOLDIER, Camp.CHO,
                createPosition(3, 0), createPosition(3, 2),
                createPosition(3, 4), createPosition(3, 6),
                createPosition(3, 8));

        putPieces(expectedBoard, Type.CHARIOT, Camp.HAN, createPosition(9, 0), createPosition(9, 8));
        putPieces(expectedBoard, Type.ELEPHANT, Camp.HAN, createPosition(9, 1), createPosition(9, 6));
        putPieces(expectedBoard, Type.HORSE, Camp.HAN, createPosition(9, 2), createPosition(9, 7));
        putPieces(expectedBoard, Type.GUARD, Camp.HAN, createPosition(9, 3), createPosition(9, 5));
        putPieces(expectedBoard, Type.GENERAL, Camp.HAN, createPosition(8, 4));
        putPieces(expectedBoard, Type.CANNON, Camp.HAN, createPosition(7, 1), createPosition(7, 7));
        putPieces(expectedBoard, Type.SOLDIER, Camp.HAN,
                createPosition(6, 0), createPosition(6, 2),
                createPosition(6, 4), createPosition(6, 6),
                createPosition(6, 8));

        return expectedBoard;
    }

    //TODO: moveStrategy 변경
    private void putPieces(Map<Position, Piece> board, Type type, Camp camp,
                           Position... positions) {
        for (Position position : positions) {
            board.put(position, new Piece(type, camp, null));
        }
    }

    private Position createPosition(int row, int column) {
        return new Position(row, column);
    }
}
