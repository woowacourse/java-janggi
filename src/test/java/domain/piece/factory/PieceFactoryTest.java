package domain.piece.factory;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("기물 생성 팩토리 클래스 테스트")
class PieceFactoryTest {

    @DisplayName("초 진영의 기물 생성")
    @ParameterizedTest(name = "팩토리={0}, 타입={1}")
    @MethodSource("factoryAndExpectedPieceType")
    void 초_진영의_기물_생성(PieceFactory factory, PieceType expectedType) {
        Piece created = factory.create(Side.CHO);

        assertThat(created.isSameType(expectedType)).isTrue();
        assertThat(created.isSameSide(Side.CHO)).isTrue();
    }

    @DisplayName("한 진영의 기물 생성")
    @ParameterizedTest(name = "팩토리={0}, 타입={1}")
    @MethodSource("factoryAndExpectedPieceType")
    void 한_진영의_기물_생성(PieceFactory factory, PieceType expectedType) {
        Piece created = factory.create(Side.HAN);

        assertThat(created.isSameType(expectedType)).isTrue();
        assertThat(created.isSameSide(Side.HAN)).isTrue();
    }

    private static Stream<Arguments> factoryAndExpectedPieceType() {
        return Stream.of(
                Arguments.of(new CannonFactory(), PieceType.CANNON),
                Arguments.of(new ChariotFactory(), PieceType.CHARIOT),
                Arguments.of(new ElephantFactory(), PieceType.ELEPHANT),
                Arguments.of(new GeneralFactory(), PieceType.GENERAL),
                Arguments.of(new GuardFactory(), PieceType.GUARD),
                Arguments.of(new HorseFactory(), PieceType.HORSE),
                Arguments.of(new SoldierFactory(), PieceType.SOLDIER)
        );
    }
}
