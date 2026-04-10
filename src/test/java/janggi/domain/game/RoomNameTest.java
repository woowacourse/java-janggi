package janggi.domain.game;

import janggi.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class RoomNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"a", "abcdefghij"})
    @DisplayName("방 이름은 1자 이상 10자 이하여야 한다.")
    public void room_name_success(String roomName) {
        // when then
        assertThatCode(() -> new RoomName(roomName))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefghijk"})
    @DisplayName("방 이름은 1자 미만 10자 초과면 예외가 발생한다.")
    public void room_name_fail(String roomName) {
        // when then
        assertThatCode(() -> new RoomName(roomName))
                .isInstanceOf(DomainException.class);
    }

}
