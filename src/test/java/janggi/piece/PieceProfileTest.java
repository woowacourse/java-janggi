package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceProfileTest {

    @DisplayName("기물은 타입과 국가를 가진다.")
    @Test
    void profile() {
        //given
        final PieceType pieceType = PieceType.CANNON;
        final Team team = Team.CHO;

        //when //then
        assertThatCode(() -> new PieceProfile(pieceType, team))
                .doesNotThrowAnyException();
    }

}
