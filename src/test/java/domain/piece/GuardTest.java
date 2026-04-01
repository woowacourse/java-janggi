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

public class GuardTest {

    static class HanSideGuardInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(3, 0), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 2), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 4), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 5), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 6), new Guard(Side.HAN));
            piecesPosition.put(new Position(3, 7), new Guard(Side.HAN));
            piecesPosition.put(new Position(4, 6), new Guard(Side.HAN));
            piecesPosition.put(new Position(2, 6), new Guard(Side.HAN));

            piecesPosition.put(new Position(4, 7), new Guard(Side.CHU));

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

    static class ChuSideGuardInitializer implements BoardInitializer {

        @Override
        public Map<Position, Piece> initialize() {
            Map<Position, Piece> piecesPosition = new HashMap<>();

            piecesPosition.put(new Position(6, 0), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 2), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 4), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 6), new Guard(Side.CHU));
            piecesPosition.put(new Position(6, 7), new Guard(Side.CHU));

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
    @DisplayName("한나라 진영에서 사는 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getHanPossibleMovesTest() {
        // given
        Board board = new Board(new HanSideGuardInitializer().initialize());
        Position start = new Position(3, 0);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(4, 0), new Position(2, 0), new Position(3, 1));
    }

    @Test
    @DisplayName("초나라 진영에서 사는 상/하/좌/우 4가지 방향으로 1 칸 이동 가능하다.")
    void getChuPossibleMovesTest() {
        // given
        Board board = new Board(new ChuSideGuardInitializer().initialize());
        Position start = new Position(6, 0);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).containsOnly(new Position(5, 0), new Position(7, 0), new Position(6, 1));
    }

    @Test
    @DisplayName("사는 아군 기물이 있는 위치로 이동할 수 없다.")
    void doesNotMoveTest() {
        // given
        Board board = new Board(new HanSideGuardInitializer().initialize());
        Position start = new Position(3, 6);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        Assertions.assertThat(possibleMoves.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Board board = new Board(new HanSideGuardInitializer().initialize());
        Position start = new Position(3, 7);

        // when
        List<Position> possibleMoves = board.calculatePossibleMoves(start);

        // then
        assertThat(possibleMoves).contains(new Position(4, 7));
    }
}
