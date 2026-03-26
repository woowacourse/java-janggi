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

class ChariotTest {

    static class ChariotTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(4, 4), new Chariot(Side.HAN));
            piecesPosition.put(new Position(4, 3), new Guard(Side.HAN));
            piecesPosition.put(new Position(4, 5), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 4), new Guard(Side.HAN));
            piecesPosition.put(new Position(5, 4), new Guard(Side.HAN));


            piecesPosition.put(new Position(4, 8), new Chariot(Side.HAN));
            piecesPosition.put(new Position(6, 8), new Guard(Side.CHU));
            piecesPosition.put(new Position(8, 8), new Guard(Side.CHU));


            piecesPosition.put(new Position(2, 1), new Chariot(Side.HAN));

            return piecesPosition;
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.HAN;
        }
    }

    @Test
    @DisplayName("한나라 진영에서 차는 상/하/좌/우 4가지 방향으로 n 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Board board = new Board(new ChariotTestInitializer());
        Position start = new Position(2, 1);
        Piece chariot = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = chariot.getPossibleMoves(board, start);

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
        Board board = new Board(new ChariotTestInitializer());
        Position start = new Position(4, 4);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("차는 적 기물을 잡으면 멈춰야 한다.")
    void doesNotJumpOpponentTest() {
        // given
        Board board = new Board(new ChariotTestInitializer());
        Position start = new Position(4, 8);
        Piece king = board.getPieceBy(start);

        // when
        List<Position> possibleMoves = king.getPossibleMoves(board, start);

        // then
        assertThat(possibleMoves).contains(new Position(5, 8), new Position(6, 8));
        assertThat(possibleMoves).doesNotContain(new Position(7, 8));
    }
}
