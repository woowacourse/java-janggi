package janggiBoard.gameTest;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import game.JanggiGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JanggiGameTest {

    private JanggiGame janggiGame;

    @BeforeEach
    public void setUp() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());
        this.janggiGame = new JanggiGame(janggiBoard);
    }

    @Test
    void 게임의_처음_턴이_초인지_확인한다() {
        assertThat(janggiGame.getCurrentTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 기물이_성공적으로_이동하면_턴이_변경된다() {
        assertThat(janggiGame.getCurrentTeam()).isEqualTo(Team.CHO);

        janggiGame.progress(new Position(6, 0), new Position(5, 0));

        assertThat(janggiGame.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 다른팀의_기물을_옮기려고_시도하면_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class, () -> {
                    janggiGame.progress(new Position(0, 0), new Position(1, 0));
                }
        );

        assertThat(janggiGame.getCurrentTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 궁이_아닌_기물을_잡으면_게임이_유지되고_다음턴으로_넘어간다() {
        janggiGame.progress(new Position(6, 4), new Position(5, 4));
        janggiGame.progress(new Position(0, 0), new Position(1, 0));

        janggiGame.progress(new Position(5, 4), new Position(4, 4));
        janggiGame.progress(new Position(1, 0), new Position(0, 0));

        janggiGame.progress(new Position(4, 4), new Position(3, 4));
        assertThat(janggiGame.isFinished()).isFalse();
        assertThat(janggiGame.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 상대방의_궁이_잡히면_게임이_종료된다() {
        janggiGame.progress(new Position(6, 4), new Position(5, 4));
        janggiGame.progress(new Position(0, 0), new Position(1, 0));
        janggiGame.progress(new Position(5, 4), new Position(4, 4));
        janggiGame.progress(new Position(1, 0), new Position(0, 0));
        janggiGame.progress(new Position(4, 4), new Position(3, 4));
        janggiGame.progress(new Position(0, 8), new Position(1, 8));

        // 한나라 궁성 들어옴
        janggiGame.progress(new Position(3, 4), new Position(2, 4));
        janggiGame.progress(new Position(1, 8), new Position(0, 8));

        // 한나라 궁 잡음
        janggiGame.progress(new Position(2, 4), new Position(1, 4));

        assertThat(janggiGame.isFinished()).isTrue();
    }
}
