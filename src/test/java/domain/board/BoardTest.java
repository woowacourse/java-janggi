package domain.board;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.PieceType;

import domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Map;
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

    @Nested
    class 기물_점수_테스트 {
        @Test
        void 초의_점수가_올바르게_나오는지_확인한다() {
            double choScore = board.calculateScore(Team.CHO);
            assertThat(choScore).isEqualTo(72.0);
        }

        @Test
        void 한의_점수가_올바르게_나오는지_확인한다() {
            double hanScore = board.calculateScore(Team.CHO);
            assertThat(hanScore).isEqualTo(72.0);
        }

        @Test
        void 기물이_잡힌_경우를_반영하여_점수가_잘나오는지_확인한다() {
            board = new Board(Map.of(new Position(0, 0), new Chariot(Team.HAN), new Position(1, 0), new Cannon(Team.HAN)));

            double hanScore = board.calculateScore(Team.HAN);
            assertThat(hanScore).isEqualTo(21.5);
        }
    }

    @Test
    void 궁이_살아있는지_확인한다() {
        board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.HAN),
                new Position(1, 0), new Cannon(Team.HAN),
                new Position(4, 9), new General(Team.HAN)
        ));

        boolean aliveGeneral = board.isAliveGeneral(Team.HAN);
        assertThat(aliveGeneral).isTrue();
    }

    @Test
    void 궁이_살아있지_않은지_확인한다() {
        board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.HAN),
                new Position(1, 0), new Cannon(Team.HAN),
                new Position(4, 9), new General(Team.HAN)
        ));

        boolean aliveGeneral = board.isAliveGeneral(Team.CHO);
        assertThat(aliveGeneral).isFalse();
    }


}
