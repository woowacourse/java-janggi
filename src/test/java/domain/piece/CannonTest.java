package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("포는 상/하/좌/우 4가지 방향으로 포를 제외한 다른 1개의 기물을 뛰어 넘은 후, n 칸 이동 가능하다.")
    void getPossibleMovesTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 2), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(4, 1), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(7, 3),
                new Position(7, 4),
                new Position(7, 5),
                new Position(7, 6),
                new Position(7, 7),
                new Position(7, 8),
                new Position(3, 1),
                new Position(2, 1),
                new Position(1, 1),
                new Position(0, 1)
        );
    }

    @Test
    @DisplayName("포는 정확히 단 1 개의 기물을 뛰어넘을 수 있다.")
    void doesNotJumpTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 2), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 4), new Horse(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(7, 3));
        assertThat(possibleMoves).doesNotContain(new Position(7, 4));
    }

    @Test
    @DisplayName("포는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 2), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 4), new Horse(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(7, 4));
    }

    @Test
    @DisplayName("포는 아군과 상대 포 모두 뛰어넘거나 잡아먹을 수 없다.")
    void doesNotCaptureAndJumpCannonTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 2), new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 4), new Horse(Side.HAN));
        boardFixtureInitializer.put(Position.of(6, 1), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(5, 1), new Cannon(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(7, 4), new Position(5, 1));
    }

    @Test
    @DisplayName("포는 일반 기물을 여러 개 뛰어넘을 수 없다.")
    void doesNotMultiJumpTest() {
        // given
        Position start = Position.of(7, 1);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 2), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 3), new Horse(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 4), new Horse(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(7, 4));
    }

    @Test
    @DisplayName("포는 궁성 영역 내의 대각 끝 좌표에서 1 방향 대각선 이동이 가능하다.")
    void palaceCenterTest() {
        // given
        Position start = Position.of(7, 3);
        boardFixtureInitializer.put(start, new Cannon(Side.CHU));
        boardFixtureInitializer.put(Position.of(8, 4), new King(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(9, 5));
        assertThat(possibleMoves).doesNotContain(new Position(8, 4));
    }
}