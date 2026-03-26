package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.Board;
import domain.board.BoardInitializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {

    static class HanSideKingInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(1, 4), new King(Side.HAN));

            piecesPosition.put(new Position(0, 3), new Guard(Side.HAN));
            piecesPosition.put(new Position(0, 5), new Guard(Side.HAN));

            piecesPosition.put(new Position(3, 5), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 6), new King(Side.HAN));
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

    static class ChuSideKingInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 0), new King(Side.CHU));
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
    @DisplayName("한나라 진영에서 장은 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Board board = new Board(new HanSideKingInitializer());
        Position start = new Position(1, 4);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        assertThat(possibleMoves).contains(new Position(1, 3), new Position(2, 4), new Position(0, 4), new Position(1, 5));
    }

    @Test
    @DisplayName("초나라 진영에서 장은 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Board board = new Board(new ChuSideKingInitializer());
        Position start = new Position(6, 0);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        assertThat(possibleMoves).contains(new Position(5, 0), new Position(7, 0), new Position(6, 1));
    }

    @Test
    @DisplayName("장은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Board board = new Board(new HanSideKingInitializer());
        Position start = new Position(3, 6);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장은 상대 기물이 있는 위치로 이동할 수 있다.")
    void getChuPossibleMoves__Test() {
        // given
        Board board = new Board(new HanSideKingInitializer());
        Position start = new Position(3, 7);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        assertThat(possibleMoves).contains(new Position(4, 7));
    }
}
