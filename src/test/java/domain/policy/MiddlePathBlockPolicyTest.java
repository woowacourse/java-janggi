package domain.policy;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.piece.*;
import domain.state.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MiddlePathBlockPolicyTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("중간 경로가 비어있으면 이동 가능하다.")
    void pass_Test() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        List<Direction> directions = List.of(Direction.UP, Direction.UP_RIGHT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).containsOnly(new Position(2, 5));
    }

    @Test
    @DisplayName("1차 중간 경로가 막히면 이동 할 수 없다.")
    void firstBlock_Test() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(3, 4), new Horse(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        List<Direction> directions = List.of(Direction.UP, Direction.UP_RIGHT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("2차 중간 경로가 막히면 이동 할 수 없다.")
    void secondBlock_Test() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Elephant(Side.CHU));
        boardFixtureInitializer.put(Position.of(3, 6), new Horse(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        List<Direction> directions = List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).isEmpty();
    }
}
