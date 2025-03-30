package janggi.domain.gamestatus;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.General;
import janggi.domain.piece.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRunnedTest {

    @DisplayName("기물을 움직이고 다음 턴은 상대편이 된다.")
    @Test
    void move() {
        //given
        GameRunned gameRunned = new GameRunned(Dynasty.HAN, new JanggiBoard(Map.of(
                new Point(1, 5), new General(Dynasty.HAN),
                new Point(10, 4), new General(Dynasty.CHU)
        )));

        //when
        GameStatus gameStatus = gameRunned.move(new Point(1, 5), new Point(1, 4));

        //then
        assertThat(gameStatus).isEqualTo(new GameRunned(Dynasty.CHU, new JanggiBoard(Map.of(
                new Point(1, 4), new General(Dynasty.HAN),
                new Point(10, 4), new General(Dynasty.CHU)
        ))));
    }

    @DisplayName("기물을 움직이고 상대편 왕이 죽는다면 게임이 끝난 상태가 된다.")
    @Test
    void move_gameEnd() {
        //given
        GameRunned gameRunned = new GameRunned(Dynasty.CHU, new JanggiBoard(Map.of(
                new Point(1, 5), new General(Dynasty.HAN),
                new Point(10, 4), new General(Dynasty.CHU),
                new Point(1, 4), new Chariot(Dynasty.CHU)
        )));

        //when
        GameStatus gameStatus = gameRunned.move(new Point(1, 4), new Point(1, 5));

        //then
        assertThat(gameStatus).isEqualTo(new GameEnded(Dynasty.CHU, new JanggiBoard(Map.of(
                new Point(10, 4), new General(Dynasty.CHU),
                new Point(1, 5), new Chariot(Dynasty.CHU)
        ))));
    }
}