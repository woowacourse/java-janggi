package piece.initiate;

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
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class InitiateJanggiPlayerPiecesTest {

    final List<Piece> redTeamInitiatePieces = List.of(
            new Piece(new JanggiPosition(0, 0), new ChaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 1), new SangMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 2), new MaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 3), new SaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 5), new SaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 6), new SangMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 7), new MaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(0, 8), new ChaMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(1, 4), new GungMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(2, 1), new FoMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(2, 7), new FoMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(3, 0), new JolMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(3, 2), new JolMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(3, 4), new JolMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(3, 6), new JolMoveBehavior(), Team.RED),
            new Piece(new JanggiPosition(3, 8), new JolMoveBehavior(), Team.RED)
    );

    final List<Piece> blueTeamInitiatePieces = List.of(
            new Piece(new JanggiPosition(6, 0), new JolMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(6, 2), new JolMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(6, 4), new JolMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(6, 6), new JolMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(6, 8), new JolMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(7, 1), new FoMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(7, 7), new FoMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(8, 4), new GungMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 0), new ChaMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 1), new SangMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 2), new MaMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 3), new SaMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 5), new SaMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 6), new SangMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 7), new MaMoveBehavior(), Team.BLUE),
            new Piece(new JanggiPosition(9, 8), new ChaMoveBehavior(), Team.BLUE)
    );

    @Test
    void 홍팀_기물_테스트() {
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();
        Pieces redTeamPieces = piecesMap.get(Team.RED);
        List<Piece> pieces = redTeamPieces.getPieces();
        for (Piece piece : pieces) {
            System.out.println(piece.toString());
        }
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
        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces().janggiInitiatePieces();

        Assertions.assertThat(piecesMap.get(Team.BLUE).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size()).isEqualTo(16);
        Assertions.assertThat(piecesMap.get(Team.RED).size() + piecesMap.get(Team.BLUE).size()).isEqualTo(32);
    }

    @Test
    void 상차림을_옵션으로_받을수_있다() {
        Map<Team, TableSetting> teamTableSetting = Map.of(Team.BLUE, TableSetting.SANG_MA_MA_SANG, Team.RED,
                TableSetting.SANG_MA_SANG_MA);

        List<Piece> expectedMaSangs = List.of(
                new Piece(new JanggiPosition(9, 1), new SangMoveBehavior(), Team.BLUE),
                new Piece(new JanggiPosition(9, 2), new MaMoveBehavior(), Team.BLUE),
                new Piece(new JanggiPosition(9, 6), new MaMoveBehavior(), Team.BLUE),
                new Piece(new JanggiPosition(9, 7), new SangMoveBehavior(), Team.BLUE),
                new Piece(new JanggiPosition(0, 1), new SangMoveBehavior(), Team.RED),
                new Piece(new JanggiPosition(0, 2), new MaMoveBehavior(), Team.RED),
                new Piece(new JanggiPosition(0, 6), new SangMoveBehavior(), Team.RED),
                new Piece(new JanggiPosition(0, 7), new MaMoveBehavior(), Team.RED)
        );

        Map<Team, Pieces> piecesMap = new InitiateJanggiTeamPieces(teamTableSetting).janggiInitiatePieces();
        List<Piece> createdPieces = new ArrayList<>();
        for (Pieces pieces : piecesMap.values()) {
            createdPieces.addAll(pieces.getPieces());
        }
        Assertions.assertThat(createdPieces).containsAll(expectedMaSangs);
    }
}
