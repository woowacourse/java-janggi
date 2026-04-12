package domain.setup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Arrangement 열거형 테스트")
class ArrangementTest {

    @Test
    @DisplayName("toArrangement: '1'은 MASANGMASANG을 반환한다")
    void toArrangementOneReturnsMaSangMaSang() {
        assertThat(Arrangement.toArrangement("1")).isEqualTo(Arrangement.MASANGMASANG);
    }

    @Test
    @DisplayName("toArrangement: '2'은 MASANGSANGMA 반환한다")
    void toArrangementOneReturnsMaSangSangMa() {
        assertThat(Arrangement.toArrangement("2")).isEqualTo(Arrangement.MASANGSANGMA);
    }

    @Test
    @DisplayName("toArrangement: '4'는 SANGMAMASANG를 반환한다")
    void toArrangementFourReturnsSangMaMaSang() {
        assertThat(Arrangement.toArrangement("3")).isEqualTo(Arrangement.SANGMAMASANG);
    }

    @Test
    @DisplayName("toArrangement: '4'는 SANGMASANGMA를 반환한다")
    void toArrangementFourReturnsSangMaSangMa() {
        assertThat(Arrangement.toArrangement("4")).isEqualTo(Arrangement.SANGMASANGMA);
    }
}
