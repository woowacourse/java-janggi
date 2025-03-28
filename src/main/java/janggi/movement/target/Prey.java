package janggi.movement.target;

import janggi.movement.middleRoute.Hurdles;
import janggi.piece.Piece;
import janggi.point.Point;

public class Prey {
    private final Piece prey;
    private final Piece attacker;

    public Prey(Piece prey, Piece attacker) {
        this.prey = prey;
        this.attacker = attacker;
    }

    public static Prey from(Point targetPoint, Hurdles hurdles, Piece attacker) {
        try {
            Piece prey = hurdles.findByPoint(targetPoint);
            return new Prey(prey, attacker);
        } catch (IllegalStateException e) {
            return new Prey(null, attacker);
        }
    }

    public boolean canAttack() {
        if (prey == null) {
            return true;
        }
        if (prey.isPo() && attacker.isPo()) {
            return false;
        }
        return prey.getTeam() != attacker.getTeam();
    }
}
