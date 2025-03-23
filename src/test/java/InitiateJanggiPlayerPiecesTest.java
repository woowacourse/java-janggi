import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JolMoveBehavior;
import move.MaMoveBehavior;
import move.SaMoveBehavior;
import move.SangMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.InitiateJanggiTeamPieces;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.TableSetting;
import piece.Team;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InitiateJanggiPlayerPiecesTest {

    final List<Piece> redTeamInitiatePieces = List.of(
            new Piece(new Position(0, 0), new ChaMoveBehavior(), PieceType.CHA, Team.RED),
            new Piece(new Position(0, 1), new SangMoveBehavior(), PieceType.SANG, Team.RED),
            new Piece(new Position(0, 2), new MaMoveBehavior(), PieceType.MA, Team.RED),
            new Piece(new Position(0, 3), new SaMoveBehavior(), PieceType.SA, Team.RED),
            new Piece(new Position(0, 5), new SaMoveBehavior(), PieceType.SA, Team.RED),
            new Piece(new Position(0, 6), new SangMoveBehavior(), PieceType.SANG, Team.RED),
            new Piece(new Position(0, 7), new MaMoveBehavior(), PieceType.MA, Team.RED),
            new Piece(new Position(0, 8), new ChaMoveBehavior(), PieceType.CHA, Team.RED),
            new Piece(new Position(1, 4), new GungMoveBehavior(), PieceType.GUNG, Team.RED),
            new Piece(new Position(2, 1), new FoMoveBehavior(), PieceType.FO, Team.RED),
            new Piece(new Position(2, 7), new FoMoveBehavior(), PieceType.FO, Team.RED),
            new Piece(new Position(3, 0), new JolMoveBehavior(), PieceType.JOL, Team.RED),
            new Piece(new Position(3, 2), new JolMoveBehavior(), PieceType.JOL, Team.RED),
            new Piece(new Position(3, 4), new JolMoveBehavior(), PieceType.JOL, Team.RED),
            new Piece(new Position(3, 6), new JolMoveBehavior(), PieceType.JOL, Team.RED),
            new Piece(new Position(3, 8), new JolMoveBehavior(), PieceType.JOL, Team.RED)
    );

    final List<Piece> blueTeamInitiatePieces = List.of(
            new Piece(new Position(6, 0), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
            new Piece(new Position(6, 2), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
            new Piece(new Position(6, 4), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
            new Piece(new Position(6, 6), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
            new Piece(new Position(6, 8), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
            new Piece(new Position(7, 1), new FoMoveBehavior(), PieceType.FO, Team.BLUE),
            new Piece(new Position(7, 7), new FoMoveBehavior(), PieceType.FO, Team.BLUE),
            new Piece(new Position(8, 4), new GungMoveBehavior(), PieceType.GUNG, Team.BLUE),
            new Piece(new Position(9, 0), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE),
            new Piece(new Position(9, 1), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
            new Piece(new Position(9, 2), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
            new Piece(new Position(9, 3), new SaMoveBehavior(), PieceType.SA, Team.BLUE),
            new Piece(new Position(9, 5), new SaMoveBehavior(), PieceType.SA, Team.BLUE),
            new Piece(new Position(9, 6), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
            new Piece(new Position(9, 7), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
            new Piece(new Position(9, 8), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE)
    );

    @Test
    void 홍팀_기물_테스트() {
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();
        Pieces redTeamPieces = piecesMap.get(Team.RED);
        List<Piece> pieces = redTeamPieces.getPieces();
        System.out.println(pieces.size() + "," + redTeamPieces.size());
        Assertions.assertThatIterable(pieces).containsExactlyInAnyOrderElementsOf(redTeamInitiatePieces);
    }

    @Test
    void 청팀_기물_테스트() {
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();
        Pieces blueTeamPieces = piecesMap.get(Team.BLUE);
        List<Piece> pieces = blueTeamPieces.getPieces();
        Assertions.assertThatIterable(pieces).containsExactlyInAnyOrderElementsOf(blueTeamInitiatePieces);
    }

    @Test
    void 장기는_총_32피스가_있고_팀별로_16개씩_나눠가진다() {
        // when
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();

        // then
        Assertions.assertThat(piecesMap.get(Team.BLUE).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size() + piecesMap.get(Team.BLUE).size()).isEqualTo(32);
    }

    @Test
    void 상차림을_옵션으로_받을수_있다() {
        // given
        Map<Team, TableSetting> teamTableSetting = Map.of(Team.BLUE, TableSetting.SANG_MA_MA_SANG, Team.RED,
                TableSetting.SANG_MA_SANG_MA);

        List<Piece> expectedMaSangs = List.of(
                new Piece(new Position(9, 1), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
                new Piece(new Position(9, 2), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
                new Piece(new Position(9, 6), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
                new Piece(new Position(9, 7), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
                new Piece(new Position(0, 1), new SangMoveBehavior(), PieceType.SANG, Team.RED),
                new Piece(new Position(0, 2), new MaMoveBehavior(), PieceType.MA, Team.RED),
                new Piece(new Position(0, 6), new SangMoveBehavior(), PieceType.SANG, Team.RED),
                new Piece(new Position(0, 7), new MaMoveBehavior(), PieceType.MA, Team.RED)
        );

        // when
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces(teamTableSetting).janggiInitiatePieces();
        List<Piece> createdPieces = new ArrayList<>();
        for (Pieces pieces : piecesMap.values()) {
            createdPieces.addAll(pieces.getPieces());
        }
        // then
        Assertions.assertThat(createdPieces).containsAll(expectedMaSangs);
    }
}
