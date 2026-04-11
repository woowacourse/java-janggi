package domain.setup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Command 클래스 테스트")
class CommandTest {

    @Test
    @DisplayName("toArrangement: 유효한 값('1'-'4')으로 상차림을 입력받는다.")
    void toArrangementReturnsCorrectArrangement() {
        assertThat(new Command("1").toArrangement()).isEqualTo(Arrangement.MASANGMASANG);
        assertThat(new Command("2").toArrangement()).isEqualTo(Arrangement.MASANGSANGMA);
        assertThat(new Command("3").toArrangement()).isEqualTo(Arrangement.SANGMAMASANG);
        assertThat(new Command("4").toArrangement()).isEqualTo(Arrangement.SANGMASANGMA);
    }

    @ParameterizedTest(name = "toArrangement({0})은 유효한 값('1'-'4') 이외의 입력에 예외를 던진다")
    @ValueSource(strings = {"0", "5", "abc", "", " "})
    @DisplayName("toArrangement: 유효하지 않은 입력은 예외를 던진다")
    void toArrangementThrowsForInvalidInput(String input) {
        assertThatThrownBy(() -> new Command(input).toArrangement())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("toCoordinate: 유효한 좌표 형식을 Coordinate로 변환한다")
    void toCoordinateReturnsCoordinate() {
        Coordinate coordinate = new Command("a0 b1").toCoordinate();

        assertThat(coordinate).isNotNull();
        assertThat(coordinate.source()).isNotNull();
        assertThat(coordinate.source()).isNotNull();
    }

    @ParameterizedTest(name = "toCoordinate({0})은 유효한 값(ex. 'a0 b2') 이외의 입력에 예외를 던진다")
    @ValueSource(strings = {"a0", "a0b1", "a0 b1 c2", "", " ", "z0 a1"})
    @DisplayName("toCoordinate: 유효하지 않은 좌표 형식은 예외를 던진다")
    void toCoordinateThrowsForInvalidInput(String input) {
        assertThatThrownBy(() -> new Command(input).toCoordinate())
                .isInstanceOf(IllegalArgumentException.class);
    }
}
