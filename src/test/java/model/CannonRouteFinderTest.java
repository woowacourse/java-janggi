package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Team;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CannonRouteFinderTest {

    private final Piece piece = new Piece(Team.GREEN, PieceType.CANNON);

    @Nested
    @DisplayName("Cannon의 이동 가능한 경로를 구한다.")
    class FindDirectionOfCannonRouteFinder {

        @Test
        @DisplayName("Up 인 경우")
        void case_up() {
            Position departure = new Position(Column.TEN, Row.ONE);
            Position arrival = new Position(Column.FIVE, Row.ONE);
            List<Position> findDirection = piece.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.NINE, Row.ONE),
                new Position(Column.EIGHT, Row.ONE),
                new Position(Column.SEVEN, Row.ONE),
                new Position(Column.SIX, Row.ONE),
                new Position(Column.FIVE, Row.ONE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("Down 인 경우")
        void case_down() {
            Position departure = new Position(Column.FIVE, Row.FIVE);
            Position arrival = new Position(Column.TEN, Row.FIVE);
            List<Position> findDirection = piece.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.TEN, Row.FIVE),
                new Position(Column.NINE, Row.FIVE),
                new Position(Column.EIGHT, Row.FIVE),
                new Position(Column.SEVEN, Row.FIVE),
                new Position(Column.SIX, Row.FIVE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("Left 인 경우")
        void case_left() {
            Position departure = new Position(Column.FIVE, Row.FIVE);
            Position arrival = new Position(Column.FIVE, Row.ONE);
            List<Position> findDirection = piece.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.FOUR),
                new Position(Column.FIVE, Row.THREE),
                new Position(Column.FIVE, Row.TWO),
                new Position(Column.FIVE, Row.ONE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("Right 인 경우")
        void case_right() {
            Position departure = new Position(Column.FIVE, Row.FIVE);
            Position arrival = new Position(Column.FIVE, Row.NINE);
            List<Position> findDirection = piece.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.SIX),
                new Position(Column.FIVE, Row.SEVEN),
                new Position(Column.FIVE, Row.EIGHT),
                new Position(Column.FIVE, Row.NINE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }
    }

    @DisplayName("Cannon이 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position departure = new Position(Column.FIVE, Row.FIVE);
        Position arrival = new Position(Column.SIX, Row.SIX);
        assertThatThrownBy(() -> piece.calculateAllDirection(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
