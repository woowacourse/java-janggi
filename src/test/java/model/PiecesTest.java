package model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    private Pieces pieces = Pieces.createAndInit();

    @DisplayName("1, 4 에는 Red팀의 General이 존재해야 한다.")
    @Test
    void find_red_general() {
        Piece piece = pieces.findPiece(new Position(1, 4));
        assertThat(piece).isInstanceOf(General.class);
        assertThat(piece.getTeam()).isEqualTo(Team.RED);
    }

    @DisplayName("1, 4 에는 Red팀의 General이 존재해야 한다.")
    @Test
    void find_green_general() {
        Piece piece = pieces.findPiece(new Position(8, 4));
        assertThat(piece).isInstanceOf(General.class);
        assertThat(piece.getTeam()).isEqualTo(Team.GREEN);
    }

    @DisplayName("0,0 에는 Red팀의 Chariot가 존재해야 한다.")
    @Test
    void find_red_chariot() {
        Piece piece = pieces.findPiece(new Position(0, 0));
        assertThat(piece).isInstanceOf(Chariot.class);
        assertThat(piece.getTeam()).isEqualTo(Team.RED);
    }

    @DisplayName("9, 0 에는 Green팀의 Chariot가 존재해야 한다.")
    @Test
    void find_green_chariot() {
        Piece piece = pieces.findPiece(new Position(9, 0));
        assertThat(piece).isInstanceOf(Chariot.class);
        assertThat(piece.getTeam()).isEqualTo(Team.GREEN);
    }

    @DisplayName("포지션에 기물이 없을 경우, 예외를 발생시켜야 한다")
    @Test
    void when_position_not_exist_piece_then_throw_exception() {
        assertThatThrownBy(() -> pieces.findPiece(new Position(5, 5)));
    }
}
