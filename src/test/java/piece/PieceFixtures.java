package piece;

import static position.Column.D;
import static position.Column.E;
import static position.Column.F;
import static position.Row.ONE;
import static position.Row.TWO;
import static position.Row.ZERO;

import position.Position;

public class PieceFixtures {
    public static final Position E0 = new Position(E, ZERO);
    public static final Position E1 = new Position(E, ONE);
    public static final Position E2 = new Position(E, TWO);

    public static final Position D2 = new Position(D, TWO);

    public static final Position F2 = new Position(F, TWO);
}
