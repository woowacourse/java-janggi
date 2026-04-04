package domain.piece;

import domain.board.Board;
import domain.coordinate.Position;
import domain.state.Side;
import domain.board.BoardInitializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    static class HanSidePawnInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(3, 0), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 2), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 5), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 6), new Pawn(Side.HAN));
            piecesPosition.put(new Position(3, 7), new Pawn(Side.HAN));
            piecesPosition.put(new Position(4, 6), new Pawn(Side.HAN));

            piecesPosition.put(new Position(4, 7), new Pawn(Side.CHU));

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

    static class ChuSidePawnInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 0), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 2), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 6), new Pawn(Side.CHU));
            piecesPosition.put(new Position(6, 7), new Pawn(Side.CHU));

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

    static class PalacePawnInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(8, 4), new Pawn(Side.HAN));
            piecesPosition.put(new Position(1, 4), new Pawn(Side.CHU));

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
    @DisplayName("한나라 진영에서 졸은 하/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Board board = new Board(new HanSidePawnInitializer().initialize());
        Position start = new Position(3, 0);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(4, 0), new Position(3, 1));
    }

    @Test
    @DisplayName("초나라 진영에서 졸은 상/좌/우 3가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Board board = new Board(new ChuSidePawnInitializer().initialize());
        Position start = new Position(6, 0);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(5, 0), new Position(6, 1));
    }

    @Test
    @DisplayName("졸은 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Board board = new Board(new HanSidePawnInitializer().initialize());
        Position start = new Position(3, 6);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("졸은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Board board = new Board(new HanSidePawnInitializer().initialize());
        Position start = new Position(3, 7);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(4, 7));
    }

    @Test
    @DisplayName("졸은 궁성 영역 내의 중앙 좌표에서 전진 2가지 방향 대각선 이동이 가능하다.")
    void palaceCenterHanSideTest() {
        // given
        Board board = new Board(new PalacePawnInitializer().initialize());
        Position start = new Position(8, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(8, 3),
                new Position(8, 5),
                new Position(9, 3),
                new Position(9, 4),
                new Position(9, 5)
        );
    }

    @Test
    @DisplayName("졸은 궁성 영역 내의 중앙 좌표에서 전진 2가지 방향 대각선 이동이 가능하다.")
    void palaceCenterChuSideTest() {
        // given
        Board board = new Board(new PalacePawnInitializer().initialize());
        Position start = new Position(1, 4);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(
                new Position(1, 3),
                new Position(1, 5),
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5)
        );
    }

    @Test
    @DisplayName("차는 궁성 영역 내의 대각 끝 좌표에서 1 방향 대각선 이동이 가능하다.")
    void palaceEdgeTest() {
        // given
        Board board = new Board(new ChariotTest.ChariotPalaceTestInitializer().initialize());
        Position start = new Position(2, 3);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(
                new Position(1, 4),
                new Position(0, 5)
        );
    }
}
