package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.coordinate.Position;
import domain.board.Side;
import domain.board.BoardInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    static class CannonTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(9, 0), new Cannon(Side.HAN));
            piecesPosition.put(new Position(2, 0), new Pawn(Side.HAN));
            piecesPosition.put(new Position(9, 6), new Pawn(Side.CHU));

            piecesPosition.put(new Position(7, 1), new Cannon(Side.HAN));
            piecesPosition.put(new Position(1, 1), new Pawn(Side.HAN));
            piecesPosition.put(new Position(4, 1), new Pawn(Side.CHU));
            piecesPosition.put(new Position(7, 5), new Pawn(Side.CHU));
            piecesPosition.put(new Position(7, 6), new Pawn(Side.HAN));

            piecesPosition.put(new Position(8, 6), new Cannon(Side.HAN));
            piecesPosition.put(new Position(6, 6), new Pawn(Side.CHU));

            piecesPosition.put(new Position(4, 4), new Cannon(Side.HAN));
            piecesPosition.put(new Position(4, 5), new Cannon(Side.CHU));
            piecesPosition.put(new Position(4, 3), new Cannon(Side.HAN));
            piecesPosition.put(new Position(4, 7), new Pawn(Side.CHU));
            piecesPosition.put(new Position(4, 2), new Pawn(Side.CHU));
            piecesPosition.put(new Position(2, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(1, 4), new Cannon(Side.CHU));

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
            return Side.HAN;
        }
    }

    static class norMalPieceMultiJumpTestInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(7, 4), new Cannon(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Pawn(Side.CHU));

            piecesPosition.put(new Position(3, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(2, 4), new King(Side.HAN));

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
    @DisplayName("포는 상/하/좌/우 4가지 방향으로 포를 제외한 다른 1개의 기물을 뛰어 넘은 후, n 칸 이동 가능하다.")
    void getPossibleMovesTest() {
        // given
        Board board = new Board(new CannonTestInitializer().initialize());
        Position start = new Position(9, 0);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(0, 0),
                new Position(1, 0),
                new Position(9, 7),
                new Position(9, 8));
    }

    @Test
    @DisplayName("포는 정확히 단 1 개의 기물을 뛰어넘을 수 있다.")
    void doesNotJumpTest() {
        // given
        Board board = new Board(new CannonTestInitializer().initialize());
        Position start = new Position(7, 1);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(2, 1),
                new Position(3, 1));
    }

    @Test
    @DisplayName("포는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Board board = new Board(new CannonTestInitializer().initialize());
        Position start = new Position(8, 6);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(6, 6));
    }

    @Test
    @DisplayName("포는 아군과 상대 포 모두 뛰어넘거나 잡아먹을 수 없다.")
    void doesNotCaptureAndJumpCannonTest() {
        // given
        Board board = new Board(new CannonTestInitializer().initialize());
        Position start = new Position(4, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
        assertThat(possibleMoves).doesNotContain(new Position(1, 4));
    }

    @Test
    @DisplayName("포는 일반 기물을 여러 개 뛰어넘을 수 없다.")
    void doesNotMultiJumpTest() {
        // given
        Board board = new Board(new norMalPieceMultiJumpTestInitializer().initialize());
        Position start = new Position(7, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).doesNotContain(
                new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4));
    }
}