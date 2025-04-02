package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,6,8",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Horse(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,6",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,4,7",
            "HAN,3,6",
            "HAN,3,4",
            "HAN,4,3,",
    })
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Horse(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,3,4",
    })
    void shouldThrowException_WhenBlocked(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Horse(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(Position.of(5, 6), new Soldier(Camp.CHO));
        board.placePiece(Position.of(6, 5), new Soldier(Camp.CHO));
        board.placePiece(Position.of(4, 5), new Soldier(Camp.CHO));
        board.placePiece(Position.of(5, 4), new Soldier(Camp.CHO));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("마는 기물을 넘어서 이동할 수 없습니다.");
    }
}
