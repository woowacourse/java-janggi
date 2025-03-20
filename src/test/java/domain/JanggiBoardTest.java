package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiBoard.JanggiBoard;
import domain.JanggiBoard.JanggiBoardBasicInitializer;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import domain.piece.궁;
import domain.piece.마;
import domain.piece.사;
import domain.piece.상;
import domain.piece.졸병;
import domain.piece.차;
import domain.piece.포;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JanggiBoardInitializerStub;

public class JanggiBoardTest {

    @Test
    void 마상상마_장기판을_생성할_수_있다() {
        // when & then
        assertDoesNotThrow(() -> new JanggiBoard(new JanggiBoardBasicInitializer()));
    }

    @Test
    void 장기_기물의_초기_위치를_저장한다() {
        // given
        JanggiPosition position = new JanggiPosition(9, 5);
        JanggiPiece piece = new 궁(JanggiSide.CHO);

        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(position, piece));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);

        // when & then
        assertThat(janggiBoard.getPieceFrom(position)).isInstanceOf(piece.getClass());
    }

    @Test
    void 기물을_이동하며_마주치는_장애물을_확인할_수_있다() {
        // given
        JanggiPosition 졸병Position = new JanggiPosition(5, 1);
        JanggiPosition 차Position = new JanggiPosition(6, 1);

        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                졸병Position, new 졸병(JanggiSide.CHO),
                차Position, new 차(JanggiSide.CHO)
        ));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);

        // when & then
        assertThatThrownBy(() -> janggiBoard.movePiece(차Position, new JanggiPosition(4, 1)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Nested
    class 기물을_이동시킬_수_있다 {

        @ParameterizedTest
        @CsvSource({"8, 5", "9, 4", "0, 5", "9, 6"})
        void 궁을_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(9, 5);
            JanggiPiece piece = new 궁(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(궁.class);
        }

        @ParameterizedTest
        @CsvSource({"4, 5", "5, 6", "7, 6", "8, 5", "8, 3", "7, 2", "5, 2", "4, 3"})
        void 마를_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(6, 4);
            JanggiPiece piece = new 마(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(마.class);
        }

        @ParameterizedTest
        @CsvSource({"8, 5", "9, 4", "0, 5", "9, 6"})
        void 사를_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(9, 5);
            JanggiPiece piece = new 사(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(사.class);
        }

        @ParameterizedTest
        @CsvSource({"5, 8", "9, 8", "0, 7", "0, 3", "9, 2", "5, 2", "4, 3", "4, 7"})
        void 상을_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(7, 5);
            JanggiPiece piece = new 상(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(상.class);
        }

        @ParameterizedTest
        @CsvSource({"8, 5", "9, 4", "9, 6"})
        void 졸을_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(9, 5);
            JanggiPiece piece = new 졸병(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(졸병.class);
        }

        @ParameterizedTest
        @CsvSource({"9, 4", "0, 5", "9, 6"})
        void 병을_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(9, 5);
            JanggiPiece piece = new 졸병(JanggiSide.HAN);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(졸병.class);
        }

        @ParameterizedTest
        @CsvSource({"6, 2", "6, 1", "6, 4", "6, 5", "6, 6", "6, 7", "6, 8", "6, 9",
                "5, 3", "4, 3", "3, 3", "2, 3", "1, 3", "7, 3", "8, 3", "9, 3", "0, 3",})
        void 차를_이동시킬_수_있다(int afterRow, int afterColumn) {
            // given
            JanggiPosition beforePosition = new JanggiPosition(6, 3);
            JanggiPiece piece = new 차(JanggiSide.HAN);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    beforePosition, piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition afterPosition = new JanggiPosition(afterRow, afterColumn);

            // when
            janggiBoard.movePiece(beforePosition, afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(afterPosition)).isInstanceOf(차.class);
        }

        @Test
        void 포는_기물을_하나_뛰어넘어서_이동할_수_있다() {
            // given
            JanggiPosition 포beforePosition = new JanggiPosition(6, 2);
            JanggiPosition 졸beforePosition = new JanggiPosition(6, 3);
            JanggiPiece 포piece = new 포(JanggiSide.HAN);
            JanggiPiece 졸piece = new 졸병(JanggiSide.CHO);
            JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                    포beforePosition, 포piece,
                    졸beforePosition, 졸piece
            ));
            JanggiBoard janggiBoard = new JanggiBoard(initializer);
            JanggiPosition 포afterPosition = new JanggiPosition(6, 4);

            // when
            janggiBoard.movePiece(포beforePosition, 포afterPosition);

            // then
            assertThat(janggiBoard.getPieceFrom(포afterPosition)).isInstanceOf(포.class);
        }
    }

    @Test
    void 목적지에_같은_팀의_기물이_있는_경우_이동할_수_없다() {
        // given
        JanggiPosition 마beforePosition = new JanggiPosition(5, 4);
        JanggiPosition 졸Position = new JanggiPosition(3, 5);
        JanggiPiece 마piece = new 마(JanggiSide.HAN);
        JanggiPiece 졸piece = new 졸병(JanggiSide.HAN);
        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                마beforePosition, 마piece,
                졸Position, 졸piece
        ));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);

        // when & then
        assertThatThrownBy(() -> janggiBoard.movePiece(마beforePosition, 졸Position))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // given
        JanggiPosition first포beforePosition = new JanggiPosition(6, 2);
        JanggiPosition second포Position = new JanggiPosition(6, 3);
        JanggiPiece first포piece = new 포(JanggiSide.HAN);
        JanggiPiece second포piece = new 포(JanggiSide.CHO);
        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                first포beforePosition, first포piece,
                second포Position, second포piece
        ));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);
        JanggiPosition first포afterPosition = new JanggiPosition(6, 4);

        // when & then
        assertThatThrownBy(() -> janggiBoard.movePiece(first포beforePosition, first포afterPosition));
    }

    @Test
    void 포는_기물을_두_개_이상_뛰어넘을_수_없다() {
        // given
        JanggiPosition 포beforePosition = new JanggiPosition(6, 2);
        JanggiPosition 졸Position = new JanggiPosition(6, 3);
        JanggiPosition 마Position = new JanggiPosition(6, 4);
        JanggiPiece 포piece = new 포(JanggiSide.HAN);
        JanggiPiece 졸piece = new 졸병(JanggiSide.CHO);
        JanggiPiece 마piece = new 마(JanggiSide.CHO);
        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                포beforePosition, 포piece,
                졸Position, 졸piece,
                마Position, 마piece
        ));

        JanggiBoard janggiBoard = new JanggiBoard(initializer);
        JanggiPosition 포afterPosition = new JanggiPosition(6, 5);

        // then
        assertThatThrownBy(() -> janggiBoard.movePiece(포beforePosition, 포afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // given
        JanggiPosition first포beforePosition = new JanggiPosition(6, 2);
        JanggiPosition 졸Position = new JanggiPosition(6, 3);
        JanggiPosition second포Position = new JanggiPosition(6, 4);
        JanggiPiece first포piece = new 포(JanggiSide.HAN);
        JanggiPiece 졸piece = new 졸병(JanggiSide.HAN);
        JanggiPiece second포piece = new 포(JanggiSide.CHO);
        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                first포beforePosition, first포piece,
                졸Position, 졸piece,
                second포Position, second포piece
        ));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);

        // when & then
        assertThatThrownBy(() -> janggiBoard.movePiece(first포beforePosition, second포Position));
    }

    @Test
    void 기물은_다른_기물을_잡아서_잡힌_기물의_상태를_바꿀_수_있다() {
        // given
        JanggiPosition 마beforePosition = new JanggiPosition(8, 2);
        JanggiPosition 졸Position = new JanggiPosition(6, 3);
        JanggiPiece 마piece = new 마(JanggiSide.HAN);
        JanggiPiece 졸piece = new 졸병(JanggiSide.CHO);
        JanggiBoardInitializerStub initializer = new JanggiBoardInitializerStub(Map.of(
                마beforePosition, 마piece,
                졸Position, 졸piece
        ));
        JanggiBoard janggiBoard = new JanggiBoard(initializer);

        // when
        janggiBoard.movePiece(마beforePosition, 졸Position);

        // then
        assertThat(졸piece.isCaptured()).isTrue();
    }
}
