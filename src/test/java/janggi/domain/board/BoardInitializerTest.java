package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.ElephantPiece;
import janggi.domain.piece.GeneralPiece;
import janggi.domain.piece.HorsePiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardInitializerTest {

    @Test
    @DisplayName("장기판을 초기화하면 총 32개의 기물이 배치된다.")
    void testInitializeBoardPieceCount() {
        Board board = BoardInitializer.initializeBoard(1, 1);

        assertThat(board.getBoard()).hasSize(32);
    }

    @Test
    @DisplayName("장기판을 초기화하면 공통 기물은 고정된 위치에 배치된다.")
    void testInitializeBoardCommonPiecePosition() {
        Board board = BoardInitializer.initializeBoard(1, 1);

        assertThat(board.hasPieceAt(new Position(5, 2))).isTrue();
        assertThat(board.hasPieceAt(new Position(5, 9))).isTrue();
        assertThat(board.hasPieceAt(new Position(2, 3))).isTrue();
        assertThat(board.hasPieceAt(new Position(8, 3))).isTrue();
        assertThat(board.hasPieceAt(new Position(2, 8))).isTrue();
        assertThat(board.hasPieceAt(new Position(8, 8))).isTrue();

        assertThat(board.getBoard().get(new Position(5, 2))).isInstanceOf(GeneralPiece.class);
        assertThat(board.getBoard().get(new Position(5, 9))).isInstanceOf(GeneralPiece.class);
    }

    @ParameterizedTest
    @DisplayName("한 차림 선택에 따라 상과 마의 배치가 달라진다.")
    @CsvSource({
            "1, 2, 7, 3, 8",
            "2, 3, 8, 2, 7",
            "3, 3, 7, 2, 8",
            "4, 2, 8, 3, 7"
    })
    void testInitializeHanOpeningFormation(int choice, int leftElephantX, int rightElephantX,
                                           int leftHorseX, int rightHorseX) {
        Board board = BoardInitializer.initializeBoard(choice, 1);

        assertPiece(board, leftElephantX, 1, ElephantPiece.class, Team.HAN);
        assertPiece(board, rightElephantX, 1, ElephantPiece.class, Team.HAN);
        assertPiece(board, leftHorseX, 1, HorsePiece.class, Team.HAN);
        assertPiece(board, rightHorseX, 1, HorsePiece.class, Team.HAN);
    }

    @ParameterizedTest
    @DisplayName("초 차림 선택에 따라 상과 마의 배치가 달라진다.")
    @CsvSource({
            "1, 3, 8, 2, 7",
            "2, 2, 7, 3, 8",
            "3, 3, 7, 2, 8",
            "4, 2, 8, 3, 7"
    })
    void testInitializeChoOpeningFormation(int choice, int leftElephantX, int rightElephantX,
                                           int leftHorseX, int rightHorseX) {
        Board board = BoardInitializer.initializeBoard(1, choice);

        assertPiece(board, leftElephantX, 10, ElephantPiece.class, Team.CHO);
        assertPiece(board, rightElephantX, 10, ElephantPiece.class, Team.CHO);
        assertPiece(board, leftHorseX, 10, HorsePiece.class, Team.CHO);
        assertPiece(board, rightHorseX, 10, HorsePiece.class, Team.CHO);
    }

    private void assertPiece(Board board, int x, int y, Class<? extends Piece> pieceType, Team team) {
        Piece piece = board.getBoard().get(new Position(x, y));
        assertThat(piece).isInstanceOf(pieceType);
        assertThat(piece.getTeam()).isEqualTo(team);
    }
}
