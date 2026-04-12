package janggi.persistence.entity;

import janggi.domain.Camp;
import janggi.persistence.entity.vo.Status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameEntityTest {

    @ParameterizedTest
    @CsvSource(value = {
            "일",
            "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십"
    })
    void 게임_이름이_1글자_이상_50글자_이하면_정상_생성된다(String name) {
        GameEntity game = new GameEntity(
                "id",
                name,
                Status.PLAYING,
                Camp.CHO);

        assertThat(game).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십십"
    })
    void 게임_이름이_1글자_미만_50글자이하면_예외_처리한다(String name) {
        assertThatThrownBy(() -> new GameEntity(
                "id",
                name,
                Status.PLAYING,
                Camp.CHO));
    }
}