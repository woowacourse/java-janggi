package janggiBoard.scoreTest;

import domain.Team;
import domain.board.JanggiBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class GameScoreTest {

    @Test
    void 현재_장기보드에_존재하는_초나라_기물의_점수합을_반환한다() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());

        double score = janggiBoard.calculateScore(Team.CHO);

        Assertions.assertThat(score).isEqualTo(72.0);
    }

    @Test
    void 현재_장기보드에_존재하는_한나라_기물의_점수합을_반환한다() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());

        double score = janggiBoard.calculateScore(Team.HAN);

        Assertions.assertThat(score).isEqualTo(73.5);
    }
}
