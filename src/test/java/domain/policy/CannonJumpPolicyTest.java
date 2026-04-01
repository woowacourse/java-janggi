package domain.policy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.EmptyPiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CannonJumpPolicyTest {

    static class CannonJumpTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(4, 4), new Cannon(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Pawn(Side.CHU));

            piecesPosition.put(new Position(7, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(9, 4), new Pawn(Side.HAN));

            piecesPosition.put(new Position(4, 6), new Cannon(Side.CHU));
            piecesPosition.put(new Position(4, 7), new Pawn(Side.HAN));


            initializeEmptyPiece(piecesPosition);
            return piecesPosition;
        }

        private void initializeEmptyPiece(Map<Position, Piece> pieceInitPlacements) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    pieceInitPlacements.putIfAbsent(new Position(i, j), EmptyPiece.getInstance());
                }
            }
        }

        @Override
        public Side getFirstTurnSide() {
            return Side.CHU;
        }
    }

    @Test
    @DisplayName("단, 한 개의 기물을 뛰어넘어야 이동 가능 하다.")
    void jumpTest() {
        // given
        Board board = new Board(new CannonJumpTest().initialize());

        MovePolicy movePolicy = new CannonJumpPolicy();
        Position start = new Position(4, 4);

        List<Direction> directions = List.of(
                Direction.DOWN,
                Direction.DOWN,
                Direction.DOWN,
                Direction.DOWN,
                Direction.DOWN
        );

        // when
        List<Position> result = movePolicy.apply(board, start, directions);


        // then
        Assertions.assertThat(result).containsOnly(new Position(7, 4));
    }

    @Test
    @DisplayName("포는 뛰어넘을 수 없다.")
    void capture_Test() {
        // given
        Board board = new Board(new CannonJumpTest().initialize());

        MovePolicy movePolicy = new CannonCapturePolicy();
        Position start = new Position(4, 4);

        List<Direction> directions = List.of(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.RIGHT
        );

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).doesNotContain(new Position(7, 4));
    }
}
