package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.InvalidMovementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class DiagonalPalaceMovementPieceTest {

    protected abstract DiagonalPalaceMovementPiece createPiece(Team team);

    @Nested
    class 궁성_외부에서의_이동인_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "1,5,1,1",
                "1,5,1,10",
                "1,5,9,5",
                "3,7,3,1"
        })
        public void 수직_수평_이동이면_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            DiagonalPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1,1,2,2",
                "3,5,5,7",
                "2,3,4,5"
        })
        public void 대각선_이동이면_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            DiagonalPalaceMovementPiece piece = createPiece(Team.HAN);
            assertThatThrownBy(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .isExactlyInstanceOf(InvalidMovementException.class);
        }
    }

    @Nested
    class 초_진영_궁성_내부에서의_이동인_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,5,9",
                "4,8,6,10",
                "5,9,4,8",
                "6,10,4,8",
                "4,8,4,9",
                "4,8,4,10",
                "5,10,5,9",
                "5,9,5,8",
                "4,8,6,8",
                "5,9,4,9",
                "5,9,6,9",
                "5,9,5,10"
        })
        public void 이동_가능한_경로이면_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            DiagonalPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class 한_진영_궁성_내부에서의_이동인_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "4,1,5,2",
                "4,1,6,3",
                "5,2,4,1",
                "6,3,4,1",
                "4,1,4,2",
                "4,1,4,3",
                "5,3,5,2",
                "5,2,5,1",
                "4,1,6,1",
                "5,2,4,2",
                "5,2,6,2",
                "5,2,5,3"
        })
        public void 이동_가능한_경로이면_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            DiagonalPalaceMovementPiece piece = createPiece(Team.HAN);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class 궁성_내부에서_외부로_이동하는_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,4,5",
                "5,9,5,4",
                "6,10,6,3",
                "4,8,1,8",
                "5,9,9,9",
                "6,10,9,10",
                "4,1,4,4",
                "5,2,5,5",
                "6,3,6,6",
                "4,1,1,1",
                "5,2,9,2",
                "6,3,9,3"
        })
        public void 수직_수평_이동이면_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            DiagonalPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }
    }
}
