package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드판_생성_성공() {

        // given
        // when
        // then
        assertThatCode(Board::initialize).doesNotThrowAnyException();
    }

    @Test
    void 보드판_말_32개_올바르게_생성() {

        // given
        final Board board = Board.initialize();

        // when
        final int pieceCount = board.countPieces();

        // then
        assertThat(pieceCount).isEqualTo(32);
    }

    @Test
    void 해당_위치에_말_존재_유무_확인() {

        // given
        final Point truePoint = Point.of(0, 0);
        final Point falsePoint = Point.of(5, 5);

        final Board board = Board.initialize();

        // when
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceOn(truePoint)).isTrue();
            softly.assertThat(board.hasPieceOn(falsePoint)).isFalse();
        });
    }
}
