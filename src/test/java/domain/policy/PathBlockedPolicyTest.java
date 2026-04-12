package domain.policy;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.state.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PathBlockedPolicyTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("다른 기물을 뛰어넘을 수 없다.")
    void firstBlock_Test() {
        // given
        Position start = Position.of(9, 0);
        boardFixtureInitializer.put(start, new Chariot(Side.CHU));
        boardFixtureInitializer.put(Position.of(8, 0), new Pawn(Side.HAN));
        boardFixtureInitializer.put(Position.of(7, 0), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new PathBlockedPolicy();
        List<Direction> directions = List.of(Direction.UP, Direction.UP, Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).containsOnly(new Position(8, 0));
        Assertions.assertThat(result).doesNotContain(new Position(7, 0));
    }
}
