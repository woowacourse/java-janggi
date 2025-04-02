package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Chariot;
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

class ChariotTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("차는 상하좌우로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Chariot(camp);

        Position origin = Position.of(3, 3);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("차는 수평 혹은 수직으로만 움직여야 합니다.");
    }

    @DisplayName("차는 상하좌우 무제한으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Chariot(camp);

        Position origin = Position.of(3, 3);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 상하좌우로 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenBlocked() {
        // given
        Piece piece = new Chariot(Camp.CHO);

        Position origin = Position.of(3, 3);
        Position target = Position.of(3, 7);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);
        board.placePiece(Position.of(3, 5), new Soldier(Camp.CHO));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("차는 기물을 넘어 이동할 수 없습니다.");
    }
}
