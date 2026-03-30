package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.rule.BasicMoveRule;
import movepolicy.rule.MoveRule;
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
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_2칸_이동할_수_있다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_2칸이_도착지인_경우_예외를_던진다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우전방이_도착지인_경우_예외를_던진다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightForwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("상의 이동 경로를 검증한다")
    class InterveningPosition {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 출발지와_도착지_사이에는_2칸의_이동_경로만_존재한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions).hasSize(2);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_우전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.forwardDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.forwardDelta())
                    .move(side.rightForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.forwardDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.forwardDelta())
                    .move(side.leftForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.backDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.backDelta())
                    .move(side.rightBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.backDelta()))
            ;
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.backDelta())
                    .move(side.leftBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta())
                .move(side.leftForwardDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.leftDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.leftDelta())
                    .move(side.leftForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta())
                .move(side.leftBackDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.leftDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.leftDelta())
                    .move(side.leftBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.rightDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.rightDelta())
                    .move(side.rightForwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_2칸_이동의_경로를_반환한다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta())
                .move(side.rightBackDelta());
            // when
            List<Position> positions = piece.findPathPositions(departure, destination);
            // then
            assertThat(positions.get(0)).isEqualTo(
                departure.move(side.rightDelta())
            );
            assertThat(positions.get(1)).isEqualTo(
                departure
                    .move(side.rightDelta())
                    .move(side.rightBackDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 도착지가_유효하지_않은_경우_예외를_던진다(Side side) {
            // given
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 상은_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다(Side side) {
        // given
        Piece piece = new Sang(side);
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.rightForwardDelta())
            .move(side.rightForwardDelta());
        // when
        MoveRule moveRule = piece.getMoveRule();
        // then
        assertThat(moveRule).isInstanceOf(BasicMoveRule.class);
    }
}