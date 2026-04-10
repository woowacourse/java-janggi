package domain.piece;

import domain.board.BoardFixtureInitializer;
import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("한나라 진영에서 졸은 하/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Position start = Position.of(3, 2);
        boardFixtureInitializer.put(start, new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(4, 2),
                new Position(3, 1),
                new Position(3, 3)
        );
        assertThat(possibleMoves).doesNotContain(new Position(2, 2));
    }

    @Test
    @DisplayName("초나라 진영에서 졸은 상/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Position start = Position.of(6, 2);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(5, 2),
                new Position(6, 1),
                new Position(6, 3)
        );
        assertThat(possibleMoves).doesNotContain(new Position(7, 2));
    }

    @Test
    @DisplayName("졸은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Position start = Position.of(6, 2);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 3), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(6, 3));
    }

    @Test
    @DisplayName("졸은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Position start = Position.of(6, 2);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(6, 3), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(6, 3));
    }

    @Test
    @DisplayName("졸은 궁성 영역 내의 중앙 좌표에서 전진 2가지 방향 대각선 이동이 추가로 가능하다.")
    void palaceCenterHanSideTest() {
        // given
        Position start = Position.of(8, 4);
        boardFixtureInitializer.put(start, new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(
                new Position(9, 3),
                new Position(9, 5)
        );
        assertThat(possibleMoves).doesNotContain(
                new Position(7, 3),
                new Position(7, 5)
        );
    }

    @Test
    @DisplayName("졸은 궁성 영역 내의 중앙 좌표에서 전진 2가지 방향 대각선 이동이 가능하다.")
    void palaceCenterChuSideTest() {
        // given
        Position start = Position.of(1, 4);
        boardFixtureInitializer.put(start, new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(
                new Position(1, 3),
                new Position(1, 5)
        );
        assertThat(possibleMoves).doesNotContain(
                new Position(2, 3),
                new Position(2, 5)
        );
    }
}
