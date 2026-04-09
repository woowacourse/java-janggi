package janggi.domain.board;

import janggi.domain.Location;
import java.util.ArrayList;
import java.util.List;

public class GungSeong {

    private static final int GUNG_SEONG_HEIGHT = 3;
    private static final int GUNG_SEONG_WIDTH = 3;
    private static final int INDEX_ADJUSTMENT = 1;

    private final List<Location> gungSeongArea;

    private GungSeong(List<Location> gungSeongArea) {
        this.gungSeongArea = gungSeongArea;
    }

    public static GungSeong of(int height, int width) {
        List<Location> gungSeongArea = new ArrayList<>();

        int gungSeongStartColIndex = (width / 2) - INDEX_ADJUSTMENT;

        for (int rowIndex = 0; rowIndex < GUNG_SEONG_HEIGHT; rowIndex++) {
            addGungSeongLocationLineByLine(gungSeongStartColIndex, gungSeongArea, rowIndex);
        }

        for (int rowIndex = height - INDEX_ADJUSTMENT; rowIndex >= height - GUNG_SEONG_HEIGHT; rowIndex--) {
            addGungSeongLocationLineByLine(gungSeongStartColIndex, gungSeongArea, rowIndex);
        }

        return new GungSeong(gungSeongArea);
    }

    private static void addGungSeongLocationLineByLine(int startColIndex, List<Location> gungSeongArea, int rowIndex) {
        for (int colIndex = startColIndex; colIndex < startColIndex + GUNG_SEONG_WIDTH; colIndex++) {
            gungSeongArea.add(new Location(rowIndex, colIndex));
        }
    }

    public boolean contains(Location location) {
        return gungSeongArea.contains(location);
    }
}
