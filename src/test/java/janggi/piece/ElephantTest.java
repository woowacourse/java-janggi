package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.BoardFactory;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.emptyBoard(Camp.CHO);
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,8,6",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Elephant(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,7,8",
            "HAN,8,7",
            "HAN,7,2",
            "HAN,2,7",
            "HAN,8,3",
            "HAN,3,8",
            "HAN,3,2",
            "HAN,2,3,",
    })
    void validateMoveTest(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Elephant(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 때 직선 이동중 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,7,8",
            "HAN,7,2",
            "HAN,8,3",
            "HAN,3,2",
    })
    void shouldThrowException_WhenLinearBlocked(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Elephant(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(Position.of(5, 6), new Soldier(Camp.CHO));
        board.placePiece(Position.of(6, 5), new Soldier(Camp.CHO));
        board.placePiece(Position.of(5, 4), new Soldier(Camp.CHO));
        board.placePiece(Position.of(4, 5), new Soldier(Camp.CHO));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("상은 기물을 넘어서 이동할 수 없습니다.");
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 때 대각선으로 이동중 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,7,8",
            "HAN,7,2",
            "HAN,8,3",
            "HAN,3,2",
    })
    void shouldThrowException_WhenDiagonalBlocked(Camp camp, int targetX, int targetY) {
        // given
        Piece piece = new Elephant(camp);

        Position origin = Position.of(5, 5);
        Position target = Position.of(targetX, targetY);
        Movement movement = new Movement(origin, target);

        board.placePiece(Position.of(6, 7), new Soldier(Camp.CHO));
        board.placePiece(Position.of(7, 6), new Soldier(Camp.CHO));
        board.placePiece(Position.of(6, 3), new Soldier(Camp.CHO));
        board.placePiece(Position.of(3, 6), new Soldier(Camp.CHO));
        board.placePiece(Position.of(4, 3), new Soldier(Camp.CHO));
        board.placePiece(Position.of(3, 4), new Soldier(Camp.CHO));
        board.placePiece(Position.of(7, 4), new Soldier(Camp.CHO));
        board.placePiece(Position.of(4, 7), new Soldier(Camp.CHO));

        // when & then
        assertThatCode(() -> piece.validateMove(movement, board))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("상은 기물을 넘어서 이동할 수 없습니다.");
    }
}
