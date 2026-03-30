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

class MaTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("마의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_우전방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_2칸이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("마의 이동 경로를 검증한다")
    class InterveningPosition {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 출발지와_도착지_사이에는_1칸의_이동_경로만_존재한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            assertThat(positions).hasSize(1);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_좌전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.leftForwardDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.forwardDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_우전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.forwardDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_좌후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.leftBackDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.backDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_우후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.rightBackDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.backDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌전방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftForwardDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.leftDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_좌후방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftBackDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.leftDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우전방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightForwardDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.rightDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_우후방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다(Side side) {
            // given
            FullPiece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightBackDelta());
            // when
            List<Position> positions = piece.getInterveningPositions(departure, destination);
            // then
            Position interveningPosition = positions.getFirst();
            assertThat(interveningPosition).isEqualTo(departure.move(side.rightDelta()));
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 도착지가_유효하지_않은_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Ma(side);
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
    void 마는_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Ma(side);
        // when
        MoveRule moveRule = piece.getMoveRule();
        // then
        assertThat(moveRule).isInstanceOf(BasicMoveRule.class);
    }
}