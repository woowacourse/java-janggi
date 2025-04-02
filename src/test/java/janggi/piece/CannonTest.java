package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("포는 수평 혹은 수직으로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Cannon(camp);

        Position origin = Position.of(3, 3);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(Position.of(3, 5), new Soldier(camp));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("포는 수평 혹은 수직으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Cannon(camp);

        Position origin = Position.of(3, 3);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(Position.of(3, 4), new Soldier(camp));
        board.placePiece(Position.of(2, 3), new Soldier(camp));
        board.placePiece(Position.of(4, 3), new Soldier(camp));
        board.placePiece(Position.of(3, 2), new Soldier(camp));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("포는 다른 포를 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchOtherCannon() {
        // given
        Piece choPiece = new Cannon(Camp.CHO);
        Piece hanPiece = new Cannon(Camp.HAN);

        // when & then
        assertThatCode(() -> choPiece.validateCatch(hanPiece))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
    }

    @DisplayName("포의 경로에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverZeroPiece() {
        // given
        Piece piece = new Cannon(Camp.CHO);

        Position origin = Position.of(1, 1);
        Position target = Position.of(1, 3);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 0");
    }

    @DisplayName("포의 경로에 기물이 2개인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenJumpOverTwoPiece() {
        // given
        Piece piece = new Cannon(Camp.CHO);

        Position origin = Position.of(1, 1);
        Position target = Position.of(1, 5);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(Position.of(1, 2), new Soldier(Camp.CHO));
        board.placePiece(Position.of(1, 3), new Soldier(Camp.CHO));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: 2");
    }

    @DisplayName("포가 포를 넘어갈 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCannonJumpOverCannon() {
        // given
        Piece piece = new Cannon(Camp.CHO);

        Position origin = Position.of(1, 1);
        Position target = Position.of(1, 3);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(Position.of(1, 2), new Cannon(Camp.HAN));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("포는 포를 넘을 수 없습니다.");
    }
}
