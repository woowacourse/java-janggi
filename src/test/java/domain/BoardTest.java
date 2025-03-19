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
    void 좌표에_해당되는_포지션_반환() {

        // given
        final Board board = Board.initialize();
        final Point point = Point.of(0, 0);
        final Position expectedPosition = new Position(point, PieceFactory.createGreenTeam(Chariot::new));

        // when
        final Position position = board.findPositionBy(point);

        // then
        assertThat(position).isEqualTo(expectedPosition);
    }

    @Test
    void 상의_이동_경로에_말이_있으면_true_없으면_false_반환() {

        // given
        final Board board = Board.initialize();

        // when
        final Position treuPosition = board.findPositionBy(Point.of(1, 0));
        final Position falsePosition = board.findPositionBy(Point.of(6, 0));

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(board.hasPieceOnPath(treuPosition, Point.of(3, 3))).isTrue();
            assertThat(board.hasPieceOnPath(falsePosition, Point.of(8, 3))).isFalse();
        });
    }
}
