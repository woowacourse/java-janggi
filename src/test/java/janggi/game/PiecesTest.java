package janggi.game;

import janggi.piece.pieces.Piece;
import janggi.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {
    public static final int DEFAULT_UNIT_SIZE = 32;

    @Test
    @DisplayName("Pieces 생성 시 기물을 초기화한다")
    void test() {
        // given
        Pieces pieces = new Pieces();

        // when
        HashMap<Position, Piece> piecesInPosition = pieces.getPieces();

        // then
        Assertions.assertThat(piecesInPosition).hasSize(DEFAULT_UNIT_SIZE);
    }

    @Test
    @DisplayName("해당 위치에 기물이 있다면 반환한다.")
    void test1() {
        // given
        Pieces pieces = new Pieces();

        // when
        Assertions.assertThatCode(() -> pieces.findPieceByPosition(new Position(0, 0)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 위치에 기물이 없다면 예외를 반환한다.")
    void test2() {
        // given
        Pieces pieces = new Pieces();

        // when
        Assertions.assertThatThrownBy(() -> pieces.findPieceByPosition(new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("선택한 기물이 포 기물인지 확인한다.")
    void test3() {
        // given
        Pieces pieces = new Pieces();

        // when
        boolean bombPiece = pieces.isBombPiece(new Position(1, 2));

        // then
        Assertions.assertThat(bombPiece).isTrue();
    }

    @Test
    @DisplayName("도착지점까지 이동한다.")
    void test4() {
        // given
        Pieces pieces = new Pieces();

        // when
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(0, 1);
        pieces.moveAndCaptureIfEnemyExists(startPosition, endPosition);

        // then
        Assertions.assertThat(pieces.isExistPiece(endPosition)).isTrue();
        Assertions.assertThat(pieces.getPieces()).hasSize(DEFAULT_UNIT_SIZE);
    }

    @Test
    @DisplayName("도착지점에 기물이 있다면 없애고 이동한다.")
    void test5() {
        // given
        Pieces pieces = new Pieces();

        // when
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 0);
        pieces.moveAndCaptureIfEnemyExists(startPosition, endPosition);

        // then
        Assertions.assertThat(pieces.isExistPiece(endPosition)).isTrue();
        Assertions.assertThat(pieces.getPieces()).hasSize(DEFAULT_UNIT_SIZE - 1);
    }
}