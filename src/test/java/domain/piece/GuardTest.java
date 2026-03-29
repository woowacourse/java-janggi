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

public class GuardTest {
    static class HanSideGuardInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(3, 0), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 2), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 4), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 5), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 6), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 7), new Guard(Side.HAN));
            piecesPosition.put(new Position(4, 6), new Guard(Side.HAN));
            piecesPosition.put(new Position(2, 6), new Guard(Side.HAN));

            piecesPosition.put(new Position(4, 7), new Guard(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    static class ChuSideGuardInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 0), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 2), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 6), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 7), new Guard(Side.CHU));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.CHU;
        }
    }


    @Test
    @DisplayName("한나라 진영에서 사는 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Game game = new Game(new HanSideGuardInitializer());
        Position start = new Position(3, 0);
        Piece guard = game.getPiece(start);

        // when
        List<Position> possibleMoves = guard.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(4, 0), new Position(2, 0), new Position(3, 1));
    }

    @Test
    @DisplayName("초나라 진영에서 사는 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Game game = new Game(new ChuSideGuardInitializer());
        Position start = new Position(6, 0);
        Piece guard = game.getPiece(start);

        // when
        List<Position> possibleMoves = guard.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(5, 0), new Position(7, 0), new Position(6, 1));
    }

    @Test
    @DisplayName("사는 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Game game = new Game(new HanSideGuardInitializer());
        Position start = new Position(3, 6);
        Piece guard = game.getPiece(start);

        // when
        List<Position> possibleMoves = guard.getPossibleMoves(game, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Game game = new Game(new HanSideGuardInitializer());
        Position start = new Position(3, 7);
        Piece guard = game.getPiece(start);

        // when
        List<Position> possibleMoves = guard.getPossibleMoves(game, start);

        // then
        assertThat(possibleMoves).contains(new Position(4, 7));
    }
}
