package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTest {

    private static final int SINGLE_STEP_DISTANCE = 1;
    private static final int PASS_PIECE_COUNT = 1;
    private static final int HORSE_STRAIGHT_MOVE_DISTANCE = 1;
    private static final int HORSE_DIAGONAL_MOVE_DISTANCE = 1;
    private static final int ELEPHANT_STRAIGHT_MOVE_DISTANCE = 1;
    private static final int ELEPHANT_DIAGONAL_MOVE_DISTANCE = 2;

    @Test
    void 같은_전략_및_이동_조건이_적용되는_기물인지_확인한다() {
        // given
        Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
        // when
        boolean result = piece.isSamePieceRule(PieceRule.CANNON);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void 기물이_같은_진영인지_확인한다() {
        // given
        Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
        // when
        boolean result = piece.isSameCampType(CampType.CHO);
        // then
        assertThat(result).isTrue();
    }

    @DisplayName("궁 행마법 테스트")
    @Nested
    class General {

        @Test
        void 궁은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(1, 4);
            Piece piece = new Piece(PieceRule.GENERAL, CampType.CHO);
            Position destination = new Position(2, 4);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 궁은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Position source = new Position(1, 4);
            Piece piece = new Piece(PieceRule.GENERAL, CampType.CHO);
            Position destination = new Position(3, 4);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }

    @DisplayName("사 행마법 테스트")
    @Nested
    class Guard {

        @Test
        void 사는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(0, 3);
            Piece piece = new Piece(PieceRule.GUARD, CampType.CHO);
            Position destination = new Position(0, 4);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 사는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Position source = new Position(0, 3);
            Piece piece = new Piece(PieceRule.GUARD, CampType.CHO);
            Position destination = new Position(0, 5);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }

    @DisplayName("마 행마법 테스트")
    @Nested
    class Horse {

        @Test
        void 마는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(0, 1);
            Piece piece = new Piece(PieceRule.HORSE, CampType.CHO);
            Position destination = new Position(2, 2);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 마는_이동_경로에_기물이_있으면_예외가_발생한다() {
            // given
            Position source = new Position(0, 1);
            Piece piece = new Piece(PieceRule.HORSE, CampType.CHO);
            Position destination = new Position(2, 2);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(1, 1), new Piece(PieceRule.CHARIOT, CampType.HAN)
            ));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }

        @Test
        void 마는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Position source = new Position(0, 1);
            Piece piece = new Piece(PieceRule.HORSE, CampType.CHO);
            Position destination = new Position(0, 7);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(HORSE_STRAIGHT_MOVE_DISTANCE, HORSE_DIAGONAL_MOVE_DISTANCE));
        }
    }

    @DisplayName("상 행마법 테스트")
    @Nested
    class Elephant {

        @Test
        void 상은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(0, 6);
            Piece piece = new Piece(PieceRule.ELEPHANT, CampType.CHO);
            Position destination = new Position(3, 4);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 상은_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Position source = new Position(0, 6);
            Piece piece = new Piece(PieceRule.ELEPHANT, CampType.CHO);
            Position destination = new Position(3, 3);
            BoardChecker board = new Board(Map.of(source, piece));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(ELEPHANT_STRAIGHT_MOVE_DISTANCE, ELEPHANT_DIAGONAL_MOVE_DISTANCE));
        }
    }

    @DisplayName("포 행마법 테스트")
    @Nested
    class Cannon {

        @Test
        void 포는_이동_경로에_기물이_1개만_있으면_정상_이동한다() {
            // given
            Position source = new Position(2, 1);
            Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
            Position destination = new Position(8, 1);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(3, 1), new Piece(PieceRule.CHARIOT, CampType.HAN)
            ));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 포는_이동_경로에_기물이_없으면_예외가_발생한다() {
            //given
            Position source = new Position(2, 1);
            Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
            Position destination = new Position(8, 1);
            BoardChecker board = new Board(Map.of(source, piece));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(PASS_PIECE_COUNT));
        }

        @Test
        void 포는_이동_경로에_기물이_2개_이상_있으면_예외가_발생한다() {
            //given
            Position source = new Position(2, 1);
            Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
            Position destination = new Position(8, 1);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(3, 1), new Piece(PieceRule.CHARIOT, CampType.HAN),
                    new Position(5, 1), new Piece(PieceRule.CHARIOT, CampType.CHO)
            ));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(PASS_PIECE_COUNT));
        }

        @Test
        void 포는_넘으려는_기물이_같은_타입이면_예외가_발생한다() {
            //given
            Position source = new Position(2, 1);
            Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
            Position destination = new Position(8, 1);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(5, 1), new Piece(PieceRule.CANNON, CampType.CHO)
            ));
            //when & then
            assertThatThrownBy(() -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }

        @Test
        void 포는_목적지에_존재하는_기물이_같은_타입이면_예외가_발생한다() {
            //given
            Position source = new Position(2, 1);
            Piece piece = new Piece(PieceRule.CANNON, CampType.CHO);
            Position destination = new Position(8, 1);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(5, 1), new Piece(PieceRule.SOLDIER, CampType.CHO),
                    destination, new Piece(PieceRule.CANNON, CampType.HAN)
            ));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }

    @DisplayName("차 행마법 테스트")
    @Nested
    class Chariot {

        @Test
        void 차는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(0, 0);
            Piece piece = new Piece(PieceRule.CHARIOT, CampType.CHO);
            Position destination = new Position(9, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 차는_이동_경로에_기물이_1개_이상_있으면_예외가_발생한다() {
            //given
            Position source = new Position(0, 0);
            Piece piece = new Piece(PieceRule.CHARIOT, CampType.CHO);
            Position destination = new Position(9, 0);
            BoardChecker board = new Board(Map.of(
                    source, piece,
                    new Position(1, 0), new Piece(PieceRule.CHARIOT, CampType.HAN),
                    new Position(5, 1), new Piece(PieceRule.CHARIOT, CampType.CHO)

            ));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }

        @Test
        void 차는_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Position source = new Position(0, 0);
            Piece piece = new Piece(PieceRule.CHARIOT, CampType.CHO);
            Position destination = new Position(3, 3);
            BoardChecker board = new Board(Map.of(source, piece));
            //when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
        }
    }

    @DisplayName("졸 행마법 테스트")
    @Nested
    class SoldierCho {

        @Test
        void 졸은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(3, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.CHO);
            Position destination = new Position(4, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 졸은_후진할_시_예외가_발생한다() {
            // given
            Position source = new Position(3, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.CHO);
            Position destination = new Position(2, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }

        @Test
        void 졸은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Position source = new Position(3, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.CHO);
            Position destination = new Position(5, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }

    @DisplayName("병 행마법 테스트")
    @Nested
    class SoldierHan {

        @Test
        void 병은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Position source = new Position(6, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.HAN);
            Position destination = new Position(5, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(source, destination, board)
            );
        }

        @Test
        void 병은_후진할_시_예외가_발생한다() {
            // given
            Position source = new Position(6, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.HAN);
            Position destination = new Position(7, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }

        @Test
        void 병은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Position source = new Position(6, 0);
            Piece piece = new Piece(PieceRule.SOLDIER, CampType.HAN);
            Position destination = new Position(1, 0);
            BoardChecker board = new Board(Map.of(source, piece));
            // when & then
            assertThatThrownBy(
                    () -> piece.validateMove(source, destination, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }
}
