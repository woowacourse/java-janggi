package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("기물에는 초나라 졸이 있다.")
    void 기물은_초나라_졸이_있음() {
        //given
        Team cho = Team.CHO;
        PieceType zol = PieceType.ZOL;
        Piece piece = new Piece(cho, zol);

        //when & then
        assertThat(piece.getTeamName()).isEqualTo("초나라");
        assertThat(piece.getPieceTypeName()).isEqualTo("졸");
    }

    @Test
    @DisplayName("기물이 차인지 확인할 수 있다")
    void 기물이_차인지_확인() {
        // given
        Piece cha = new Piece(Team.CHO, PieceType.CHA);
        Piece zol = new Piece(Team.HAN, PieceType.ZOL);

        // when
        boolean isCha = cha.isCha();
        boolean isCha2 = zol.isCha();

        //then
        assertThat(isCha).isTrue();
        assertThat(isCha2).isFalse();
    }

    @Test
    @DisplayName("기물이 포인지 확인할 수 있다")
    void 기물이_포인지_확인() {
        // given
        Piece po = new Piece(Team.CHO, PieceType.PO);
        Piece zol = new Piece(Team.HAN, PieceType.ZOL);

        // when
        boolean isPo = po.isPo();
        boolean isPo2 = zol.isPo();

        //then
        assertThat(isPo).isTrue();
        assertThat(isPo2).isFalse();
    }

    @Test
    @DisplayName("같은 팀인지 확인할 수 있다")
    void 같은_팀인지_확인() {
        // given
        Piece sameTeam1 = new Piece(Team.CHO, PieceType.CHA);
        Piece sameTeam2 = new Piece(Team.CHO, PieceType.MA);
        Piece differentTeam1 = new Piece(Team.CHO, PieceType.PO);
        Piece differentTeam2 = new Piece(Team.HAN, PieceType.KING);

        // when
        boolean same = sameTeam1.isSameTeam(sameTeam2);
        boolean different = differentTeam1.isSameTeam(differentTeam2);

        // then
        assertThat(same).isTrue();
        assertThat(different).isFalse();
    }
}
