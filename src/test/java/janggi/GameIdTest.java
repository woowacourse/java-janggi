package janggi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameIdTest {

    @Test
    @DisplayName("GameId는 0보다 큰 값으로만 생성할 수 있다")
    void validValue() {
        // given
        // when
        final GameId gameId = GameId.from(123);

        // then
        assertThat(gameId.getValue()).isEqualTo(123);
        assertThat(gameId.isSet()).isTrue();
    }

    @Test
    @DisplayName("GameId는 0 이하일 경우 예외를 던진다")
    void invalidValue() {
        // given
        // when
        // then
        assertThatThrownBy(() -> GameId.from(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0보다 커야");
    }

    @Test
    @DisplayName("unset 상태는 isSet()이 false를 반환한다")
    void unset() {
        // given
        final GameId unset = GameId.unset();

        // when
        // then
        assertThat(unset.isSet()).isFalse();
    }

    @Test
    @DisplayName("unset 상태에서 getValue() 호출 시 예외 발생")
    void getValueWhenUnset() {
        // given
        final GameId unset = GameId.unset();

        // when
        // then
        assertThatThrownBy(unset::getValue)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("아이디가 설정되지 않았습니다");
    }

    @Test
    @DisplayName("setValue()는 새로운 GameId 인스턴스를 반환한다")
    void setValueReturnsNewInstance() {
        // given
        final GameId original = GameId.unset();

        // when
        final GameId updated = original.setValue(999);

        // then
        assertThat(updated.isSet()).isTrue();
        assertThat(updated.getValue()).isEqualTo(999);
        assertThat(original.isSet()).isFalse();
    }
}
