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

class ChariotTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("한나라 진영에서 차는 상/하/좌/우 4가지 방향으로 n 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Position start = Position.of(2, 1);
        boardFixtureInitializer.put(start, new Chariot(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(2, 0),
                new Position(2, 2),
                new Position(2, 3),
                new Position(2, 4),
                new Position(2, 5),
                new Position(2, 6),
                new Position(2, 7),
                new Position(2, 8),
                new Position(1, 1),
                new Position(0, 1),
                new Position(3, 1),
                new Position(4, 1),
                new Position(5, 1),
                new Position(6, 1),
                new Position(7, 1),
                new Position(8, 1),
                new Position(9, 1));
    }

    @Test
    @DisplayName("차는 아군 기물을 뛰어넘을 수 없다.")
    void doesNotJumpTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Chariot(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 5), new Pawn(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 3), new Pawn(Side.HAN));
        boardFixtureInitializer.put(Position.of(3, 4), new Pawn(Side.HAN));
        boardFixtureInitializer.put(Position.of(5, 4), new Pawn(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("차는 적 기물을 잡으면 멈춰야 한다.")
    void doesNotJumpOpponentTest() {
        // given
        Position start = Position.of(4, 4);
        boardFixtureInitializer.put(start, new Chariot(Side.HAN));
        boardFixtureInitializer.put(Position.of(6, 4), new Pawn(Side.CHU));
        boardFixtureInitializer.put(Position.of(7, 4), new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(6, 4));
        assertThat(possibleMoves).doesNotContain(new Position(7, 4));
    }

    @Test
    @DisplayName("차는 궁성 영역 내의 중앙 좌표에서 4가지 방향 대각선 이동이 추가로 가능하다.")
    void palaceCenterTest() {
        // given
        Position start = Position.of(8, 4);
        boardFixtureInitializer.put(start, new Chariot(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(
                new Position(8, 3),
                new Position(8, 5),
                new Position(9, 4),
                new Position(7, 4),
                new Position(7, 3),
                new Position(7, 5),
                new Position(9, 3),
                new Position(9, 5)
        );
    }

    @Test
    @DisplayName("차는 궁성 영역 내의 대각 끝 좌표에서 1 방향 대각선 이동이 가능하다.")
    void palaceEdgeTest() {
        // given
        Position start = Position.of(2, 3);
        boardFixtureInitializer.put(start, new Chariot(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(
                new Position(1, 4),
                new Position(0, 5)
        );
    }
}
