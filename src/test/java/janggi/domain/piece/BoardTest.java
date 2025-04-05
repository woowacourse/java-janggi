package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Board;
import janggi.domain.piece.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardTest {

    @Test
    void 두_좌표를_입력_받아_기물을_이동한다() {
        // Given
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);

        final Board board = new Board(Map.of(currentPosition, Piece.CHO_SOLDIER));

        // When
        board.updatePiece(currentPosition, arrivalPosition);

        // Then
        assertAll(
                () -> assertThat(board.hasPiece(currentPosition)).isFalse(),
                () -> assertThat(board.findPieceByPosition(arrivalPosition)).isEqualTo(Piece.CHO_SOLDIER)
        );
    }

    @ParameterizedTest
    @MethodSource
    void 해당_기물이_존재하는지_확인한다(final Board board, final boolean expected) {
        // Given
        final Position position = new Position(10, 1);

        // When & Then
        assertThat(board.hasPiece(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> 해당_기물이_존재하는지_확인한다() {
        return Stream.of(
                Arguments.of(new Board(Map.of(new Position(10, 1), Piece.CHO_SOLDIER)), true),
                Arguments.of(new Board(Map.of()), false)
        );
    }

    @Test
    void 해당_위치에_기물이_있는지_확인한다() {
        // Given
        final Position position = new Position(10, 1);
        final Board board = new Board(Map.of(position, Piece.CHO_SOLDIER));

        // When & Then
        assertThat(board.findPieceByPosition(position)).isEqualTo(Piece.CHO_SOLDIER);
    }

    @Test
    void 해당_위치에_기물이_없으면_예외가_발생한다() {
        // Given
        final Position position = new Position(10, 1);
        final Board board = new Board(Map.of(position, Piece.CHO_SOLDIER));

        // When & Then
        assertThatThrownBy(() -> board.findPieceByPosition(new Position(9, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }

    @Test
    void 기물들의_점수_총_합을_구한다() {
        // Given
        final Position soldierPosition = new Position(10, 1);
        final Position chariotPosition = new Position(8, 1);
        final Board board = new Board(Map.of(soldierPosition, Piece.CHO_SOLDIER, chariotPosition, Piece.CHARIOT));

        // When
        final Double score = board.calculateScore();

        // Then
        assertThat(score).isEqualTo(15.0);
    }
}
