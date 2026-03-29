package domain.piece;

import domain.Game;
import domain.coordinate.Position;
import domain.board.Side;
import domain.board.BoardInitializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    static class HanSidePawnInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(3, 0), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 2), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 5), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 6), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 7), new Pawn(Side.HAN));
            piecesPosition.put(new Position(4, 6), new Pawn(Side.HAN));

            piecesPosition.put(new Position(4, 7), new Pawn(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    static class ChuSidePawnInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 0), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 2), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 6), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 7), new Pawn(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.CHU;
        }
    }

    @Test
    @DisplayName("한나라 진영에서 졸은 하/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Game game = new Game(new HanSidePawnInitializer());
        Position start = new Position(3, 0);
        Piece pawn = game.getPiece(start);

        // when
        List<Position> possibleMoves = pawn.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(4, 0), new Position(3, 1));
    }

    @Test
    @DisplayName("초나라 진영에서 졸은 상/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Game game = new Game(new ChuSidePawnInitializer());
        Position start = new Position(6, 0);
        Piece pawn = game.getPiece(start);

        // when
        List<Position> possibleMoves = pawn.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(5, 0), new Position(6, 1));
    }

    @Test
    @DisplayName("졸은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Game game = new Game(new HanSidePawnInitializer());
        Position start = new Position(3, 6);
        Piece pawn = game.getPiece(start);

        // when
        List<Position> possibleMoves = pawn.getPossibleMoves(game, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("졸은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Game game = new Game(new HanSidePawnInitializer());
        Position start = new Position(3, 7);
        Piece pawn = game.getPiece(start);

        // when
        List<Position> possibleMoves = pawn.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).contains(new Position(4, 7));
    }
}
