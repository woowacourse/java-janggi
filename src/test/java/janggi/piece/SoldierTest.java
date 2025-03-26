package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @DisplayName("BLUE 병사의 시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @CsvSource(value = {
            "5, 4", // 상
            "4, 5", // 좌
            "6, 5"  // 우
    })
    void shouldReturnPathWhenBlueSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.BLUE);
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
            "5, 6", // 하
            "4, 5", // 좌
            "6, 5"  // 우
    })
    void shouldReturnPathWhenRedSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.RED);
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
            "5, 6", // 하
            "6, 6", // 우하
            "4, 6", // 좌하
            "3, 5", // 좌좌
            "7, 5"  // 우우
    })
    void shouldThrowExceptionWhenBlueSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("RED 병사의 이동 경로가 벗어나면 예외를 던진다.")
    @CsvSource(value = {
            "5, 4", // 상
            "6, 4", // 우상
            "4, 4", // 좌상
            "3, 5", // 좌좌
            "7, 5"  // 우우
    })
    void shouldThrowExceptionWhenRedSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
