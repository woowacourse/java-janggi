package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void 특정_위치에_말이_있다면_true_없다면_false_반환() {

        // given
        final Board board = Board.initialize();

        // when
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(Point.of(0, 0))).isTrue();
            softly.assertThat(board.hasPieceAt(Point.of(0, 1))).isFalse();
        });
    }

    @Test
    void 이동할_위치에_같은_팀_말이_있으면_예외_발생() {

        // given
        Board board = Board.initialize();

        // when
        Position position = board.findPositionBy(Point.of(2, 0));
        Point point = Point.of(1, 2);

        // then
        assertThatThrownBy(() -> board.moveForEnd(position, point)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동할_위치에_같은_팀_말이_없으면_정상_동작() {

        // given
        Board board = Board.initialize();

        // when
        Position position = board.findPositionBy(Point.of(2, 0));
        Point point = Point.of(3, 2);

        // then
        assertThatCode(() -> board.moveForEnd(position, point)).doesNotThrowAnyException();
    }
}
