package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.direction.PieceMoveRule;
import janggi.strategy.ObstacleBlockStrategy;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
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
        final Piece soldier = new Piece(new PieceMoveRule(PieceMovement.CHO_SOLDIER, new ObstacleBlockStrategy()),
                currentPosition);

        final Board board = new Board(Map.of(currentPosition, soldier));

        // When
        board.updatePiece(currentPosition, arrivalPosition);

        // Then
        assertAll(
                () -> assertThat(board.hasPiece(currentPosition)).isFalse(),
                () -> assertThat(board.findPieceByPosition(arrivalPosition)).isEqualTo(
                        soldier)
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
        final Piece piece = new Piece(new PieceMoveRule(PieceMovement.CHO_SOLDIER, new ObstacleBlockStrategy()),
                new Position(10, 1));
        return Stream.of(
                Arguments.of(new Board(Map.of(piece.getPosition(), piece)), true),
                Arguments.of(new Board(Map.of()), false)
        );
    }

    @Test
    void 해당_위치에_기물이_있는지_확인한다() {
        // Given
        final Position position = new Position(10, 1);
        final Piece choSolider = new Piece(new PieceMoveRule(PieceMovement.CHO_SOLDIER, new ObstacleBlockStrategy()),
                position);
        final Board board = new Board(Map.of(position, choSolider));

        // When & Then
        assertThat(board.findPieceByPosition(position)).isEqualTo(choSolider);
    }

    @Test
    void 해당_위치에_기물이_없으면_예외가_발생한다() {
        // Given
        final Position position = new Position(10, 1);
        final Piece choSolider = new Piece(new PieceMoveRule(PieceMovement.CHO_SOLDIER, new ObstacleBlockStrategy()),
                position);
        final Board board = new Board(Map.of(position, choSolider));

        // When & Then
        assertThatThrownBy(() -> board.findPieceByPosition(new Position(9, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }


    @Test
    @DisplayName("궁성 안에 있는지 확인한다")
    void 궁성_안에_있는지_확인한다() {
        // Given
        final Position position = new Position(8, 4);
        final Piece choSolider = new Piece(new PieceMoveRule(PieceMovement.CHO_SOLDIER, new ObstacleBlockStrategy()),
                position);
        final Board board = new Board(Map.of(position, choSolider));

        // When & Then
        assertThat(board.isInPalace(position.getY(), position.getX())).isTrue();
    }
}
