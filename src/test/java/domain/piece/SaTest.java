package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.coordiante.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaTest {

    @DisplayName("사의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)")
    @Test
    void saTest() {
        Sa sa = new Sa(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sa);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sa.findAvailablePaths(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(5, 4), new Coordinate(5, 6), new Coordinate(4, 5), new Coordinate(6, 5),
                new Coordinate(4, 4), new Coordinate(6, 6), new Coordinate(4, 6), new Coordinate(6, 4)
        );

        assertThat(availableMovePositions).containsAnyElementsOf(expected);
    }

    @DisplayName("사는 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void saTest2() {
        Sa sa = new Sa(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sa);
        pieces.put(new Coordinate(5, 6), new Sa(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sa.findAvailablePaths(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 6))).isFalse();
    }

    @DisplayName("사는 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void saTest3() {
        Sa sa = new Sa(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sa);
        pieces.put(new Coordinate(5, 6), new Sa(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sa.findAvailablePaths(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 6))).isTrue();
    }

    @DisplayName("사는 궁성 안에서 대각선을 따라 이동할 수 있다.")
    @Test
    void saTest4() {
        Sa sa = new Sa(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(2, 5), sa);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sa.findAvailablePaths(new Coordinate(2, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(2, 6),
                new Coordinate(3, 6), new Coordinate(3, 5), new Coordinate(3, 4), new Coordinate(2, 4)
        );

        assertThat(availableMovePositions).containsAnyElementsOf(expected);
    }

    @DisplayName("사는 궁성 안에서 대각선을 따라 이동할 수 있다.")
    @Test
    void saTest5() {
        Sa sa = new Sa(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 4), sa);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sa.findAvailablePaths(new Coordinate(1, 4), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 4), new Coordinate(2, 5), new Coordinate(1, 5)
        );

        assertThat(availableMovePositions).containsAnyElementsOf(expected);
    }
}
