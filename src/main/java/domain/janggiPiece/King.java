package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.Map;

public class King extends CastleJanggiPiece {
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public King(final JanggiTeam team) {
        super(team);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPositionFactory.of(1, 4), new King(JanggiTeam.RED),
                JanggiPositionFactory.of(8, 4), new King(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.KING;
    }
}
