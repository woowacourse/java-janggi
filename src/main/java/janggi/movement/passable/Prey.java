package janggi.movement.passable;

import janggi.movement.route.Hurdles;
import janggi.piece.Movable;
import janggi.point.Point;

public class Prey {
    private final Movable prey;
    private final Movable attacker;

    public Prey(Movable prey, Movable attacker) {
        this.prey = prey;
        this.attacker = attacker;
    }

    public static Prey from(Point targetPoint, Hurdles hurdles, Movable attacker) {
        Movable prey = null; //TODO 해결하기
        if (hurdles.containsPoint(targetPoint)) {
            prey = hurdles.findByPoint(targetPoint);
        }
        return new Prey(prey, attacker);
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
