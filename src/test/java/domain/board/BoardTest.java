package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.createBoard(InitializeSetting.RIGHT_ELEPHANT_SETTING, InitializeSetting.RIGHT_ELEPHANT_SETTING);
    }

    @Nested
    class 기물_위치_테스트 {

        @ParameterizedTest
        @MethodSource("soldierProvider")
        void 졸을_올바른_위치에_초기화한다(Position position) {
            Optional<Piece> piece = board.getPiece(position);
            assertThat(piece.get().getPieceType()).isEqualTo(PieceType.SOLDIER);
        }

        static Stream<Arguments> soldierProvider() {
            return Stream.of(
                    Arguments.of(new Position(0, 3)),
                    Arguments.of(new Position(2, 3)),
                    Arguments.of(new Position(4, 3)),
                    Arguments.of(new Position(6, 3)),
                    Arguments.of(new Position(8, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("chariotProvider")
        void 차를_올바른_위치에_초기화한다(Position position) {
            Optional<Piece> piece = board.getPiece(position);
            assertThat(piece.get().getPieceType()).isEqualTo(PieceType.CHARIOT);
        }

        static Stream<Arguments> chariotProvider() {
            return Stream.of(
                    Arguments.of(new Position(0, 0)),
                    Arguments.of(new Position(8, 0))
            );
        }

        @ParameterizedTest
        @MethodSource("guardProvider")
        void 사를_올바른_위치에_초기화한다(Position position) {
            Optional<Piece> piece = board.getPiece(position);
            assertThat(piece.get().getPieceType()).isEqualTo(PieceType.GUARD);
        }

        static Stream<Arguments> guardProvider() {
            return Stream.of(
                    Arguments.of(new Position(3, 0)),
                    Arguments.of(new Position(5, 0))
            );
        }

        @Test
        void 궁을_올바른_위치에_초기화한다() {
            Optional<Piece> piece = board.getPiece(new Position(4, 1));
            assertThat(piece.get().getPieceType()).isEqualTo(PieceType.GENERAL);
        }

        @ParameterizedTest
        @MethodSource("cannonProvider")
        void 포를_올바른_위치에_초기화한다(Position position) {
            Optional<Piece> piece = board.getPiece(position);
            assertThat(piece.get().getPieceType()).isEqualTo(PieceType.CANNON);
        }

        static Stream<Arguments> cannonProvider() {
            return Stream.of(
                    Arguments.of(new Position(1, 2)),
                    Arguments.of(new Position(7, 2))
            );
        }
    }
}
