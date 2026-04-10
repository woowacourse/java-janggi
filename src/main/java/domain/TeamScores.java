package domain;

import java.util.Optional;

public final class TeamScores {

    private final MaterialPoints choTotal;
    private final MaterialPoints hanTotal;

    private TeamScores(MaterialPoints choTotal, MaterialPoints hanTotal) {
        this.choTotal = choTotal;
        this.hanTotal = hanTotal;
    }

    public static TeamScores of(MaterialPoints choTotal, MaterialPoints hanTotal) {
        return new TeamScores(choTotal, hanTotal);
    }

    public MaterialPoints pointsFor(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return choTotal;
        }
        return hanTotal;
    }

    public Optional<TeamColor> winner() {
        long cho = scaledHalfPointUnits(choTotal);
        long han = scaledHalfPointUnits(hanTotal);
        if (cho > han) {
            return Optional.of(TeamColor.CHO);
        }
        if (han > cho) {
            return Optional.of(TeamColor.HAN);
        }
        return Optional.empty();
    }

    private long scaledHalfPointUnits(MaterialPoints points) {
        return Math.round(points.value() * 2);
    }
}
