package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("마는 상/하/좌/우 4가지 방향으로 1 칸 이동 후 해당 방향의 대각선으로 이동한다.")
    void getPossibleMovesTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(2, 3),
                new Position(2, 5),
                new Position(3, 2),
                new Position(3, 6),
                new Position(5, 2),
                new Position(5, 6),
                new Position(6, 3),
                new Position(6, 5)
        );
    }

    @Test
    @DisplayName("마는 1차 경로에 아군 혹은 상대 기물이 있는 경우 뛰어 넘을 수 없다.")
    void firstMoveBlockTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
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
    @DisplayName("마는 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 3), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(4, 4));
    }

    @Test
    @DisplayName("마는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(2, 3), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(2, 3));
    }
}
