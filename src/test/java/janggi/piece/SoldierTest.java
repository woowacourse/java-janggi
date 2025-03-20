package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Position;
import janggi.Side;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @DisplayName("BLUE 병사의 시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @CsvSource(value = {
            "5, 6",
            "4, 5",
            "6, 5"
    })
    void shouldReturnPathWhenBlueSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = soldier.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("RED 병사의 시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @CsvSource(value = {
            "5, 4",
            "4, 5",
            "6, 5"
    })
    void shouldReturnPathWhenRedSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = soldier.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("BLUE 병사의 이동 경로가 벗어나면 예외를 던진다.")
    @CsvSource(value = {
            "5, 4",
            "6, 6",
            "4, 6",
            "3, 5",
            "7, 5"
    })
    void shouldThrowExceptionWhenBlueSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("RED 병사의 이동 경로가 벗어나면 예외를 던진다.")
    @CsvSource(value = {
            "5, 6",
            "6, 4",
            "4, 4",
            "3, 5",
            "7, 5"
    })
    void shouldThrowExceptionWhenRedSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
