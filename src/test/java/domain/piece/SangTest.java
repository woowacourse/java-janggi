package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SangTest {

    @DisplayName("상의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: (3, 2), (-3, 2), (3, -2), (-3, -2), (2, 3), (-2, 3), (2, -3), (-2, -3)")
    @Test
    void sangTest() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(2, 7), new Coordinate(3, 8),
                new Coordinate(2, 3), new Coordinate(3, 2),
                new Coordinate(7, 2), new Coordinate(8, 3),
                new Coordinate(8, 7), new Coordinate(7, 8)
        );

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("상은 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void sangTest2() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        pieces.put(new Coordinate(2, 7), new Sang(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(2, 7))).isFalse();
    }

    @DisplayName("상은 이동 가능한 경로에 다른 말이 있으면 이동할 수 없다_first에 기물 존재")
    @Test
    void sangTest3() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        pieces.put(new Coordinate(4, 5), new Sang(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(2, 3))).isFalse();
        assertThat(availableMovePositions.contains(new Coordinate(2, 7))).isFalse();
    }

    @DisplayName("상은 이동 가능한 경로에 다른 말이 있으면 이동할 수 없다_second에 기물 존재1")
    @Test
    void sangTest4() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        pieces.put(new Coordinate(3, 6), new Sang(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(2, 7))).isFalse();
    }

    @DisplayName("상은 이동 가능한 경로에 다른 말이 있으면 이동할 수 없다_second에 기물 존재2")
    @Test
    void sangTest5() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        pieces.put(new Coordinate(3, 4), new Sang(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(2, 3))).isFalse();
    }

    @DisplayName("상은 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void sangTest6() {
        Sang sang = new Sang(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), sang);
        pieces.put(new Coordinate(2, 7), new Sang(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = sang.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(2, 7))).isTrue();
    }
}
