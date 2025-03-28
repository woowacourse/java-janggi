package janggi;

import janggi.board.Board;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Jol;
import janggi.position.Position;
import janggi.team.TeamType;
import janggi.team.Turn;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameTest {

    @Test
    void 현재_진행_중인_팀과_점수를_계산하여_반환한다() {
        // Given
        final Jol jol = new Jol();
        final Cha cha = new Cha(jol.getTeamType());
        final Byeong byeong = new Byeong();
        final Position jolPosition = new Position(10, 1);
        final Position chaPosition = new Position(9, 1);
        final Position byeongPosition = new Position(1, 1);

        final Board board = new Board(Map.of(
                jolPosition, jol,
                chaPosition, cha,
                byeongPosition, byeong
        ));

        final JanggiGame janggiGame = new JanggiGame(board, new Turn());

        final Map<TeamType, Double> expected = new HashMap<>();
        expected.put(jol.getTeamType(), (double) (jol.getScore() + cha.getScore()));
        expected.put(byeong.getTeamType(), byeong.getScore() + 1.5);

        // When & Then
        assertThat(janggiGame.getScoreEachTeam())
                .isEqualTo(expected);
    }
}
