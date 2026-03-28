package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.MoveContext;
import movepolicy.destination.PoDestinationRule;
import movepolicy.path.PoPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import position.Position;

class PoTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("포의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 앞으로_여러_칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 뒤로_여러_칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.backDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌로_여러_칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우로_여러_칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightDelta());
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 대각이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightForwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("포의 이동 경로를 검증한다")
    class PathPosition {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 출발지와_도착지_차이_만큼의_이동_경로가_존재한다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.backDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(1);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 앞_1칸_차이인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 뒤_1칸_차이인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.backDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_차이인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.leftDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_차이인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightDelta());
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 앞으로_여러_칸_차이인_경우의_이동_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(
                departure.move(side.forwardDelta())
            );
            assertThat(pathPositions.get(1)).isEqualTo(
                departure
                    .move(side.forwardDelta())
                    .move(side.forwardDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 뒤로_여러_칸_차이인_경우의_이동_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.backDelta())
                .move(side.backDelta())
                .move(side.backDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(
                departure.move(side.backDelta())
            );
            assertThat(pathPositions.get(1)).isEqualTo(
                departure
                    .move(side.backDelta())
                    .move(side.backDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌로_여러_칸_차이인_경우의_이동_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.leftDelta())
                .move(side.leftDelta())
                .move(side.leftDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(
                departure.move(side.leftDelta())
            );
            assertThat(pathPositions.get(1)).isEqualTo(
                departure
                    .move(side.leftDelta())
                    .move(side.leftDelta())
            );
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우로_여러_칸_차이인_경우의_이동_경로를_반환한다(Side side) {
            // given
            FullPiece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.rightDelta())
                .move(side.rightDelta())
                .move(side.rightDelta());
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(
                departure.move(side.rightDelta())
            );
            assertThat(pathPositions.get(1)).isEqualTo(
                departure
                    .move(side.rightDelta())
                    .move(side.rightDelta())
            );
        }
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 포는_포를_제외한_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Po(side);
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.forwardDelta());
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(PoDestinationRule.class);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 포는_이동_경로에_포를_제외한_기물이_1개_있을_때_이동할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Po(side);
        Position departure = DEFAULT;
        Position destination = departure
            .move(side.forwardDelta())
            .move(side.forwardDelta());
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(PoPathRule.class);
    }
}