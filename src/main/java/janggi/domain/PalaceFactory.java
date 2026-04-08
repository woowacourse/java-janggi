package janggi.domain;

import janggi.util.ResourceReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PalaceFactory {

    private static final String PALACE_BOUNDARY_DEFAULT_PATH = "data/PalaceBoundary.csv";
    private static final String PALACE_CENTER_DEFAULT_PATH = "data/PalaceCenter.csv";

    private PalaceFactory() {
    }

    public static Set<Position> generateBoundary() {
        Set<Position> palaceBoundary = new HashSet<>();

        try (BufferedReader boundaryReader = ResourceReader.getBufferedReader(PALACE_BOUNDARY_DEFAULT_PATH)) {
            parseBoundaryData(palaceBoundary, boundaryReader);

            return palaceBoundary;
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 궁성 초기화 실패");
        }
    }

    private static void parseBoundaryData(Set<Position> palaceBoundary, BufferedReader br) throws IOException {
        String line = br.readLine(); // header skip
        while ((line = br.readLine()) != null) {
            List<String> parts = List.of(line.split(","));
            validateDataFormat(parts, line);
            int x = Integer.parseInt(parts.get(0));
            int y = Integer.parseInt(parts.get(1));
            palaceBoundary.add(new Position(x, y));
        }
    }

    public static Set<Position> generateCenter() {
        Set<Position> palaceCenter = new HashSet<>();

        try (BufferedReader centerReader = ResourceReader.getBufferedReader(PALACE_CENTER_DEFAULT_PATH)) {
            parseCenterData(palaceCenter, centerReader);

            return palaceCenter;
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 궁성 초기화 실패");
        }
    }

    private static void parseCenterData(Set<Position> palaceCenter, BufferedReader br) throws IOException {
        String line = br.readLine(); // header skip
        while ((line = br.readLine()) != null) {
            List<String> parts = List.of(line.split(","));
            validateDataFormat(parts, line);
            int x = Integer.parseInt(parts.get(0));
            int y = Integer.parseInt(parts.get(1));

            palaceCenter.add(new Position(x, y));
        }
    }

    private static void validateDataFormat(List<String> parts, String line) {
        if (parts.size() != 2) {
            throw new IllegalArgumentException("잘못된 CSV 형식: " + line);
        }
    }
}
