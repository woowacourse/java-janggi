package janggiGame;

import static org.assertj.core.api.Assertions.assertThat;

import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.piece.character.Dynasty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("총 점수를 반환 한다.")
    @Test
    void calculateSurvivedPieceTotalPoints_Test() {
        // given
        Board board = new Board(new InnerElephantStrategy(), new InnerElephantStrategy());

        // when // then
        assertThat(board.calculateTotalPoints(Dynasty.HAN)).isEqualTo(73.5);
        assertThat(board.calculateTotalPoints(Dynasty.CHO)).isEqualTo(72.0);
    }

    // TODO: 왕 죽음 체크 로직 테스트 추가
}