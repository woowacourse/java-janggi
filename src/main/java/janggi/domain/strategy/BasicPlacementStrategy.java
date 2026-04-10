package janggi.domain.strategy;

import janggi.domain.space.piece.PieceFactory;
import janggi.domain.space.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import janggi.util.ResourceReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BasicPlacementStrategy implements InitializeStrategy {

    private static final String DEFAULT_PATH = "data/BasicPlacement.csv";

    @Override
    public void basicSetting(Map<Position, Space> blankBoard) {
        try (BufferedReader br = ResourceReader.getBufferedReader(DEFAULT_PATH)) {
            parseData(blankBoard, br);
        } catch (Exception e) {
            throw new IllegalArgumentException("기물 초기화 실패");
        }
    }

    private void parseData(Map<Position, Space> blankBoard, BufferedReader br) throws IOException {
        String line = br.readLine(); // header skip
        while ((line = br.readLine()) != null) {
            List<String> parts = List.of(line.split(","));
            validateDataFormat(parts, line);
            int x = Integer.parseInt(parts.get(0));
            int y = Integer.parseInt(parts.get(1));
            Team team = Team.from(parts.get(2));
            PieceType pieceType = PieceType.from(parts.get(3));
            blankBoard.put(new Position(x, y), PieceFactory.createPiece(team, pieceType));
        }
    }

    private void validateDataFormat(List<String> parts, String line) {
        if (parts.size() != 4) {
            throw new IllegalArgumentException("잘못된 CSV 형식: " + line);
        }
    }
}
