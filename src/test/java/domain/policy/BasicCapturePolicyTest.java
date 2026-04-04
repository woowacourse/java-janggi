package domain.policy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.state.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BasicCapturePolicyTest {

    static class BasicCaptureTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 4), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 5), new Pawn(Side.HAN));
            piecesPosition.put(new Position(6, 6), new Pawn(Side.HAN));
            piecesPosition.put(new Position(5, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(7, 4), new Pawn(Side.HAN));

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
        Board board = new Board(new BasicCaptureTest().initialize());

        MovePolicy movePolicy = new BasicCapturePolicy();
        Position start = new Position(6, 4);

        List<Direction> directions = List.of(Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("상대 기물을 잡을 수 있다.")
    void captureTest() {
        // given
        Board board = new Board(new BasicCaptureTest().initialize());

        MovePolicy movePolicy = new BasicCapturePolicy();
        Position start = new Position(6, 4);
        List<Direction> directions = List.of(Direction.UP);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아군 기물을 잡을 수 있다.")
    void doesNotCaptureTest() {
        // given
        Board board = new Board(new BasicCaptureTest().initialize());

        MovePolicy movePolicy = new BasicCapturePolicy();
        Position start = new Position(6, 4);
        List<Direction> directions = List.of(Direction.DOWN);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(1);
    }
}
