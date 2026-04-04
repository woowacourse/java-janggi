package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTypeTest {

    @DisplayName("EMPTY 타입은 어떤 상황에서도 이동 가능한 목적지를 반환하지 않는다")
    @Test
    void EMPTY_타입_테스트() {
        PieceType empty = PieceType.EMPTY;
        List<Intersection> movableDestinations =
                empty.movableDestinations(Side.NONE, new Intersection(1, 1), new AlivePieces(Map.of()));

        assertThat(movableDestinations).isEmpty();
    }

    @DisplayName("기물 종류별 점수 계산")
    @ParameterizedTest(name = "{0}은 {1}점으로 계산된다")
    @MethodSource("pieceTypeAndPoint")
    void 기물별_점수_계산(PieceType pieceType, int expectedPoint) {
        assertThat(pieceType.point()).isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> pieceTypeAndPoint() {
        return Stream.of(
                Arguments.of(PieceType.CHARIOT, 13),
                Arguments.of(PieceType.CANNON, 7),
                Arguments.of(PieceType.HORSE, 5),
                Arguments.of(PieceType.ELEPHANT, 3),
                Arguments.of(PieceType.GUARD, 3),
                Arguments.of(PieceType.SOLDIER, 2),
                Arguments.of(PieceType.GENERAL, 0),
                Arguments.of(PieceType.EMPTY, 0)
        );
    }
}
