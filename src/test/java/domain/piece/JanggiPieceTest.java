package domain.piece;

import static domain.constant.JanggiPieceConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.BlockedByFriendlyPieceException;
import janggiexception.CannotCaptureCannonException;
import janggiexception.CannotJumpCannonException;
import janggiexception.HurdleExistException;
import janggiexception.InvalidPathException;
import janggiexception.NotExistOnlyOneHurdleException;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiPieceTest {

    @Test
    void 기물을_잡으면_잡힌_기물의_상태가_바뀐다() {
        // given
        JanggiPiece piece = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.HORSE);

        // when
        piece.capture();

        // then
        assertThat(piece.isCaptured()).isTrue();
    }

    @Test
    void 포가_아닌_기물은_장애물을_넘을_수_없다() {
        // given
        JanggiPiece 마 = CHO_마;
        JanggiPiece hurdlePiece = EMPTY;
        int hurdleCount = 1;
        JanggiPiece targetPiece = HAN_병;

        // when & then
        assertThatThrownBy(() -> 마.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(HurdleExistException.class);
    }

    @Test
    void 같은_팀인_기물은_잡을_수_없다() {
        // given
        JanggiPiece 마 = CHO_마;
        JanggiPiece hurdlePiece = CHO_졸;
        int hurdleCount = 1;
        JanggiPiece targetPiece = CHO_졸;


        // when & then
        assertThatThrownBy(() -> 마.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                .isInstanceOf(BlockedByFriendlyPieceException.class);
    }

    @Test
    void 특정_기물의_팀을_확인할_수_있다() {
        // when & then
        assertThat(CHO_마.isTeamOf(JanggiSide.CHO)).isTrue();
    }

    @Nested
    class 궁Test {

        @ParameterizedTest
        @MethodSource("provide궁Route")
        void 궁의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPostion, List<MovingPattern> expected) {
            // given
            int beforeRow = 9;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> route = CHO_궁.getRoute(beforePosition, afterPostion);

            // when & then
            Assertions.assertThat(route)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide궁Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(8, 5), List.of(MovingPattern.MOVE_UP)),
                    Arguments.of(new JanggiPosition(9, 4), List.of(MovingPattern.MOVE_LEFT)),
                    Arguments.of(new JanggiPosition(9, 6), List.of(MovingPattern.MOVE_RIGHT)),
                    Arguments.of(new JanggiPosition(0, 5), List.of(MovingPattern.MOVE_DOWN)));
        }

        @Test
        void 궁이_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 9;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 8;
            int afterColumn = 4;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            assertThatThrownBy(
                    () -> CHO_궁.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_궁;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_궁;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }
    }

    @Nested
    class 마Test {

        @ParameterizedTest
        @MethodSource("provide마Path")
        void 마의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(6, 4);

            // when
            List<MovingPattern> route = CHO_마.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(route)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide마Path() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(5, 2), List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)),
                    Arguments.of(new JanggiPosition(5, 6), List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT)),
                    Arguments.of(new JanggiPosition(4, 5), List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT)),
                    Arguments.of(new JanggiPosition(4, 3), List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT)),
                    Arguments.of(new JanggiPosition(7, 2), List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT)),
                    Arguments.of(new JanggiPosition(8, 3), List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT)),
                    Arguments.of(new JanggiPosition(8, 5), List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)),
                    Arguments.of(new JanggiPosition(7, 6), List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT))
            );
        }

        @Test
        void 마가_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            JanggiPosition beforePosition = new JanggiPosition(6, 4);
            JanggiPosition afterPosition = new JanggiPosition(4, 4);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_마.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_마;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_마;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }

        @Test
        void 경로에_장애물이_있으면_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_마;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(HurdleExistException.class);
        }
    }

    @Nested
    class 병Test {

        @ParameterizedTest
        @MethodSource("provide병Route")
        void 병의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(7, 5);

            // when
            List<MovingPattern> 병path = HAN_병.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(병path)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide병Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(8, 5), List.of(MovingPattern.MOVE_DOWN)),
                    Arguments.of(new JanggiPosition(7, 4), List.of(MovingPattern.MOVE_LEFT)),
                    Arguments.of(new JanggiPosition(7, 6), List.of(MovingPattern.MOVE_RIGHT))
            );
        }

        @Test
        void 병이_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            JanggiPosition beforePosition = new JanggiPosition(7, 5);
            JanggiPosition afterPosition = new JanggiPosition(6, 5);

            // when & then
            Assertions.assertThatThrownBy(() -> HAN_병.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = HAN_병;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_포;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = HAN_병;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }
    }

    @Nested
    class 사Test {

        @ParameterizedTest
        @MethodSource("provide사Route")
        void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            int beforeRow = 9;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> route = CHO_사.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(route)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide사Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(8, 5), List.of(MovingPattern.MOVE_UP)),
                    Arguments.of(new JanggiPosition(9, 4), List.of(MovingPattern.MOVE_LEFT)),
                    Arguments.of(new JanggiPosition(9, 6), List.of(MovingPattern.MOVE_RIGHT)),
                    Arguments.of(new JanggiPosition(0, 5), List.of(MovingPattern.MOVE_DOWN)));
        }

        @Test
        void 사가_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 9;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 8;
            int afterColumn = 4;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_사.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_사;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_사;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }
    }

    @Nested
    class 상Test {

        @ParameterizedTest
        @MethodSource("provide상Route")
        void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            int beforeRow = 6;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> 상route = CHO_상.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(상route)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide상Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(4, 8), List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT)),
                    Arguments.of(new JanggiPosition(8, 8), List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)),
                    Arguments.of(new JanggiPosition(9, 7), List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)),
                    Arguments.of(new JanggiPosition(9, 3), List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT)),
                    Arguments.of(new JanggiPosition(8, 2), List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT)),
                    Arguments.of(new JanggiPosition(4, 2), List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)),
                    Arguments.of(new JanggiPosition(3, 3), List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)),
                    Arguments.of(new JanggiPosition(3, 7), List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT))
            );
        }

        @Test
        void 상이_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 6;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 4;
            int afterColumn = 6;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_상.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_상;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_상;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }

        @Test
        void 경로에_장애물이_있으면_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_상;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(HurdleExistException.class);
        }
    }

    @Nested
    class 졸Test {

        @ParameterizedTest
        @MethodSource("provide졸Route")
        void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            int beforeRow = 7;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> route = CHO_졸.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(route)
                    .isEqualTo(expected);
        }

        static Stream<Arguments> provide졸Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(6, 5), List.of(MovingPattern.MOVE_UP)),
                    Arguments.of(new JanggiPosition(7, 4), List.of(MovingPattern.MOVE_LEFT)),
                    Arguments.of(new JanggiPosition(7, 6), List.of(MovingPattern.MOVE_RIGHT))
            );
        }

        @Test
        void 졸이_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 7;
            int beforeColumn = 5;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 8;
            int afterColumn = 5;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_졸.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_졸;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_졸;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }
    }

    @Nested
    class 차Test {

        @ParameterizedTest
        @MethodSource("provide차Route")
        void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            int beforeRow = 0;
            int beforeColumn = 1;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> route = CHO_차.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(route).containsAll(expected);
        }

        static Stream<Arguments> provide차Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(5, 1),
                            List.of(
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP
                            )),
                    Arguments.of(new JanggiPosition(0, 9),
                            List.of(
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT
                            )));
        }

        @Test
        void 차가_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 0;
            int beforeColumn = 1;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 9;
            int afterColumn = 2;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_차.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_차;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 도착지에_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_차;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }

        @Test
        void 경로에_장애물이_있으면_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_차;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(HurdleExistException.class);
        }
    }

    @Nested
    class 포Test {

        @Test
        void 포는_포를_넘을_수_없다() {
            // given
            JanggiPiece 포 = CHO_포;
            JanggiPiece hurdlePiece = CHO_포;
            int hurdleCount = 1;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> 포.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(CannotJumpCannonException.class);
        }

        @Test
        void 포는_포를_잡을_수_없다() {
            // given
            JanggiPiece 포 = CHO_포;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = HAN_포;

            // when & then
            assertThatThrownBy(() -> 포.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(CannotCaptureCannonException.class);
        }

        @Test
        void 포는_장애물을_1개만_뛰어넘을_수_있다() {
            // given
            JanggiPiece 포 = CHO_포;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 2;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertThatThrownBy(() -> 포.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(NotExistOnlyOneHurdleException.class);
        }

        @Test
        void 포는_장애물을_1개가_있어야_움직일_수_있다() {
            // given
            JanggiPiece 포 = CHO_포;
            JanggiPiece hurdlePiece = EMPTY;
            int hurdleCount = 0;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertThatThrownBy(() -> 포.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(NotExistOnlyOneHurdleException.class);
        }

        @Test
        void 같은_팀인_기물은_잡을_수_없다() {
            // given
            JanggiPiece 포 = CHO_포;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> 포.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @ParameterizedTest
        @MethodSource("provide포Route")
        void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<MovingPattern> expected) {
            // given
            int beforeRow = 0;
            int beforeColumn = 1;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            // when
            List<MovingPattern> route = CHO_포.getRoute(beforePosition, afterPosition);

            // when & then
            Assertions.assertThat(route).containsAll(expected);
        }

        static Stream<Arguments> provide포Route() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(5, 1),
                            List.of(
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP,
                                    MovingPattern.MOVE_UP
                            )
                    ),
                    Arguments.of(new JanggiPosition(0, 9),
                            List.of(
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT,
                                    MovingPattern.MOVE_RIGHT
                            )
                    ));
        }

        @Test
        void 포가_이동할_수_없는_경로면_예외를_발생시킨다() {
            // given
            int beforeRow = 0;
            int beforeColumn = 1;
            JanggiPosition beforePosition = new JanggiPosition(beforeRow, beforeColumn);

            int afterRow = 9;
            int afterColumn = 2;
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when & then
            Assertions.assertThatThrownBy(() -> CHO_포.getRoute(beforePosition, afterPosition))
                    .isInstanceOf(InvalidPathException.class);
        }

        @Test
        void 포가_아닌_장애물이_1개_존재하지만_도착지에_같은_편의_말이_존재하는_경우_예외를_발생시킨다() {
            // given
            JanggiPiece piece = CHO_포;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = CHO_졸;

            // when & then
            assertThatThrownBy(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece))
                    .isInstanceOf(BlockedByFriendlyPieceException.class);
        }

        @Test
        void 포가_아닌_장애물이_1개_존재하고_도착지에_포가_아닌_상대_편의_말이_존재하는_경우_이동할_수_있다() {
            // given
            JanggiPiece piece = CHO_포;
            JanggiPiece hurdlePiece = CHO_졸;
            int hurdleCount = 1;
            JanggiPiece targetPiece = HAN_병;

            // when & then
            assertDoesNotThrow(() -> piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece));
        }
    }
}
