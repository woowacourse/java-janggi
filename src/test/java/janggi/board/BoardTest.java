package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.exception.ErrorException;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.position.Movement;
import janggi.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @DisplayName("특정 좌표에 기물을 둘 수 있다.")
    @Test
    void placePieceTest() {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.CHO, board);

        Position position = new Position(1, 1);

        // when & then
        assertThatCode(() -> board.placePiece(position, piece))
                .doesNotThrowAnyException();
    }

    @DisplayName("기물이 9x10 크기의 보드 내에서 이동하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "9,11",
            "9,0",
            "10,10",
            "0,10"
    })
    void shouldThrowException_WhenInvalidPosition(int x, int y) {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.CHO, board);

        Position position = new Position(x, y);

        // when & then
        assertThatCode(() -> board.placePiece(position, piece))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("기물은 9x10 크기의 보드 내에서만 이동 가능합니다.");
    }

    @DisplayName("기물을 정상적인 좌표로 움직인다.")
    @Test
    void moveTest() {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.CHO, board);

        Position origin = new Position(0, 3);
        Position target = new Position(0, 4);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);

        // when
        board.move(movement);

        // then
        assertThat(board.getCells())
                .containsEntry(target, piece);
    }

    @DisplayName("기물을 보드판의 영역을 넘어서 움직일 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidMove() {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.CHO, board);

        Position origin = new Position(0, 3);
        Position target = new Position(0, 15);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);

        // when & then
        assertThatCode(() -> board.move(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("기물은 9x10 크기의 보드 내에서만 이동 가능합니다.");
    }

    @DisplayName("이동시킬 기물을 찾을 수 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenNotFoundPiece() {
        // given
        Board board = new Board();

        Position origin = new Position(0, 3);
        Position target = new Position(0, 4);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThatCode(() -> board.move(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("같은 진영의 기물을 잡는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCampPiece() {
        // given
        Board board = new Board();
        Piece originPiece = new Soldier(Camp.CHO, board);
        Piece targetPiece = new Soldier(Camp.CHO, board);

        Position origin = new Position(0, 3);
        Position target = new Position(0, 4);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, originPiece);
        board.placePiece(target, targetPiece);

        // when & then
        assertThatCode(() -> board.move(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("같은 진영의 기물을 잡을 수 없습니다.");
    }

    @DisplayName("다른 진영의 기물을 잡을 수 있다.")
    @Test
    void moveCatchTest() {
        // given
        Board board = new Board();
        Piece originPiece = new Soldier(Camp.CHO, board);
        Piece targetPiece = new Soldier(Camp.HAN, board);

        Position origin = new Position(0, 3);
        Position target = new Position(0, 4);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, originPiece);
        board.placePiece(target, targetPiece);

        // when
        board.move(movement);

        // then
        assertAll(() -> {
            assertThat(board.getCells()).doesNotContainEntry(origin, originPiece);
            assertThat(board.getCells()).containsEntry(target, originPiece);
            assertThat(board.getCells()).doesNotContainEntry(target, targetPiece);
        });
    }

    @DisplayName("출발 위치와 도착 위치가 같은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMoveSamePosition() {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.CHO, board);

        Position origin = new Position(1, 1);
        Position target = new Position(1, 1);

        board.placePiece(origin, piece);

        // when & then
        assertThatCode(() -> board.move(new Movement(origin, target)))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("출발 위치와 도착 위치가 같은 경우 움직일 수 없습니다.");
    }

    @DisplayName("현재 턴에 해당하지 않는 진영의 기물을 선택하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenSelectOppositeCampPiece() {
        // given
        Board board = new Board();
        Piece piece = new Soldier(Camp.HAN, board);

        Position origin = new Position(1, 1);
        Position target = new Position(1, 2);
        Movement movement = new Movement(origin, target);

        board.placePiece(origin, piece);

        // when & then
        assertThatCode(() -> board.move(movement))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("현재 턴에 해당하지 않는 진영의 기물을 선택할 수 없습니다.");
    }
}
