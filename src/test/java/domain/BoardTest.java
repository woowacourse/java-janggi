package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import view.OutputView;

class BoardTest {

    @Test
    void 보드판_생성_성공() {

        // given
        // when
        // then
        assertThatCode(BoardFactory::create).doesNotThrowAnyException();
    }

    @Test
    void 보드판_말_32개_올바르게_생성() {

        // given
        final Board board = BoardFactory.create();

        // when
        final int pieceCount = board.countPieces();

        // then
        assertThat(pieceCount).isEqualTo(32);
    }

    @Test
    void 좌표에_해당되는_포지션_반환() {

        // given
        final Board board = BoardFactory.create();
        final Point point = Point.of(0, 0);
        final Position expectedPosition = new Position(point,
                PieceFactory.createGreenTeam(Chariot::new, Score.CHARIOT));

        // when
        final Position position = board.findPositionBy(point);

        // then
        assertThat(position).isEqualTo(expectedPosition);
    }

    @Test
    void 상의_이동_경로에_말이_있으면_true_없으면_false_반환() {

        // given
        final Board board = BoardFactory.create();

        // when
        final Position treuPosition = board.findPositionBy(Point.of(1, 0));
        final Position falsePosition = board.findPositionBy(Point.of(6, 0));

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(board.canMoveOnPath(treuPosition, Point.of(3, 3))).isTrue();
            assertThat(board.canMoveOnPath(falsePosition, Point.of(8, 3))).isFalse();
        });
    }

    @Test
    void 특정_위치에_말이_있다면_true_없다면_false_반환() {

        // given
        final Board board = BoardFactory.create();

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
        final Board board = BoardFactory.create();

        // when
        final Position position = board.findPositionBy(Point.of(2, 0));
        final Point point = Point.of(1, 2);

        // then
        assertThatThrownBy(() -> board.move(position, point, OutputView::printCaptureMessage)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 이동할_위치에_같은_팀_말이_없으면_정상_동작() {

        // given
        final Board board = BoardFactory.create();

        // when
        final Position position = board.findPositionBy(Point.of(2, 0));
        final Point point = Point.of(3, 2);

        // then
        assertThatCode(
                () -> board.move(position, point, OutputView::printCaptureMessage)).doesNotThrowAnyException();
    }

    @Test
    void 말이_올바른_이동을_한다() {
        // given
        final Board board = BoardFactory.create();

        // when
        final Position position = board.findPositionBy(Point.of(2, 0));
        final Point point = Point.of(3, 2);
        board.move(position, point, OutputView::printCaptureMessage);

        // then
        assertThat(board.hasPieceAt(point)).isTrue();
    }

    @Test
    void 말이_상대_말을_잡을_수_있다() {
        // given
        final Board board = BoardFactory.create();
        final Point expectedPoint = Point.of(1, 6);

        // when
        final Position position1 = board.findPositionBy(Point.of(2, 3));
        final Point toPoint1 = Point.of(1, 3);
        board.move(position1, toPoint1, OutputView::printCaptureMessage);

        final Position position2 = board.findPositionBy(Point.of(2, 6));
        final Point toPoint2 = Point.of(1, 6);
        board.move(position2, toPoint2, OutputView::printCaptureMessage);

        final Position position3 = board.findPositionBy(Point.of(1, 2));
        final Point toPoint3 = Point.of(1, 6);
        board.move(position3, toPoint3, OutputView::printCaptureMessage);

        final Position position = board.findPositionBy(expectedPoint);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(expectedPoint)).isTrue();
            softly.assertThat(position.isSamePieceType(PieceType.CANNON)).isTrue();
        });
    }

    @Test
    void 포가_포를_잡지_못한다() {
        // given
        final Board board = BoardFactory.create();
        final Point expectedPoint = Point.of(1, 7);

        // when
        final Position position2 = board.findPositionBy(Point.of(0, 3));
        final Point toPoint2 = Point.of(1, 3);
        board.move(position2, toPoint2, OutputView::printCaptureMessage);

        final Position position3 = board.findPositionBy(Point.of(1, 7));
        final Point toPoint3 = Point.of(1, 2);
        if (board.canMoveOnPath(position3, toPoint3)) {
            board.move(position3, toPoint3, OutputView::printCaptureMessage);
        }

        final Position position = board.findPositionBy(expectedPoint);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(expectedPoint)).isTrue();
            softly.assertThat(position.isSamePieceType(PieceType.CANNON)).isTrue();
        });

    }

    @Test
    void 포가_포를_넘지_못한다() {
        // given
        final Board board = BoardFactory.create();

        // when
        final Position position = board.findPositionBy(Point.of(1, 2));
        final Point toPoint = Point.of(1, 8);
        if (board.canMoveOnPath(position, toPoint)) {
            board.move(position, toPoint, OutputView::printCaptureMessage);
        }

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(position.isSamePieceType(PieceType.CANNON)).isTrue();
            softly.assertThat(board.hasPieceAt(toPoint)).isFalse();
        });
    }

    @Test
    void 포가_포를_제외한_말을_넘어서_잡는다() {
        // given
        final Board board = BoardFactory.create();

        // when
        final Position notCannonPosition = board.findPositionBy(Point.of(2, 3));
        final Point toPoint1 = Point.of(1, 3);
        board.move(notCannonPosition, toPoint1, OutputView::printCaptureMessage);

        final Position position2 = board.findPositionBy(Point.of(2, 6));
        final Point toPoint2 = Point.of(1, 6);
        board.move(position2, toPoint2, OutputView::printCaptureMessage);

        final Position cannonPosition = board.findPositionBy(Point.of(1, 2));
        final Point toPoint3 = Point.of(1, 6);
        if (board.canMoveOnPath(cannonPosition, toPoint3)) {
            board.move(cannonPosition, toPoint3, OutputView::printCaptureMessage);
        }

        // then
        final Position movedCannonPosition = board.findPositionBy(Point.of(1, 6));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cannonPosition.isSamePieceType(PieceType.CANNON)).isTrue();
            softly.assertThat(notCannonPosition.isSamePieceType(PieceType.CANNON)).isFalse();
            softly.assertThat(movedCannonPosition.isSamePieceType(PieceType.CANNON)).isTrue();
        });
    }

    @Test
    void 보드판에_궁이_1개가_아니다() {

        // given
        final Board board = BoardFactory.create();

        // when
        // then
        assertThat(board.hasOnlyOneGeneral()).isFalse();
    }

    @Test
    void 보드판에_궁이_1개다() {

        // given
        final Board board = new Board(
                List.of(new Position(Point.of(4, 1), PieceFactory.createGreenTeam(General::new, Score.GENERAL))));

        // when
        // then
        assertThat(board.hasOnlyOneGeneral()).isTrue();
    }

    @Test
    void 초나라가_우승팀이다() {

        // given
        final Board board = new Board(
                List.of(new Position(Point.of(4, 1), PieceFactory.createGreenTeam(General::new, Score.GENERAL))));

        // when
        final Team winTeam = board.determineWinTeam();

        // then
        assertThat(winTeam).isEqualTo(Team.GREEN);
    }

    @Test
    void 한나라가_우승팀이다() {

        // given
        final Board board = new Board(
                List.of(new Position(Point.of(4, 1), PieceFactory.createRedTeam(General::new, Score.GENERAL))));

        // when
        final Team winTeam = board.determineWinTeam();

        // then
        assertThat(winTeam).isEqualTo(Team.RED);
    }

}
