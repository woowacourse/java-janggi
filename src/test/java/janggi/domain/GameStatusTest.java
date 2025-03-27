package janggi.domain;

import static janggi.domain.GameStatus.BLUE_WIN;
import static janggi.domain.GameStatus.OVER;
import static janggi.domain.GameStatus.PROGRESS;
import static janggi.domain.GameStatus.RED_WIN;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.General;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @DisplayName("한나라 왕이 죽었을 시 초나라의 승리다.")
    @Test
    void blueWinTest() {

        // given
        final Pieces pieces = new Pieces(List.of(
                new General(new Position(8, 8), BLUE)
        ));

        // when
        final GameStatus result = GameStatus.checkStatus(pieces);

        // then
        assertThat(result).isEqualTo(BLUE_WIN);
    }

    @DisplayName("초나라 왕이 죽었을 시 한나라의 승리다.")
    @Test
    void redWinTest() {

        // given
        final Pieces pieces = new Pieces(List.of(
                new General(new Position(8, 8), RED)
        ));

        // when
        final GameStatus result = GameStatus.checkStatus(pieces);

        // then
        assertThat(result).isEqualTo(RED_WIN);
    }

    @DisplayName("두 나라의 왕이 모두 죽지 않으며 두 왕만 남은 경우 게임은 종료된다.")
    @Test
    void overTest() {

        // given
        final Pieces pieces = new Pieces(List.of(
                new General(new Position(4, 4), BLUE),
                new General(new Position(1, 1), RED)
        ));

        // when
        final GameStatus result = GameStatus.checkStatus(pieces);

        // then
        assertThat(result).isEqualTo(OVER);
    }

    @DisplayName("두 나라의 왕이 모두 죽지 않으며 다른 기물도 남아있을 시 게임은 진행된다.")
    @Test
    void progressTest() {

        // given
        final Pieces pieces = new Pieces(List.of(
                new General(new Position(4, 4), BLUE),
                new General(new Position(1, 1), RED),
                new Soldier(new Position(2, 1), RED)
        ));

        // when
        final GameStatus result = GameStatus.checkStatus(pieces);

        // then
        assertThat(result).isEqualTo(PROGRESS);
    }
}
