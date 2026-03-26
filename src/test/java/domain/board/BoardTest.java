package domain.board;


import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardInitializer.init(1, 2));
    }

    @Test
    @DisplayName("좌표에 기물이 있다면 기물을 반환한다.")
    void shouldReturnPiece_When_Piece_AtThePosition() {
        Piece elephantPiece = board.findBy(new Position(2, 1));
        Piece horsePiece = board.findBy(new Position(3, 1));
        Piece soldierPiece = board.findBy(new Position(1, 4));
        Piece guardPiece = board.findBy(new Position(4, 1));
        Piece cannonPiece = board.findBy(new Position(2, 3));
        Piece generalPiece = board.findBy(new Position(5, 2));
        Piece chariotPiece = board.findBy(new Position(1, 1));

        assertThat(elephantPiece.type()).isEqualTo(PieceType.ELEPHANT);
        assertThat(horsePiece.type()).isEqualTo(PieceType.HORSE);
        assertThat(soldierPiece.type()).isEqualTo(PieceType.SOLDIER);
        assertThat(guardPiece.type()).isEqualTo(PieceType.GUARD);
        assertThat(cannonPiece.type()).isEqualTo(PieceType.CANNON);
        assertThat(generalPiece.type()).isEqualTo(PieceType.GENERAL);
        assertThat(chariotPiece.type()).isEqualTo(PieceType.CHARIOT);
    }

    @Test
    @DisplayName("좌표에 기물이 존재하지 않는 경우 예외를 발생한다.")
    void throwException_When_PieceNotExist_AtThePosition() {
        assertThatThrownBy(() -> board.findBy(new Position(4, 4)))
                .isInstanceOf(NoSuchElementException.class);
    }
}
