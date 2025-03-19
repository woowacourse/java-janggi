package model;

import static model.JanggiBoardSetUp.INNER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {
    @Test
    @DisplayName("장기말 세팅 테스트 - 초기 장기말 갯수 확인")
   void  test1() {
        JanggiBoard janggiBoard = new JanggiBoard(INNER_ELEPHANT);
        
        assertThat(janggiBoard.countPiece()).isEqualTo(32);
    }
}
