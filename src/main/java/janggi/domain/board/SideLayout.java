package janggi.domain.board;

import janggi.domain.Side;
import java.util.List;
import java.util.Map;

public class SideLayout {
    private static final Map<Side, SideLayout> LAYOUTS = Map.of(
            Side.CHO, new SideLayout(0, 1, 2, 3, List.of(1, 2, 6, 7)),
            Side.HAN, new SideLayout(9, 8, 7, 6, List.of(7, 6, 2, 1))
    );

    private final int baseY;
    private final int generalY;
    private final int cannonY;
    private final int soldierY;
    private final List<Integer> formationX;

    private SideLayout(int baseY, int generalY, int cannonY, int soldierY, List<Integer> formationX) {
        this.baseY = baseY;
        this.generalY = generalY;
        this.cannonY = cannonY;
        this.soldierY = soldierY;
        this.formationX = formationX;
    }

    public static SideLayout from(Side side) {
        return LAYOUTS.get(side);
    }

    public int getBaseY() { return baseY; }
    public int getGeneralY() { return generalY; }
    public int getCannonY() { return cannonY; }
    public int getSoldierY() { return soldierY; }
    public List<Integer> getFormationX() { return formationX; }
}
