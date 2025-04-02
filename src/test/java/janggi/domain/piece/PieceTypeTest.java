package janggi.domain.piece;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTypeTest {

    @Test
    void 이름이_일치하는_기물_종류를_반환한다() {
        final String target = "상";
        final PieceType expected = PieceType.SANG;

        assertThat(PieceType.of(target))
                .isEqualTo(expected);
    }

    @Test
    void 팀_종류를_전달하여_기물을_생성한다() {
        final PieceType target = PieceType.SANG;
        final TeamType teamType = TeamType.HAN;

        assertThat(target.createPiece(teamType))
                .isInstanceOf(Sang.class);
    }
}
