package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PalaceMovementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public abstract class FullPalaceMovementPieceTest {

    protected abstract FullPalaceMovementPiece createPiece(Team team);

    @Nested
    class CHO_진영의_궁성인_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "5,9,4,8",
                "5,9,4,9",
                "5,9,4,10",
                "5,9,5,8",
                "5,9,5,10",
                "5,9,6,8",
                "5,9,6,9",
                "5,9,6,10",
                "4,8,4,9",
                "4,9,4,8",
                "4,8,5,8",
                "4,8,5,9",
                "4,10,5,9",
                "6,10,5,9"
        })
        public void 궁성_내_이동_가능한_위치일_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,6,8",
                "4,8,4,10",
                "4,8,6,10",
                "5,8,6,10",
                "4,9,5,10",
                "4,9,6,10",
                "4,10,6,8"
        })
        public void 궁성_내_이동_불가능한_위치일_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatThrownBy(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,9,5,7",
                "4,8,4,7",
                "5,8,5,7",
                "6,9,7,9"
        })
        public void 궁성_밖으로_이동할_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.CHO);
            assertThatThrownBy(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }
    }

    @Nested
    class HAN_진영의_궁성인_경우 {

        @ParameterizedTest
        @CsvSource(value = {
                "5,2,4,1",
                "5,2,4,2",
                "5,2,4,3",
                "5,2,5,1",
                "5,2,5,3",
                "5,2,6,1",
                "5,2,6,2",
                "5,2,6,3",
                "4,1,4,2",
                "4,2,4,1",
                "4,1,5,1",
                "4,1,5,2",
                "4,3,5,2",
                "6,3,5,2"
        })
        public void 궁성_내_이동_가능한_위치일_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.HAN);
            assertThatCode(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,1,6,1",
                "4,1,6,2",
                "4,1,6,3",
                "5,1,5,3",
                "6,3,4,1",
                "4,3,4,1",
                "4,2,6,2",
                "4,1,5,3"
        })
        public void 궁성_내_이동_불가능한_위치일_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.HAN);
            assertThatThrownBy(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,2,3,2",
                "4,3,4,4",
                "6,2,7,2"
        })
        public void 궁성_밖으로_이동할_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            FullPalaceMovementPiece piece = createPiece(Team.HAN);
            assertThatThrownBy(() -> piece.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow)))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }
    }
}
