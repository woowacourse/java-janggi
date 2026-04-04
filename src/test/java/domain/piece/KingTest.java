package domain.piece;

import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import domain.board.BoardInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {

    static class kingTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(0, 3), new King(Side.HAN));
            piecesPosition.put(new Position(1, 4), new King(Side.CHU));
            piecesPosition.put(new Position(2, 4), new King(Side.CHU));

            piecesPosition.put(new Position(9, 3), new King(Side.CHU));


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
    @DisplayName("한나라 진영에서 장은 한나라 진영 궁성 영역을 벗어날 수 없다.")
    void hanPalaceTest() {
        // given
        Board board = new Board(new kingTestInitializer().initialize());
        Position start = new Position(0, 3);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(1, 3),
                new Position(0, 4),
                new Position(1, 4)
        );
    }

    @Test
    @DisplayName("초나라 진영에서 장은 초나라 진영 초궁성 영역을 벗어날 수 없다.")
    void chuPalaceTest() {
        // given
        Board board = new Board(new kingTestInitializer().initialize());
        Position start = new Position(9, 3);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(8, 3),
                new Position(9, 4),
                new Position(8, 4)
        );
    }

    @Test
    @DisplayName("궁성 영역 내의 특정 좌표에서 대각선 이동이 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Board board = new Board(new kingTestInitializer().initialize());
        Position start = new Position(1, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5),
                new Position(1, 3),
                new Position(1, 5),
                new Position(2, 3),
                new Position(2, 5)
        );
    }

    @Test
    @DisplayName("장은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Board board = new Board(new kingTestInitializer().initialize());
        Position start = new Position(2, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(new Position(1, 4));
        assertThat(possibleMoves).containsOnly(new Position(2, 3), new Position(2, 5));
    }

    @Test
    @DisplayName("장은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Board board = new Board(new kingTestInitializer().initialize());
        Position start = new Position(0, 3);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(1, 4));
    }
}
