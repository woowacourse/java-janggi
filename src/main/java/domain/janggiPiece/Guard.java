package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.Map;

public class Guard extends CastleJanggiPiece {
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Guard(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(0, 3), new Guard(JanggiTeam.RED),
                JanggiPosition.of(0, 5), new Guard(JanggiTeam.RED),
                JanggiPosition.of(9, 3), new Guard(JanggiTeam.BLUE),
                JanggiPosition.of(9, 5), new Guard(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.GUARD;
    }
}
