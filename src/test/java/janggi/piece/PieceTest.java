package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.value.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("장기말을 움직일 수 있다.")
    @Test
    void canMove() {
        Piece piece = new Piece(PieceType.JOL, new Position(4, 4));
        Piece movedPiece = piece.move(new Position(5, 4), List.of(), List.of());

        assertThat(movedPiece.getPosition()).isEqualTo(new Position(5, 4));
    }

    @DisplayName("장기말을 움직일때 이동 불가능한 목적지가 주어졌을 경우 예외를 발생시킨다.")
    @Test
    void canNoMove() {
        Piece piece = new Piece(PieceType.JOL, new Position(4, 4));

        assertThatThrownBy(() -> piece.move(new Position(8, 4), List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("장기말의 타입을 체크할 수 있다.")
    @Test
    void canCheckType() {
        Piece piece = new Piece(PieceType.JOL, new Position(4, 4));

        assertAll(
                () -> assertThat(piece.checkPieceType(PieceType.JOL)).isTrue(),
                () -> assertThat(piece.checkPieceType(PieceType.BYUNG)).isFalse()
        );
    }
}