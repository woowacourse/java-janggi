package janggi.domain.board;

import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;

import janggi.domain.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GungSeong {

    private static final int GUNG_SEONG_HEIGHT = 3;
    private static final int GUNG_SEONG_WIDTH = 3;
    private static final int INDEX_ADJUSTMENT = 1;

    private final List<Location> hanArea;
    private final List<Location> choArea;
    private final List<List<Location>> possibleDiagonalPaths;

    private GungSeong(List<Location> hanArea, List<Location> choArea) {
        this.hanArea = List.copyOf(hanArea);
        this.choArea = List.copyOf(choArea);
        this.possibleDiagonalPaths = calculateDiagonalPaths(this.hanArea, this.choArea);
    }

    private List<List<Location>> calculateDiagonalPaths(List<Location> hanArea, List<Location> choArea) {
        int indexOfCenter = (GUNG_SEONG_HEIGHT * GUNG_SEONG_WIDTH) / 2;

        Location hanCenter = hanArea.get(indexOfCenter);
        Location choCenter = choArea.get(indexOfCenter);

        return List.of(
                List.of(FRONT_LEFT.apply(hanCenter), hanCenter , BACK_RIGHT.apply(hanCenter)),
                List.of(FRONT_RIGHT.apply(hanCenter), hanCenter, BACK_LEFT.apply(hanCenter)),

                List.of(FRONT_LEFT.apply(choCenter), choCenter, BACK_RIGHT.apply(choCenter)),
                List.of(FRONT_RIGHT.apply(choCenter), choCenter, BACK_LEFT.apply(choCenter))
        );
    }

    public static GungSeong of(int height, int width) {
        int startCol = (width / 2) - INDEX_ADJUSTMENT;
        int choStartRow = height - GUNG_SEONG_HEIGHT;

        return new GungSeong(
                createArea(0, startCol),
                createArea(choStartRow, startCol)
        );
    }

    private static List<Location> createArea(int startRow, int startCol) {
        List<Location> area = new ArrayList<>();
        for (int rowIndex = startRow; rowIndex < startRow + GUNG_SEONG_HEIGHT; rowIndex++) {
            addGungSeongLocationLineByLine(startCol, area, rowIndex);
        }
        return area;
    }

    private static void addGungSeongLocationLineByLine(int startColIndex, List<Location> gungSeongArea, int rowIndex) {
        for (int colIndex = startColIndex; colIndex < startColIndex + GUNG_SEONG_WIDTH; colIndex++) {
            gungSeongArea.add(new Location(rowIndex, colIndex));
        }
    }

    public boolean contains(Location location) {
        return hanArea.contains(location) || choArea.contains(location);
    }

    public Optional<List<Location>> findValidDiagonalPath(Location from, Location to) {
        Optional<List<Location>> diagonalPath = getDiagonallyConnectedPath(from, to);
        return diagonalPath.flatMap(path -> getValidDiagonalPath(from, to, path));
    }

    private Optional<List<Location>> getValidDiagonalPath(Location from, Location to, List<Location> diagonalPath) {
        int fromIndex = diagonalPath.indexOf(from);
        int toIndex = diagonalPath.indexOf(to);
        if (fromIndex > toIndex) {
            List<Location> reversedPath = new ArrayList<>(diagonalPath.subList(toIndex, fromIndex));
            Collections.reverse(reversedPath);
            return Optional.of(reversedPath);
        }
        return Optional.of(List.copyOf(diagonalPath.subList(fromIndex + 1, toIndex + 1)));
    }

    private Optional<List<Location>> getDiagonallyConnectedPath(Location from, Location to) {
        return possibleDiagonalPaths.stream()
                .filter(path -> path.contains(from) && path.contains(to))
                .findAny();
    }
}
