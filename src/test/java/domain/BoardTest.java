package domain;

import domain.vo.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @ParameterizedTest
    @MethodSource("providePiece")
    @DisplayName("보드를 생성하면 기물들 초기화된다.")
    void 보드_생성(int row, int col, Type expected) {
        // given
        // when
        Board board = BoardFactory.setUp();

        // then
        Piece piece = board.findPieceByPosition(Position.of(row, col)).get();
        assertEquals(expected, piece.getType());
    }

    @Test
    @DisplayName("기물의 이동 경로에 다른 기물이 없으면 이동한다.")
    void 기물_이동() {
        // given
        Map<Position, Piece> tempBoard = new HashMap<>();
        tempBoard.put(Position.of(0, 0), Piece.of(Team.CHU, Type.SOLIDER, new FixedMoveStrategy()));
        tempBoard.put(Position.of(0, 3), Piece.of(Team.CHU, Type.SOLIDER, new FixedMoveStrategy()));

        Board board = Board.of(tempBoard);

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(3, 0);
        board.move(from, to);

        // then
        Piece findPiece = board.findPieceByPosition(to).get();
        assertEquals(Type.SOLIDER, findPiece.getType());
        assertFalse(board.isExistPosition(from));
    }

    private static Stream<Arguments> providePiece() {
        return Stream.of(
                Arguments.of(0, 0, Type.CHARIOT),
                Arguments.of(0, 1, Type.ELEPHANT),
                Arguments.of(0, 2, Type.HORSE),
                Arguments.of(0, 3, Type.GUARD),
                Arguments.of(0, 8, Type.CHARIOT),
                Arguments.of(1, 4, Type.GENERAL),
                Arguments.of(2, 1, Type.CANNON),
                Arguments.of(3, 0, Type.SOLIDER),
                Arguments.of(3, 4, Type.SOLIDER),
                Arguments.of(6, 2, Type.SOLIDER),
                Arguments.of(7, 1, Type.CANNON),
                Arguments.of(8, 4, Type.GENERAL),
                Arguments.of(9, 0, Type.CHARIOT),
                Arguments.of(9, 3, Type.GUARD),
                Arguments.of(9, 7, Type.HORSE),
                Arguments.of(9, 8, Type.CHARIOT)
        );
    }
}
