package domain.piece;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("한나라 진영에서 장은 한나라 진영 궁성 영역을 벗어날 수 없다.")
    void hanPalaceTest() {
        // given
        Position start = Position.of(0, 3);
        boardFixtureInitializer.put(start, new King(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(1, 3),
                new Position(0, 4),
                new Position(1, 4)
        );
    }

    @Test
    @DisplayName("초나라 진영에서 장은 초나라 진영 초궁성 영역을 벗어날 수 없다.")
    void chuPalaceTest() {
        // given
        Position start = Position.of(9, 3);
        boardFixtureInitializer.put(start, new King(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(8, 3),
                new Position(9, 4),
                new Position(8, 4)
        );
    }

    @Test
    @DisplayName("궁성 영역 내의 특정 좌표에서 대각선 이동이 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Position start = Position.of(1, 4);
        boardFixtureInitializer.put(start, new King(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5),
                new Position(1, 3),
                new Position(1, 5),
                new Position(2, 3),
                new Position(2, 4),
                new Position(2, 5)
        );
    }

    @Test
    @DisplayName("장은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Position start = Position.of(1, 4);
        boardFixtureInitializer.put(start, new King(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 4), new Guard(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(2, 4));
    }

    @Test
    @DisplayName("장은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(1, 4);
        boardFixtureInitializer.put(start, new King(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 4), new Chariot(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(2, 4));
    }
}
