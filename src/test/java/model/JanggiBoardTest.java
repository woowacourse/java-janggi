package model;

import static model.janggiboard.JanggiBoardSetUp.INNER_SANG;
import static org.assertj.core.api.Assertions.assertThat;

import dao.JanggiDao;
import model.janggiboard.JanggiBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardTest {
    @Test
    @DisplayName("장기말 세팅 테스트 - 초기 장기말 갯수 확인")
   void  test1() {
        JanggiDao janggiDao = new JanggiDao();
        JanggiBoard janggiBoard = new JanggiBoard(INNER_SANG, janggiDao);

        assertThat(janggiBoard.countPiece()).isEqualTo(32);
    }

    @Test
    @DisplayName("기물들의 점수 합산 반환 확인")
    void test2() {
        JanggiDao janggiDao = new JanggiDao();
        JanggiBoard janggiBoard = new JanggiBoard(INNER_SANG, janggiDao);

        assertThat(janggiBoard.getTeamScore(Team.BLUE)).isEqualTo(72);
        assertThat(janggiBoard.getTeamScore(Team.RED)).isEqualTo(73.5);
    }
}
