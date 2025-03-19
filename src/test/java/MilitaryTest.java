import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MilitaryTest {

    @Test
    @DisplayName("초나라 기물을 초기화한다.")
    void test1() {
        // given
        /**
         * 1: HEHE
         * 2: HEEH
         * 3: EHHE
         * 4: EHEH
         */
        String teamName = "한나라";
        int strategy = 1;

        // when
        Military military = Military.of(teamName, strategy);

        // then

    }
}
