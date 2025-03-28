package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.strategy.WalkingStrategy;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PiecesTest {

    @Test
    void 두_좌표를_입력_받아_기물을_이동한다() {
        // Given
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);
        Piece soldier = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), currentPosition);

        Pieces pieces = new Pieces(Map.of(currentPosition, soldier));

        // When
        pieces.updatePiece(currentPosition, arrivalPosition);

        // Then
        assertAll(
                () -> assertThat(pieces.hasPiece(currentPosition)).isFalse(),
                () -> assertThat(pieces.findPieceByPosition(arrivalPosition)).isEqualTo(
                        soldier)
        );
    }

    @ParameterizedTest
    @MethodSource
    void 해당_기물이_존재하는지_확인한다(final Pieces pieces, final boolean expected) {
        // Given
        final Position position = new Position(10, 1);

        // When & Then
        assertThat(pieces.hasPiece(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> 해당_기물이_존재하는지_확인한다() {
        final Piece piece = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(10, 1));
        return Stream.of(
                Arguments.of(new Pieces(Map.of(piece.getPosition(), piece)), true),
                Arguments.of(new Pieces(Map.of()), false)
        );
    }

    @Test
    void 해당_위치에_기물이_있는지_확인한다() {
        // Given
        final Position position = new Position(10, 1);
        Piece choSolider = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), position);
        Pieces pieces = new Pieces(Map.of(position, choSolider));

        // When & Then
        assertThat(pieces.findPieceByPosition(position)).isEqualTo(choSolider);
    }

    @Test
    void 해당_위치에_기물이_없으면_예외가_발생한다() {
        // Given
        final Position position = new Position(10, 1);
        Piece choSolider = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), position);
        Pieces pieces = new Pieces(Map.of(position, choSolider));

        // When & Then
        assertThatThrownBy(() -> pieces.findPieceByPosition(new Position(9, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }
}
