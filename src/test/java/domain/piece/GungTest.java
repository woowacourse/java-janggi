package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.coordiante.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GungTest {

    @DisplayName("궁의 이동 가능한 경로를 검사한다."
            + "궁성 중앙에서 이동 가능 경로: (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)")
    @Test
    void gungTest() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(2, 5), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(2, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(2, 6),
                new Coordinate(3, 6), new Coordinate(3, 5), new Coordinate(3, 4), new Coordinate(2, 4)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁은 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void gungTest2() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(2, 5), gung);
        pieces.put(new Coordinate(1, 5), new Sa(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(2, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(1, 5))).isFalse();
    }

    @DisplayName("궁은 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void gungTest3() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(2, 5), gung);
        pieces.put(new Coordinate(1, 5), new Sa(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(2, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(1, 5))).isTrue();
    }

    @DisplayName("궁은 궁성 밖으로 나갈 수 없다")
    @Test
    void gungTest4() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(3, 5), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(3, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(3, 4), new Coordinate(3, 6), new Coordinate(2, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁은 궁성 안에서 대각선을 따라 이동할 수 있다._(1, 1) 방향 대각선으로 이동 가능")
    @Test
    void gungTest5() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 4), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(1, 4), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 4), new Coordinate(2, 5), new Coordinate(1, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁은 궁성 안에서 대각선을 따라 이동할 수 있다._(1, -1) 방향 대각선으로 이동 가능")
    @Test
    void gungTest6() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(1, 6), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(1, 6), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 6), new Coordinate(2, 5), new Coordinate(1, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁은 궁성 안에서 대각선을 따라 이동할 수 있다._(-1, 1) 방향 대각선으로 이동 가능")
    @Test
    void gungTest7() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(3, 4), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(3, 4), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 4), new Coordinate(2, 5), new Coordinate(3, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("궁은 궁성 안에서 대각선을 따라 이동할 수 있다._(-1, 1) 방향 대각선으로 이동 가능")
    @Test
    void gungTest8() {
        Gung gung = new Gung(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(3, 6), gung);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = gung.findAvailablePaths(new Coordinate(3, 6), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 6), new Coordinate(2, 5), new Coordinate(3, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

}
