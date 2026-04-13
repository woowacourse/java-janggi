package janggi.domain.piece;

import janggi.domain.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PieceFactoryTest {

    static Stream<Arguments> pieceProvider() {
        return Stream.of(
                Arguments.of("GENERAL", General.class),
                Arguments.of("ADVISOR", Advisor.class),
                Arguments.of("CHARIOT", Chariot.class),
                Arguments.of("CANNON", Cannon.class),
                Arguments.of("HORSE", Horse.class),
                Arguments.of("ELEPHANT", Elephant.class),
                Arguments.of("SOLDIER", Soldier.class)
        );
    }

    @DisplayName("올바른 PieceType이면 올바른 Piece를 반환한다")
    @ParameterizedTest
    @MethodSource("pieceProvider")
    void create_CorrectPieceType_ReturnCorrectPiece(String pieceType, Class<? extends Piece> pieceClass) {
        assertThat(PieceFactory.create(pieceType, Camp.CHO)).isInstanceOf(pieceClass);
    }

    @DisplayName("올바르지 않은 PieceType이면 예외가 발생한다")
    @Test
    void create_IncorrectPieceType_ReturnIncorrectPieceType() {
        assertThatThrownBy(() -> PieceFactory.create("INCORRECT", Camp.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 기물 타입입니다: INCORRECT");
    }

}
