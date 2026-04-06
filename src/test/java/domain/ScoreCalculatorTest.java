package domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import strategy.formation.InnerFormationStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCalculatorTest {

    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    @Test
    void 빈_보드는_팀별_기준_점수만_반영한다() {
        GameSnapshot snapshot = GameSnapshot.from(new HashMap<>());
        TeamScores scores = scoreCalculator.calculate(snapshot);

        assertThat(scores.pointsFor(TeamColor.CHO)).isEqualTo(MaterialPoints.of(72));
        assertThat(scores.pointsFor(TeamColor.HAN)).isEqualTo(MaterialPoints.of(73.5));
    }

    @Test
    void 초_차_한_기만_있으면_72에_13을_더한다() {
        Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
        GameSnapshot snapshot = GameSnapshot.from(Map.of(Position.of(0, 0), choRook));
        TeamScores scores = scoreCalculator.calculate(snapshot);

        assertThat(scores.pointsFor(TeamColor.CHO)).isEqualTo(MaterialPoints.of(85));
        assertThat(scores.pointsFor(TeamColor.HAN)).isEqualTo(MaterialPoints.of(73.5));
    }

    @Test
    void InnerFormation_초기판이면_초_144_한_145점5이다() {
        InnerFormationStrategy strategy = new InnerFormationStrategy();
        Map<Position, Piece> pieceMap = new HashMap<>();
        pieceMap.putAll(strategy.setUpPieces(TeamColor.CHO));
        pieceMap.putAll(strategy.setUpPieces(TeamColor.HAN));
        GameSnapshot snapshot = GameSnapshot.from(pieceMap);
        TeamScores scores = scoreCalculator.calculate(snapshot);

        assertThat(scores.pointsFor(TeamColor.CHO)).isEqualTo(MaterialPoints.of(144));
        assertThat(scores.pointsFor(TeamColor.HAN)).isEqualTo(MaterialPoints.of(145.5));
    }
}
