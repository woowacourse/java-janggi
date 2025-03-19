import static org.assertj.core.api.Assertions.assertThat;

import janggi.Board;
import janggi.Janggi;
import janggi.PieceType;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import janggi.piece.Piece;
import janggi.piece.Position;
import janggi.piece.TeamType;

class JanggiTest {

    @DisplayName("보드를 초기화하면 청궁이 95, 홍궁이 25에 배치된다.")
    @Test
    void initializeGeneral() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(9, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(2, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.RED))
        );
    }

    @DisplayName("보드를 초기화하면 청차가 01, 09, 홍차가 11, 19에 배치된다.")
    @Test
    void initializeChariot() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(10, 1))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(10, 9))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(1, 1))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(1, 9))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.RED))
        );
    }

    @DisplayName("보드를 초기화하면 청포가 82, 88, 홍포가 32, 38에 배치된다.")
    @Test
    void initializeCannon() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(8, 2))).isEqualTo(new Piece(PieceType.CANNON, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(8, 8))).isEqualTo(new Piece(PieceType.CANNON, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(3, 2))).isEqualTo(new Piece(PieceType.CANNON, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(3, 8))).isEqualTo(new Piece(PieceType.CANNON, TeamType.RED))
        );
    }

    @DisplayName("보드를 초기화하면 청사가 04, 06, 홍사가 14, 16에 배치된다.")
    @Test
    void initializeGuard() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(10, 4))).isEqualTo(new Piece(PieceType.GUARD, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(10, 6))).isEqualTo(new Piece(PieceType.GUARD, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(1, 4))).isEqualTo(new Piece(PieceType.GUARD, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(1, 6))).isEqualTo(new Piece(PieceType.GUARD, TeamType.RED))
        );
    }

    @DisplayName("보드를 초기화하면 청졸이 71, 73, 75, 77, 79, 홍졸이 41, 43, 45, 47, 49에 배치된다.")
    @Test
    void initializeSoldier() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(7, 1))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 3))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 5))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 7))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 9))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),

                () -> assertThat(board.getPiece(new Position(4, 1))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 3))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 5))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 7))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 9))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED))
        );
    }

    @DisplayName("보드를 초기화하면 청졸이 71, 73, 75, 77, 79, 홍졸이 41, 43, 45, 47, 49에 배치된다.")
    @Test
    void initializeHorse() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        Assertions.assertAll(
                () -> assertThat(board.getPiece(new Position(7, 1))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 3))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 5))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 7))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),
                () -> assertThat(board.getPiece(new Position(7, 9))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.BLUE)),

                () -> assertThat(board.getPiece(new Position(4, 1))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 3))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 5))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 7))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED)),
                () -> assertThat(board.getPiece(new Position(4, 9))).isEqualTo(new Piece(PieceType.SOLDIER, TeamType.RED))
        );
    }
}
