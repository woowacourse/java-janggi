package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.StopAtHurdlePolicy;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.Map;

public class Chariot extends SlidingJanggiPiece {
    private final HurdlePolicy hurdlePolicy = new StopAtHurdlePolicy();

    public Chariot(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(0, 0), new Chariot(JanggiTeam.RED),
                JanggiPosition.of(0, 8), new Chariot(JanggiTeam.RED),
                JanggiPosition.of(9, 0), new Chariot(JanggiTeam.BLUE),
                JanggiPosition.of(9, 8), new Chariot(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.CHARIOT;
    }
}
