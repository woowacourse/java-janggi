package domain;

import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamScoresWinnerTest {

    @Test
    void 초점수가_더_크면_초가_승자다() {
        TeamScores scores = TeamScores.of(MaterialPoints.of(90), MaterialPoints.of(80));

        assertThat(scores.winner()).isEqualTo(Optional.of(TeamColor.CHO));
    }

    @Test
    void 한점수가_더_크면_한이_승자다() {
        TeamScores scores = TeamScores.of(MaterialPoints.of(80), MaterialPoints.of(90));

        assertThat(scores.winner()).isEqualTo(Optional.of(TeamColor.HAN));
    }

    @Test
    void 점수가_같으면_무승부로_승자가_없다() {
        TeamScores scores = TeamScores.of(MaterialPoints.of(90), MaterialPoints.of(90));

        assertThat(scores.winner()).isEqualTo(Optional.empty());
    }

    @Test
    void 소수점_오차가_있어도_0점5_단위로_비교한다() {
        TeamScores scores = TeamScores.of(MaterialPoints.of(73.5), MaterialPoints.of(73.5));

        assertThat(scores.winner()).isEqualTo(Optional.empty());
    }
}

