package domain.policy;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.state.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BasicCapturePolicyTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("목적지가 비어있으면 이동할 수 있다.")
    void empty_Pass_Test() {
        // given
        Position start = Position.of(6, 4);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new BasicCapturePolicy();
        List<Direction> directions = List.of(Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("상대 기물을 잡을 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(6, 4);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 4), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new BasicCapturePolicy();
        List<Direction> directions = List.of(Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아군 기물을 잡을 수 없다.")
    void doesNotCaptureTest() {
        // given
        Position start = Position.of(6, 4);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 4), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        MovePolicy movePolicy = new BasicCapturePolicy();
        List<Direction> directions = List.of(Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(0);
    }
}
