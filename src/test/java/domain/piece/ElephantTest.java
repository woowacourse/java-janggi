package domain.piece;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("상은 상/하/좌/우 4가지 방향으로 1 칸 이동 후 해당 방향의 대각선으로 2 칸 이동한다.")
    void getPossibleMovesTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(1, 2),
                new Position(1, 6),
                new Position(2, 7),
                new Position(6, 7),
                new Position(7, 6),
                new Position(7, 2),
                new Position(6, 1),
                new Position(2, 1)
        );
    }

    @Test
    @DisplayName("상은 1차 경로에 아군 혹은 상대 기물이 있는 경우 뛰어 넘을 수 없다.")
    void firstMoveBlockTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 4), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(3, 4), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(4, 3), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(4, 5), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("상은 2차 경로에 아군 혹은 상대 기물이 있는 경우 뛰어 넘을 수 없다.")
    void secondMoveBlockTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 3), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 5), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(3, 6), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 6), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 3), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 5), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(3, 2), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 2), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("상은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        boardFixtureInitializer.put(Position.of(1, 2), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(1, 2));
    }

    @Test
    @DisplayName("상은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        boardFixtureInitializer.put(Position.of(1, 2), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(1, 2));
    }
}
