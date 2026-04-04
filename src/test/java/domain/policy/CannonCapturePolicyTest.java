package domain.policy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.state.Side;
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

class CannonCapturePolicyTest {

    static class CannonCaptureTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(7, 1), new Cannon(Side.CHU));
            piecesPosition.put(new Position(7, 2), new Pawn(Side.CHU));
            piecesPosition.put(new Position(7, 7), new Cannon(Side.HAN));
            piecesPosition.put(new Position(6, 1), new Pawn(Side.CHU));

            piecesPosition.put(new Position(3, 1), new Pawn(Side.HAN));
            piecesPosition.put(new Position(2, 1), new Pawn(Side.HAN));

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
    }

    @Test
    @DisplayName("목적지가 비어있으면 이동할 수 있다.")
    void empty_Pass_Test() {
        // given
        Board board = new Board(new CannonCaptureTest().initialize());

        MovePolicy movePolicy = new CannonCapturePolicy();
        Position start = new Position(7, 1);

        List<Direction> directions = List.of(Direction.UP, Direction.UP, Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).contains(
                new Position(5, 1),
                new Position(4, 1)
        );
    }

    @Test
    @DisplayName("포를 제외한 일반 상대 기물은 잡을 수 있다.")
    void capture_Test() {
        // given
        Board board = new Board(new CannonCaptureTest().initialize());

        MovePolicy movePolicy = new CannonCapturePolicy();
        Position start = new Position(7, 1);

        List<Direction> directions = List.of(
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP
        );

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).contains(new Position(2, 1));
    }

    @Test
    @DisplayName("포는 잡을 수 없다.")
    void doesNotCaptureCannon_Test() {
        // given
        Board board = new Board(new CannonCaptureTest().initialize());

        MovePolicy movePolicy = new CannonCapturePolicy();
        Position start = new Position(7, 1);

        List<Direction> directions = List.of(Direction.RIGHT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).doesNotContain(new Position(7, 7));
    }
}
