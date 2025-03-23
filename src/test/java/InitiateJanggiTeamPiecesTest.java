import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.InitiateJanggiTeamPieces;
import piece.Pieces;
import piece.Team;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InitiateJanggiTeamPiecesTest {

    @Test
    void 장기는_총_32피스가_있고_팀별로_16개씩_나눠가진다() {
        // when
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();

        // then
        Assertions.assertThat(piecesMap.get(Team.BLUE).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size() + piecesMap.get(Team.BLUE).size()).isEqualTo(32);
    }
}
