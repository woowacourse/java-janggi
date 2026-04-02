package domain.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import common.exception.JanggiException;
import domain.piece.BasicPiece;
import domain.piece.Cha;
import domain.piece.Jol;
import domain.piece.Po;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PlayerTest {

    @Test
    void 플레이어가_정상적으로_생성된다() {
        String nameValue = "초나라고수";
        Player player = new Player(new Name(nameValue), Team.CHO);

        assertEquals(nameValue, player.getProfile().nameValue());
        assertEquals(Team.CHO, player.getProfile().team());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void 이름이_빈값이거나_공백이면_예외가_발생한다(String name) {
        assertThrows(JanggiException.class, () -> new Player(new Name(name), Team.CHO));
    }

    @Test
    void 기물과_플레이어의_팀이_다름을_확인한다() {
        Player player = new Player(new Name("p1"), Team.CHO);
        BasicPiece hanPiece = new Cha(Team.HAN);
        BasicPiece choPiece = new Cha(Team.CHO);

        assertTrue(player.isDifferentTeam(hanPiece));
        assertFalse(player.isDifferentTeam(choPiece));
    }

    @Test
    void 플레이어의_이름이_일치하는지_확인한다() {
        String nameValue = "장기천재";
        Player player = new Player(new Name(nameValue), Team.CHO);

        assertTrue(player.hasName(nameValue));
        assertFalse(player.hasName("다른이름"));
    }

    @Test
    void 플레이어_점수를_계산한다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        choPlayer.addPiece(new Cha(Team.CHO));
        choPlayer.addPiece(new Po(Team.CHO));
        choPlayer.addPiece(new Jol(Team.CHO));

        assertEquals(22.0, choPlayer.calculateScore());
    }

    @Test
    void 한팀_점수는_덤_1_5를_포함한다() {
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        hanPlayer.addPiece(new Cha(Team.HAN));

        assertEquals(14.5, hanPlayer.calculateScore());
    }

    @Test
    void 플레이어와_다른팀_기물은_추가할_수_없다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);

        assertThrows(JanggiException.class, () -> choPlayer.addPiece(new Cha(Team.HAN)));
    }
}
