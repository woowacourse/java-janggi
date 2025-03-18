import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @DisplayName("보드를 초기화하면 청궁이 95, 홍궁이 25에 배치된다.")
    @Test
    void initializeGeneral() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        assertThat(board.getPiece(new Position(9, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.BLUE));
        assertThat(board.getPiece(new Position(2, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.RED));
    }

    @DisplayName("보드를 초기화하면 청차가 01, 09, 홍차가 11, 19에 배치된다.")
    @Test
    void initializeChariot() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        assertThat(board.getPiece(new Position(0, 1))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.BLUE));
        assertThat(board.getPiece(new Position(0, 9))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.BLUE));
        assertThat(board.getPiece(new Position(1, 1))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.RED));
        assertThat(board.getPiece(new Position(1, 9))).isEqualTo(new Piece(PieceType.CHARIOT, TeamType.RED));
    }

    @DisplayName("보드를 초기화하면 청포가 82, 88, 홍포가 32, 38에 배치된다.")
    @Test
    void initializeCannon() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        assertThat(board.getPiece(new Position(8, 2))).isEqualTo(new Piece(PieceType.CANNON, TeamType.BLUE));
        assertThat(board.getPiece(new Position(8, 8))).isEqualTo(new Piece(PieceType.CANNON, TeamType.BLUE));
        assertThat(board.getPiece(new Position(3, 2))).isEqualTo(new Piece(PieceType.CANNON, TeamType.RED));
        assertThat(board.getPiece(new Position(3, 8))).isEqualTo(new Piece(PieceType.CANNON, TeamType.RED));
    }
}
