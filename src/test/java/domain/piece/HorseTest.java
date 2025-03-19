package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    static Stream<Arguments> canMoveHorse1() {
        return Stream.of(
                Arguments.of(Position.of(1, 0), true),
                Arguments.of(Position.of(0, 1), true),
                Arguments.of(Position.of(0, 3), true),
                Arguments.of(Position.of(1, 4), true),
                Arguments.of(Position.of(3, 4), true),
                Arguments.of(Position.of(4, 3), true),
                Arguments.of(Position.of(4, 1), true),
                Arguments.of(Position.of(3, 0), true),
                Arguments.of(Position.of(3, 2), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveHorse1(Position movePosition, boolean expected) {
        Position startPosition = Position.of(2, 2);
        Piece horse = new Horse(startPosition, TeamType.CHO);

        Assertions.assertThat(horse.canMove(movePosition, List.of())).isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveHorse2(){
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(3, 4);
        Piece horse = new Horse(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(2, 3);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = horse.canMove(expectedPosition, List.of(soldier, horse));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveHorse3(){
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(3, 4);
        Piece horse = new Horse(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(3, 4);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = horse.canMove(expectedPosition, List.of(soldier, horse));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveHorse4(){
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(3, 4);
        Piece horse = new Horse(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(3, 4);
        Piece soldier = new Soldier(otherPosition, TeamType.CHO);

        boolean result = horse.canMove(expectedPosition, List.of(soldier, horse));

        Assertions.assertThat(result).isTrue();
    }
}
