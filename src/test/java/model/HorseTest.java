package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.Horse;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HorseTest {

    private final Horse horse = new Horse(Team.RED);
    private final Position departure = new Position(Column.THREE, Row.THREE);

    @Nested
    @DisplayName("horse의 이동 가능한 경로를 구한다.")
    class FindDirectionOfHorse {

        @Test
        @DisplayName("위 + 우측 상단 대각인 경우")
        void up_and_up_right() {
            Position arrival = new Position(Column.ONE, Row.FOUR);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.TWO, Row.THREE),
                new Position(Column.ONE, Row.FOUR));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("위 + 좌측 상단 대각인 경우 ")
        void up_and_up_left() {
            Position arrival = new Position(Column.ONE, Row.TWO);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.TWO, Row.THREE),
                new Position(Column.ONE, Row.TWO));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("아래 + 우측 하단 대각인 경우")
        void down_and_down_right() {
            Position arrival = new Position(Column.FIVE, Row.FOUR);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FOUR, Row.THREE),
                new Position(Column.FIVE, Row.FOUR));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("아래 + 좌측 하단 대각인 경우")
        void down_and_down_left() {
            Position arrival = new Position(Column.FIVE, Row.TWO);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FOUR, Row.THREE),
                new Position(Column.FIVE, Row.TWO));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("좌측 + 좌측 상단 대각인 경우")
        void left_and_up_left() {
            Position arrival = new Position(Column.TWO, Row.ONE);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.THREE, Row.TWO),
                new Position(Column.TWO, Row.ONE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("좌측 + 좌측 하단 대각인 경우")
        void left_and_down_left() {
            Position arrival = new Position(Column.FOUR, Row.ONE);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.THREE, Row.TWO),
                new Position(Column.FOUR, Row.ONE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("우측 + 우측 상단 대각인 경우")
        void right_and_up_right() {
            Position arrival = new Position(Column.FOUR, Row.FIVE);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.THREE, Row.FOUR),
                new Position(Column.FOUR, Row.FIVE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("우측 + 우측 하단 대각인 경우")
        void right_and_down_right() {
            Position arrival = new Position(Column.TWO, Row.FIVE);
            List<Position> findDirection = horse.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.THREE, Row.FOUR),
                new Position(Column.TWO, Row.FIVE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }
    }

    @DisplayName("horse가 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position arrival = new Position(Column.THREE, Row.FIVE);
        assertThatThrownBy(() -> horse.calculateAllDirection(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
