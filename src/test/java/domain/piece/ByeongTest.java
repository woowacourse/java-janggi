package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ByeongTest {

    @DisplayName("한나라 병의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: (1, 0), (0, 1), (0, -1)")
    @Test
    void byeongTest() {
        Byeong byeong = new Byeong(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), byeong);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = byeong.availableMovePositions(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(5, 4), new Coordinate(5, 6), new Coordinate(6, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("초나라 병의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: (-1, 0), (0, 1), (0, -1)")
    @Test
    void byeongTest2() {
        Byeong byeong = new Byeong(Country.CHO);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), byeong);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = byeong.availableMovePositions(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(5, 4), new Coordinate(5, 6), new Coordinate(4, 5)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("병은 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void byeongTest4() {
        Byeong byeong = new Byeong(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(4, 5), byeong);
        pieces.put(new Coordinate(5, 5), new Byeong(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = byeong.availableMovePositions(new Coordinate(4, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 5))).isFalse();
    }

    @DisplayName("병은 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void byeongTest5() {
        Byeong byeong = new Byeong(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(4, 5), byeong);
        pieces.put(new Coordinate(5, 5), new Byeong(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = byeong.availableMovePositions(new Coordinate(4, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(5, 5))).isTrue();
    }
}
