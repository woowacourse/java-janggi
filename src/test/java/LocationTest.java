import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        Location location = new Location(4, 5);

        //when - then
        assertThat(location.getRow()).isEqualTo(4);
        assertThat(location.getCol()).isEqualTo(5);
    }

}
