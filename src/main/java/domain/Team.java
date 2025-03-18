package domain;

import domain.position.Position;
import domain.position.PositionFile;
import domain.position.PositionRank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Team {

    private final Map<Position, PieceType> board;

    public Team(final StartingPosition startingPosition, final Country country) {
        board = new HashMap<>();
        board.putAll(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, PositionRank.of(1, country)), PieceType.차),
                Map.entry(new Position(PositionFile.라, PositionRank.of(1, country)), PieceType.사),
                Map.entry(new Position(PositionFile.바, PositionRank.of(1, country)), PieceType.사),
                Map.entry(new Position(PositionFile.자, PositionRank.of(1, country)), PieceType.차),
                Map.entry(new Position(PositionFile.마, PositionRank.of(2, country)), PieceType.장),
                Map.entry(new Position(PositionFile.나, PositionRank.of(3, country)), PieceType.포),
                Map.entry(new Position(PositionFile.아, PositionRank.of(3, country)), PieceType.포),
                Map.entry(new Position(PositionFile.가, PositionRank.of(4, country)), PieceType.졸),
                Map.entry(new Position(PositionFile.다, PositionRank.of(4, country)), PieceType.졸),
                Map.entry(new Position(PositionFile.마, PositionRank.of(4, country)), PieceType.졸),
                Map.entry(new Position(PositionFile.사, PositionRank.of(4, country)), PieceType.졸),
                Map.entry(new Position(PositionFile.자, PositionRank.of(4, country)), PieceType.졸)
        ));
        switch (startingPosition) {
            case 마상마상 -> {
                board.put(new Position(PositionFile.나, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.다, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.사, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.아, PositionRank.of(1, country)), PieceType.상);
            }
            case 상마상마 -> {
                board.put(new Position(PositionFile.나, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.다, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.사, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.아, PositionRank.of(1, country)), PieceType.마);
            }
            case 상마마상 -> {
                board.put(new Position(PositionFile.나, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.다, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.사, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.아, PositionRank.of(1, country)), PieceType.상);
            }
            case 마상상마 -> {
                board.put(new Position(PositionFile.나, PositionRank.of(1, country)), PieceType.마);
                board.put(new Position(PositionFile.다, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.사, PositionRank.of(1, country)), PieceType.상);
                board.put(new Position(PositionFile.아, PositionRank.of(1, country)), PieceType.마);
            }
        }
    }

    public Map<Position, PieceType> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
