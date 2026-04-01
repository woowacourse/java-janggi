package domain.policy;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Horse;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class MiddlePathBlockPolicyTest {

    static class MiddlePathBlockTest implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(4, 4), new Horse(Side.CHU));
            piecesPosition.put(new Position(3, 4), new Horse(Side.CHU));
            piecesPosition.put(new Position(3, 6), new Horse(Side.CHU));

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
    @DisplayName("중간 경로가 비어있으면 이동 가능하다.")
    void pass_Test() {
        // given
        Board board = new Board(new MiddlePathBlockTest().initialize());

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        Position start = new Position(4, 4);

        List<Direction> directions = List.of(Direction.DOWN, Direction.DOWN_LEFT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).contains(new Position(6, 3));
    }

    @Test
    @DisplayName("1차 중간 경로가 막히면 이동 할 수 없다.")
    void firstBlock_Test() {
        // given
        Board board = new Board(new MiddlePathBlockTest().initialize());

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        Position start = new Position(4, 4);

        List<Direction> directions = List.of(Direction.UP, Direction.UP_LEFT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("2차 중간 경로가 막히면 이동 할 수 없다.")
    void secondBlock_Test() {
        // given
        Board board = new Board(new MiddlePathBlockTest().initialize());

        MovePolicy movePolicy = new MiddlePathBlockPolicy();
        Position start = new Position(4, 4);

        List<Direction> directions = List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT);

        // when
        List<Position> result = movePolicy.apply(board, start, directions);

        // then
        Assertions.assertThat(result).isEmpty();
    }
}
