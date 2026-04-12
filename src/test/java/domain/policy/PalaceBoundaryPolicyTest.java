package domain.policy;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Guard;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PalaceBoundaryPolicyTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("궁성 영역을 넘어갈 수 없다.")
    void applyTest() {
        // given
        Position start = Position.of(1, 3);
        boardFixtureInitializer.put(start, new Guard(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new PalaceBoundaryPolicy();
        List<Direction> directions = List.of(Direction.LEFT, Direction.LEFT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(0);
    }
}