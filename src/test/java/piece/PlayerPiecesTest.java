package piece;

import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PlayerPiecesTest {

    @Test
    void 장기판의_모든_피스들을_가지고올_수_있다() {
        // given
        var bluePiece = new Piece(new Position(0, 1), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        var redPiece = new Piece(new Position(0, 2), new FoMoveBehavior(), PieceType.FO, Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        // when
        Pieces allPieces = playerPieces.allPieces();
        // then
        Assertions.assertThat(allPieces.getPieces())
                .containsExactlyInAnyOrderElementsOf(bluePieces.add(redPieces).getPieces());
    }

    @Test
    void 기물을_움직일_수_있다() {
        // given
        var bluePiece = new Piece(new Position(0, 1), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        var redPiece = new Piece(new Position(0, 3), new FoMoveBehavior(), PieceType.FO, Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playerPieces.move(Team.BLUE, new Position(0, 1), new Position(1, 1));
        Pieces allPieces = playerPieces.allPieces();

        var expectedBluePiece = new Piece(new Position(1, 1), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        // then
        Assertions.assertThat(allPieces.getPieces())
                .contains(expectedBluePiece);

    }

    @Test
    void 기물을_움직이고_중복된_기물은_제거된다() {
        // given
        var bluePiece = new Piece(new Position(0, 1), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        var redPiece = new Piece(new Position(1, 1), new FoMoveBehavior(), PieceType.FO, Team.RED);
        Pieces bluePieces = new Pieces(List.of(bluePiece));
        Pieces redPieces = new Pieces(List.of(redPiece));
        Map<Team, Pieces> teamPieces = Map.of(Team.BLUE, bluePieces, Team.RED, redPieces);
        PlayerPieces playerPieces = new PlayerPieces(teamPieces);
        playerPieces.move(Team.BLUE, new Position(0, 1), new Position(1, 1));

        // then
        Assertions.assertThatIterable(playerPieces.allPieces().getPieces())
                .containsExactlyInAnyOrderElementsOf(bluePieces.getPieces());
    }
}
