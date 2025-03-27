package domain.janggiPiece;

import domain.hurdlePolicy.CannonHurdlePolicy;
import domain.hurdlePolicy.HurdlePolicy;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.Map;

public class Cannon extends SlidingJanggiPiece {
    private final HurdlePolicy hurdlePolicy = new CannonHurdlePolicy();

    public Cannon(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(2, 1), new Cannon(JanggiTeam.RED),
                JanggiPosition.of(2, 7), new Cannon(JanggiTeam.RED),
                JanggiPosition.of(7, 1), new Cannon(JanggiTeam.BLUE),
                JanggiPosition.of(7, 7), new Cannon(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.CANNON;
    }
}
