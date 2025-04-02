package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.coordiante.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChaTest {

    @DisplayName("차의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: 상,하,좌,우 방향으로 기물을 만나거나, 장기판 끝까지 이동한다.")
    @Test
    void chaAvailableMovePosition() {
        Cha cha = new Cha(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), cha);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = cha.findAvailablePaths(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(5, 1), new Coordinate(5, 2), new Coordinate(5, 3),
                new Coordinate(5, 4), new Coordinate(5, 6), new Coordinate(5, 7),
                new Coordinate(5, 8), new Coordinate(5, 9), new Coordinate(1, 5),
                new Coordinate(2, 5), new Coordinate(3, 5), new Coordinate(4, 5),
                new Coordinate(6, 5), new Coordinate(7, 5), new Coordinate(8, 5),
                new Coordinate(9, 5), new Coordinate(10, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("차는 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void chaAvailableMovePosition2() {
        Cha cha = new Cha(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), cha);
        pieces.put(new Coordinate(5, 6), new Cha(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = cha.findAvailablePaths(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 6))).isFalse();
    }

    @DisplayName("차는 기물을 만나기 전까지만 이동할 수 있다")
    @Test
    void chaAvailableMovePosition3() {
        Cha cha = new Cha(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), cha);
        pieces.put(new Coordinate(5, 6), new Cha(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = cha.findAvailablePaths(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(5, 7), new Coordinate(5, 8), new Coordinate(5, 9)
        );

        assertThat(availableMovePositions).doesNotContainAnyElementsOf(expected);
    }

    @DisplayName("차는 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void chaAvailableMovePosition4() {
        Cha cha = new Cha(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), cha);
        pieces.put(new Coordinate(5, 6), new Cha(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = cha.findAvailablePaths(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 6))).isTrue();
    }

    @DisplayName("차는 궁성 내에서 대각선을 따라 이동할 수 있다._오른쪽 대각선으로 이동 가능")
    @Test
    void chaAvailableMovePosition5() {
        Cha cha = new Cha(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 4), cha);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = cha.findAvailablePaths(new Coordinate(1, 4), board);

        List<Coordinate> expectedIn = List.of(
                new Coordinate(2, 5), new Coordinate(3, 6));
        List<Coordinate> notExpectedIn = List.of(
                new Coordinate(4, 7), new Coordinate(5, 8), new Coordinate(6, 9));

        assertThat(availableMovePositions).containsAnyElementsOf(expectedIn);
        assertThat(availableMovePositions).doesNotContainAnyElementsOf(notExpectedIn);
    }

}
