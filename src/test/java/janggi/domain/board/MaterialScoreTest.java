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
        assertThat(currentScore.han()).isEqualTo(55.5);
    }

    @Test
    void 점수_감소_메서드에서_음수가_들어오면_예외가_발생한다() {
        MaterialScore materialScore = new MaterialScore(72, 72);

        assertThatThrownBy(() -> materialScore.decreaseScore(Side.HAN, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 점수는 0보다 커야 합니다.");
    }

    @Test
    void 점수_차감_시_해당_진영의_점수만_정확히_감소한다() {
        MaterialScore materialScore = new MaterialScore(50, 50);

        materialScore.decreaseScore(Side.HAN, 10);

        SideScore currentScore = materialScore.getCurrentScore();
        assertThat(currentScore.han()).isEqualTo(41.5);
        assertThat(currentScore.cho()).isEqualTo(50.0);
    }

    @Test
    void 궁이_죽기_전까지는_isAnyGungDead가_거짓이다() {
        MaterialScore materialScore = new MaterialScore(72, 72);
        assertThat(materialScore.isAnyGungDead()).isFalse();

        materialScore.updateGungDead(Side.CHO);
        assertThat(materialScore.isAnyGungDead()).isTrue();
    }

    @Test
    void 가장_높은_점수의_진영을_정확하게_반환한다() {
        MaterialScore materialScore = new MaterialScore(1, 20);

        assertThat(materialScore.getHighestSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 기물_점수가_같으면_덤_때문에_한_진영이_높다() {
        MaterialScore materialScore = new MaterialScore(73, 73);

        assertThat(materialScore.getHighestSide()).isEqualTo(Side.HAN);
    }

    @Test
    void 존재하지_않는_진영의_점수를_차감하려_하면_예외가_발생한다() {
        MaterialScore materialScore = new MaterialScore(72, 72);

        assertThatThrownBy(() -> materialScore.decreaseScore(Side.EMPTY, 5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 진영의 점수 정보가 존재하지 않습니다");
    }


}
