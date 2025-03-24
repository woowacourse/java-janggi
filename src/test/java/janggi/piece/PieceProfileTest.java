package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceProfileTest {

    @DisplayName("기물은 이름과 국가를 가진다.")
    @Test
    void profile() {
        //given
        final String name = "차";
        final Nation nation = Nation.CHO;

        //when //then
        assertThatCode(() -> new PieceProfile(name, nation))
                .doesNotThrowAnyException();
    }

}
