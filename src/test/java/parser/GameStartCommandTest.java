package parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class GameStartCommandTest {

    @Test
    void 시작_메뉴_입력이_1_2면_정상_동작한다() {
        assertThat(GameStartCommand.from("1")).isEqualTo(GameStartCommand.NEW_GAME);
        assertThat(GameStartCommand.from("2")).isEqualTo(GameStartCommand.LOAD_GAME);
    }

    @Test
    void 시작_메뉴_입력에_공백이_포함되어도_정상_동작한다() {
        assertThat(GameStartCommand.from("1 ")).isEqualTo(GameStartCommand.NEW_GAME);
        assertThat(GameStartCommand.from(" 2")).isEqualTo(GameStartCommand.LOAD_GAME);
    }

    @Test
    void 시작_메뉴_입력이_1_2가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> GameStartCommand.from("0"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> GameStartCommand.from("3"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
