package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Side;
import janggi.domain.SideScore;
import org.junit.jupiter.api.Test;

public class MaterialScoreTest {
    @Test
    void 초기_점수를_정확하게_반환한다() {
        MaterialScore materialScore = new MaterialScore(54, 72);
        SideScore currentScore = materialScore.getCurrentScore();

        assertThat(currentScore.cho()).isEqualTo(72);
        assertThat(currentScore.han()).isEqualTo(54);
    }

    @Test
    void 점수_감소_메서드에서_음수가_들어오면_예외가_발생한다() {
        MaterialScore materialScore = new MaterialScore(72, 72);

        assertThatThrownBy(() -> materialScore.decreaseScore(Side.HAN, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 점수는 0보다 커야 합니다.");
    }

    @Test
    void 궁이_죽기_전까지는_isAnyGungDead가_거짓이다() {
        MaterialScore materialScore = new MaterialScore(72, 72);
        assertThat(materialScore.isAnyGungDead()).isFalse();

        materialScore.updateGungDead(Side.CHO);
        assertThat(materialScore.isAnyGungDead()).isTrue();
    }
}
