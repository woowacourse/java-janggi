package janggi.team;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamScoreTest {

    @DisplayName("정상: 팀 점수 계산 확인 (초)")
    @Test
    void calculateTeamScoreCho() {
        TeamScore teamScore = new TeamScore();

        List<Piece> teamPieces = List.of(
                new Elephant(TeamName.CHO, new Position(1, 0)),
                new Horse(TeamName.CHO, new Position(2, 0)),
                new Elephant(TeamName.CHO, new Position(6, 0)),
                new Horse(TeamName.CHO, new Position(7, 0))
        );

        teamScore.calculateTeamScore(TeamName.CHO, teamPieces);

        assertThat(teamScore.getScore()).isEqualTo(16);
    }

    @DisplayName("정상: 팀 점수 계산 확인 (한)")
    @Test
    void calculateTeamScoreHan() {
        TeamScore teamScore = new TeamScore();

        List<Piece> teamPieces = List.of(
                new Elephant(TeamName.HAN, new Position(1, 9)),
                new Horse(TeamName.HAN, new Position(2, 9)),
                new Elephant(TeamName.HAN, new Position(6, 9)),
                new Horse(TeamName.HAN, new Position(7, 9))
        );

        teamScore.calculateTeamScore(TeamName.HAN, teamPieces);

        assertThat(teamScore.getScore()).isEqualTo(17.5);
    }
}
