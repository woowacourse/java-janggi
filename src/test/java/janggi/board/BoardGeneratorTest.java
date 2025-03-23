package janggi.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.Point;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardGeneratorTest {

    @DisplayName("초나라 초기 세팅이 올바른지 확인한다.")
    @Test
    void verifyChuInitialSetupTest() {
        // when
        Board board = BoardGenerator.generate();

        // then
        assertThat(board.getPiece(new Point(0, 3))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(2, 3))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(4, 3))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(6, 3))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(8, 3))).isInstanceOf(Soldier.class);

        assertThat(board.getPiece(new Point(1, 2))).isInstanceOf(Cannon.class);
        assertThat(board.getPiece(new Point(7, 2))).isInstanceOf(Cannon.class);

        assertThat(board.getPiece(new Point(4, 1))).isInstanceOf(General.class);

        assertThat(board.getPiece(new Point(0, 0))).isInstanceOf(Chariot.class);
        assertThat(board.getPiece(new Point(1, 0))).isInstanceOf(Elephant.class);
        assertThat(board.getPiece(new Point(2, 0))).isInstanceOf(Horse.class);
        assertThat(board.getPiece(new Point(3, 0))).isInstanceOf(Guard.class);
        assertThat(board.getPiece(new Point(5, 0))).isInstanceOf(Guard.class);
        assertThat(board.getPiece(new Point(6, 0))).isInstanceOf(Elephant.class);
        assertThat(board.getPiece(new Point(7, 0))).isInstanceOf(Horse.class);
        assertThat(board.getPiece(new Point(8, 0))).isInstanceOf(Chariot.class);
    }

    @DisplayName("한나라 초기 세팅이 올바른지 확인한다.")
    @Test
    void verifyHanInitialSetupTest() {
        // when
        Board board = BoardGenerator.generate();

        // then
        assertThat(board.getPiece(new Point(0, 6))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(2, 6))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(4, 6))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(6, 6))).isInstanceOf(Soldier.class);
        assertThat(board.getPiece(new Point(8, 6))).isInstanceOf(Soldier.class);

        assertThat(board.getPiece(new Point(1, 7))).isInstanceOf(Cannon.class);
        assertThat(board.getPiece(new Point(7, 7))).isInstanceOf(Cannon.class);

        assertThat(board.getPiece(new Point(4, 8))).isInstanceOf(General.class);

        assertThat(board.getPiece(new Point(0, 9))).isInstanceOf(Chariot.class);
        assertThat(board.getPiece(new Point(1, 9))).isInstanceOf(Elephant.class);
        assertThat(board.getPiece(new Point(2, 9))).isInstanceOf(Horse.class);
        assertThat(board.getPiece(new Point(3, 9))).isInstanceOf(Guard.class);
        assertThat(board.getPiece(new Point(5, 9))).isInstanceOf(Guard.class);
        assertThat(board.getPiece(new Point(6, 9))).isInstanceOf(Elephant.class);
        assertThat(board.getPiece(new Point(7, 9))).isInstanceOf(Horse.class);
        assertThat(board.getPiece(new Point(8, 9))).isInstanceOf(Chariot.class);
    }

    @DisplayName("총 기물은 32개여야 한다.")
    @Test
    void pieceCount32OnGenerateBoard() {
        // when
        Board board = BoardGenerator.generate();
        Map<Point, Piece> placedPieces = board.getPlacedPieces();
        int count = (int) placedPieces.values().stream()
                .filter(Piece::exists)
                .count();

        // then
        assertThat(count)
            .isSameAs(32);
    }
}
