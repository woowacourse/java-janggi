package model.formation;

import model.Team;
import model.coordinate.Position;
import model.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormationFactory {

    private static final Map<JanggiFormation, FormationStrategy> FORMATION_STRATEGIES = Map.of(
            JanggiFormation.SANG_MA_SANG_MA, new SangMaSangMaStrategy(),
            JanggiFormation.MA_SANG_MA_SANG, new MaSangMaSangStrategy(),
            JanggiFormation.MA_SANG_SANG_MA, new MaSangSangMaStrategy(),
            JanggiFormation.SANG_MA_MA_SANG, new SangMaMaSangStrategy()
    );

    public static Map<Position, Piece> generateFormation(JanggiFormation hanFormation, JanggiFormation choFormation) {
        Map<Position, Piece> formations = new HashMap<>();
        formations.putAll(extractFormationByTeam(hanFormation, Team.HAN));
        formations.putAll(extractFormationByTeam(choFormation, Team.CHO));
        return Map.copyOf(formations);
    }

    private static Map<Position, Piece> extractFormationByTeam(JanggiFormation formation, Team team) {
        return Optional.ofNullable(FORMATION_STRATEGIES.get(formation))
                .map(strategy -> strategy.generateFormation(team))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상차림 생성 전략입니다."));
    }
}
