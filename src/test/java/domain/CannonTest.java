package domain;

import domain.piece.Cannon;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {
    static Stream<Arguments> canMoveChariot() {
        return Stream.of(
                Arguments.of(Position.of(4, 3), Position.of(5, 3), false),
                Arguments.of(Position.of(5, 3), Position.of(4, 3), true),
                Arguments.of(Position.of(6, 3), Position.of(5, 3), true),
                Arguments.of(Position.of(7, 3), Position.of(6, 3), true),
                Arguments.of(Position.of(8, 3), Position.of(7, 3), true),
                Arguments.of(Position.of(2, 3), Position.of(4, 3), false),
                Arguments.of(Position.of(1, 3), Position.of(2, 3), true),
                Arguments.of(Position.of(0, 3), Position.of(1, 3), true),
                Arguments.of(Position.of(3, 2), Position.of(2, 2), false),
                Arguments.of(Position.of(3, 1), Position.of(3, 2), true),
                Arguments.of(Position.of(3, 0), Position.of(3, 2), true),
                Arguments.of(Position.of(3, 4), Position.of(5, 3), false),
                Arguments.of(Position.of(3, 5), Position.of(3, 4), true),
                Arguments.of(Position.of(3, 6), Position.of(3, 5), true),
                Arguments.of(Position.of(3, 7), Position.of(3, 6), true),
                Arguments.of(Position.of(3, 8), Position.of(3, 5), true),
                Arguments.of(Position.of(4, 4), Position.of(3, 6), false),
                Arguments.of(Position.of(5, 5), Position.of(3, 7), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("포 이동 테스트")
    void canMoveChariot(Position movePosition, Position otherPosition, boolean expected) {
        Position startPosition = Position.of(3, 3);
        Piece cannon = new Cannon(startPosition, TeamType.CHO);

        Piece other = new King(otherPosition, TeamType.HAN);

        Assertions.assertThat(cannon.canMove(movePosition, List.of(other, cannon))).isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 경로에 포가 있으면 false를 반환한다")
    void canMoveChariot2() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(3, 2);
        Piece otherCannon = new Cannon(otherPosition, TeamType.HAN);

        boolean result = cannon.canMove(expectedPosition, List.of(otherCannon, cannon));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveChariot3() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(startPosition, TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(jumpPosition, TeamType.CHO);

        Position otherPosition = Position.of(4, 2);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = cannon.canMove(expectedPosition, List.of(soldier, jump, cannon));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveChariot4() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(startPosition, TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(jumpPosition, TeamType.CHO);

        Position otherPosition = Position.of(4, 2);
        Piece soldier = new Soldier(otherPosition, TeamType.CHO);

        boolean result = cannon.canMove(expectedPosition, List.of(soldier, jump, cannon));

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("도착 지점에 포가 있으면 false를 반환한다")
    void canMoveCannon5() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece cannon = new Cannon(startPosition, TeamType.HAN);

        Position jumpPosition = Position.of(3, 2);
        Piece jump = new Soldier(jumpPosition, TeamType.CHO);

        Position otherPosition = Position.of(4, 2);
        Piece otherCannon = new Cannon(otherPosition, TeamType.CHO);

        boolean result = cannon.canMove(expectedPosition, List.of(otherCannon, jump, cannon));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점까지 기물이 2개 이상이면 false를 반환한다")
    void canMoveCannon6() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(6, 2);
        Piece cannon = new Cannon(startPosition, TeamType.HAN);

        Position otherPosition1 = Position.of(4, 2);
        Piece other1 = new Horse(otherPosition1, TeamType.CHO);

        Position otherPosition2 = Position.of(5, 2);
        Piece other2 = new Horse(otherPosition2, TeamType.CHO);

        boolean result = cannon.canMove(expectedPosition, List.of(other1, other2, cannon));

        Assertions.assertThat(result).isFalse();
    }
}
