package domain.piece;

import domain.Game;
import domain.Position;
import domain.Side;
import domain.board.BoardInitializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    static class ElephantTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(4, 4), new Elephant(Side.HAN));

            piecesPosition.put(new Position(0, 0), new Elephant(Side.HAN));
            piecesPosition.put(new Position(0, 1), new Horse(Side.CHU));
            piecesPosition.put(new Position(1, 0), new Horse(Side.HAN));

            piecesPosition.put(new Position(9, 8), new Elephant(Side.HAN));
            piecesPosition.put(new Position(7, 7), new Horse(Side.CHU));
            piecesPosition.put(new Position(8, 6), new Horse(Side.HAN));

            piecesPosition.put(new Position(9, 0), new Elephant(Side.HAN));
            piecesPosition.put(new Position(6, 2), new Horse(Side.HAN));
            piecesPosition.put(new Position(7, 3), new Horse(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("상은 상/하/좌/우 4가지 방향으로 1 칸 이동 후 해당 방향의 대각선으로 2 칸 이동한다.")
    void getPossibleMovesTest() {
        // given
        Game game = new Game(new ElephantTestInitializer());
        Position start = new Position(4, 4);
        Piece elephant = game.getPiece(start);

        // when
        List<Position> possibleMoves = elephant.getPossibleMoves(game, start);

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
        Game game = new Game(new ElephantTestInitializer());
        Position start = new Position(0, 0);
        Piece elephant = game.getPiece(start);

        // when
        List<Position> possibleMoves = elephant.getPossibleMoves(game, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("상은 2차 경로에 아군 혹은 상대 기물이 있는 경우 뛰어 넘을 수 없다.")
    void secondMoveBlockTest() {
        // given
        Game game = new Game(new ElephantTestInitializer());
        Position start = new Position(9, 8);
        Piece elephant = game.getPiece(start);

        // when
        List<Position> possibleMoves = elephant.getPossibleMoves(game, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("상은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Game game = new Game(new ElephantTestInitializer());
        Position start = new Position(9, 0);
        Piece elephant = game.getPiece(start);

        // when
        List<Position> possibleMoves = elephant.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(6, 2));
    }

    @Test
    @DisplayName("마는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Game game = new Game(new ElephantTestInitializer());
        Position start = new Position(9, 0);
        Piece elephant = game.getPiece(start);

        // when
        List<Position> possibleMoves = elephant.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(7, 3));
    }
}