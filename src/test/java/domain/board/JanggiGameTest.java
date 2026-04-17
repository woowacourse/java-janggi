package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.Team;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {
    private JanggiGame janggiGame;
    private JanggiBoard board;

    @BeforeEach
    void setUp() {
        board = new JanggiBoard(new JanggiBoardInitializer());
        janggiGame = new JanggiGame(board);
    }

    @Test
    void 게임_시작_턴은_초이다() {
        assertThat(janggiGame.getTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void 빈_칸으로_이동하려고_하면_예외가_발생한다() {
        Position emptyPosition = new Position(4, 4);

        assertThatThrownBy(() -> janggiGame.playTurn(emptyPosition, new Position(5, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 위치에는 기물이 존재하지 않습니다.");
    }

    @Test
    void 상대방의_기물을_선택하여_이동하려고_하면_예외가_발생한다() {
        Position hanPiecePosition = new Position(0,0);

        assertThatThrownBy(() -> janggiGame.playTurn(hanPiecePosition, new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상대방의 기물을 이동할 수 없습니다.");
    }

    @Test
    void 정상적인_이동_후에는_턴이_교체되어야_한다() {
        Position from = new Position(6, 0);
        Position to = new Position(5, 0);

        janggiGame.playTurn(from, to);

        assertThat(janggiGame.getTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void 왕이_잡히지_않은_상태에서_게임_종료_여부는_false다() {
        assertThat(janggiGame.isGameOver()).isFalse();
    }
}
