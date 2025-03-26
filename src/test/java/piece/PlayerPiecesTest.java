package piece;

import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.player.PlayerPieces;
import piece.player.Team;
import piece.position.JanggiPosition;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlayerPiecesTest {

    @Test
    void 장기판의_모든_피스들을_가지고올_수_있다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(0, 2), new FoMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        Pieces allPieces = playerPieces.allPieces();
        Assertions.assertThat(allPieces.getPieces())
                .containsExactlyInAnyOrderElementsOf(bluePieces.combine(redPieces).getPieces());
    }

    @Test
    void 기물을_움직일_수_있다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(0, 3), new FoMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playerPieces.placePhase(Team.BLUE, new JanggiPosition(0, 1), new JanggiPosition(1, 1));
        Pieces allPieces = playerPieces.allPieces();

        var expectedBluePiece = new Piece(new JanggiPosition(1, 1), new ChaMoveBehavior(), Team.BLUE);
        Assertions.assertThat(allPieces.getPieces())
                .contains(expectedBluePiece);
    }

    @Test
    void 기물을_움직이고_중복된_기물은_제거된다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(1, 1), new FoMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playerPieces.placePhase(Team.BLUE, new JanggiPosition(0, 1), new JanggiPosition(1, 1));

        Assertions.assertThatIterable(playerPieces.allPieces().getPieces())
                .containsExactlyInAnyOrderElementsOf(bluePieces.getPieces());
    }

    @Test
    void 궁이_없는_팀을_반환한다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new GungMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(1, 1), new FoMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        Team loseTeam = playerPieces.kingDeadTeam();
        Assertions.assertThat(loseTeam).isEqualTo(Team.RED);
    }

    @Test
    void 모두_궁이_있으면_아무것도_반환하지않는다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new GungMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(1, 1), new GungMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        Team loseTeam = playerPieces.kingDeadTeam();
        Assertions.assertThat(loseTeam).isEqualTo(Team.EMPTY);
    }

    @Test
    void 잡힌_기물이_있으면_잡은_플레이어는_점수를_얻는다() {
        var bluePiece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var redPiece = new Piece(new JanggiPosition(0, 3), new FoMoveBehavior(), Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playerPieces.placePhase(Team.BLUE, new JanggiPosition(0, 1), new JanggiPosition(0, 3));
        Map<Team, Integer> playerScores = playerPieces.getPlayerScores();
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(playerScores.get(Team.BLUE)).isEqualTo(7),
                () -> Assertions.assertThat(playerScores.get(Team.RED)).isEqualTo(0)
        );
    }
}
