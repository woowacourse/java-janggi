package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("기물에는 초나라 졸이 있다.")
    void 기물은_초나라_졸이_있음(){
        //given
        Team cho = Team.CHO;
        PieceType zol = PieceType.ZOL;
        Piece piece = new Piece(cho,zol);

        //when & then
        assertThat(piece.getTeamName()).isEqualTo("초나라");
        assertThat(piece.getPieceTypeName()).isEqualTo("졸");
    }
}
