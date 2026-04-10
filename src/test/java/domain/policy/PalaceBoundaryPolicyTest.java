package domain.policy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PalaceBoundaryPolicyTest {

    static class PalaceTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(1, 3), new Guard(Side.HAN));

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
    @DisplayName("궁성 영역을 넘어갈 수 없다.")
    void applyTest() {
        // given
        Board board = new Board(new PalaceTest().initialize());
        MovePolicy movePolicy = new PalaceBoundaryPolicy();
        Position start = new Position(1, 3);
        List<Direction> directions = List.of(Direction.LEFT, Direction.LEFT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        assertThat(result.size()).isEqualTo(0);
    }
}