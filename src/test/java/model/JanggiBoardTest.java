package model;

import static model.janggiboard.JanggiBoardSetUp.INNER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;

import model.janggiboard.JanggiBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardTest {
    @Test
    @DisplayName("장기말 세팅 테스트 - 초기 장기말 갯수 확인")
   void  test1() {
        JanggiBoard janggiBoard = new JanggiBoard(INNER_ELEPHANT);

        assertThat(janggiBoard.countPiece()).isEqualTo(32);
    }
}
