package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceStateTest {

    @DisplayName("기물의 포지션을 변경한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(7, 1);
        Piece piece = new Piece(Side.CHO, new Soldier());
        PieceState pieceState = new PieceState(position, piece);

        Position movePosition = Position.of(6, 1);

        // when
        pieceState.move(movePosition);

        // then
        assertThat(pieceState.getPosition()).isEqualTo(movePosition);
    }

    @DisplayName("기물이 움직일 수 없는 포지션이라면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(7, 1);
        Piece piece = new Piece(Side.CHO, new Soldier());
        PieceState pieceState = new PieceState(position, piece);
        Position movePosition = Position.of(5, 1);

        // when & then
        assertThatThrownBy(() -> pieceState.move(movePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
    }
}
