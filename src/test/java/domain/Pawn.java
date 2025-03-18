package domain;

import java.util.ArrayList;
import java.util.List;

public class Pawn {

    private final int cR;
    private final int cC;

    public Pawn(final int cR, final int cC) {
        this.cR = cR;
        this.cC = cC;
    }

    public List<int[]> getAvailablePositions() {
        final int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        final List<int[]> pos = new ArrayList<>();
        for (int[] d : ds) {
            int nR = cR + d[0];
            int nC = cC + d[1];
            pos.add(new int[]{nR, nC});
        }
        return pos;
    }
}
