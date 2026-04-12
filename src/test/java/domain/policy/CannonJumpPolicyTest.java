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

class CannonJumpPolicyTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("단, 한 개의 기물을 뛰어넘어야 이동 가능 하다.")
    void jumpTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 1), new Chariot(Side.HAN));
        boardFixtureInitializer.put(Position.of(5, 1), new Elephant(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 1), new Horse(Side.HAN));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new CannonJumpPolicy();
        List<Direction> directions = List.of(
                Direction.UP,
                Direction.UP,
                Direction.UP
        );

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).containsOnly(new Position(5, 1));
        Assertions.assertThat(result).doesNotContain(new Position(4, 1));
    }

    @Test
    @DisplayName("포는 뛰어넘을 수 없다.")
    void capture_Test() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 1), new Cannon(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 1), new Horse(Side.HAN));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new CannonJumpPolicy();
        List<Direction> directions = List.of(
                Direction.UP,
                Direction.UP,
                Direction.UP
        );

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }
}
