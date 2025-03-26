package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.team.Team;
import janggi.team.TeamFactory;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @DisplayName("예외: 턴이 올바르지 않은 경우")
    @Test
    void validateTeamTurn() {
        Board board = new Board();
        Team teamCho = TeamFactory.createTeam(BoardSetup.of(List.of("초", "HEHE")));

        assertThatThrownBy(() -> board.validateTeamTurn(teamCho, teamCho))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 두 위치 사이의 경로에 있는 모든 좌표들을 조회")
    @Test
    void findPositionsOnPath() {
        Board board = new Board();

        assertThat(board.findPositionsOnPath(new Position(1, 1), new Position(3, 3)))
                .containsExactly(new Position(2, 2));
    }
}
