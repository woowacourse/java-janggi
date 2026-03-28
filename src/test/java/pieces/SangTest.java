package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.MoveContext;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import position.Position;

class SangTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("상의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_우전방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_2칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_2칸이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우전방이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightForwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("상의 이동 경로를 검증한다")
    class PathPosition {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 출발지와_도착지_사이에는_2칸의_이동_경로만_존재한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(2);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_우전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.forwardDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.forwardDelta())
                    .move(side.rightForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.forwardDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.forwardDelta())
                    .move(side.leftForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.backDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.backDelta())
                    .move(side.rightBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.backDelta()))
            ;
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.backDelta())
                    .move(side.leftBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());

            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);

            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.leftDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.leftDelta())
                    .move(side.leftForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.leftDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.leftDelta())
                    .move(side.leftBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.rightDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.rightDelta())
                    .move(side.rightForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(
                departure.move(side.rightDelta())
            );
            assertThat(pathPosition.get(1)).isEqualTo(
                departure
                    .move(side.rightDelta())
                    .move(side.rightBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 도착지가_유효하지_않은_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 상은_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Sang(side);
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 상은_이동_경로에_기물이_없을_때_이동할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Sang(side);
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}