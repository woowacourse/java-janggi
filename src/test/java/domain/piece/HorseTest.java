package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Game;
import domain.coordinate.Position;
import domain.board.Side;
import domain.board.BoardInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {
    static class HorseTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(4, 4), new Horse(Side.HAN));

            piecesPosition.put(new Position(1, 1), new Horse(Side.HAN));
            piecesPosition.put(new Position(0, 1), new Horse(Side.CHU));
            piecesPosition.put(new Position(1, 0), new Horse(Side.CHU));
            piecesPosition.put(new Position(2, 1), new Horse(Side.HAN));
            piecesPosition.put(new Position(1, 2), new Horse(Side.HAN));

            piecesPosition.put(new Position(9, 0), new Horse(Side.HAN));
            piecesPosition.put(new Position(7, 1), new Horse(Side.HAN));
            piecesPosition.put(new Position(8, 2), new Horse(Side.CHU));
            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("마는 상/하/좌/우 4가지 방향으로 1 칸 이동 후 해당 방향의 대각선으로 이동한다.")
    void getPossibleMovesTest() {
        // given
        Game game = new Game(new HorseTestInitializer());
        Position start = new Position(4, 4);
        Piece horse = game.getPiece(start);

        // when
        List<Position> possibleMoves = horse.getPossibleMoves(game, start);

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
        Game game = new Game(new HorseTestInitializer());
        Position start = new Position(1, 1);
        Piece horse = game.getPiece(start);

        // when
        List<Position> possibleMoves = horse.getPossibleMoves(game, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("마는 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Game game = new Game(new HorseTestInitializer());
        Position start = new Position(9, 0);
        Piece horse = game.getPiece(start);

        // when
        List<Position> possibleMoves = horse.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(7, 1));
    }

    @Test
    @DisplayName("마는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Game game = new Game(new HorseTestInitializer());
        Position start = new Position(9, 0);
        Piece horse = game.getPiece(start);

        // when
        List<Position> possibleMoves = horse.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(8, 2));
    }
}