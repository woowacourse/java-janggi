package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Team;
import model.coordinate.Position;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.Piece;

public enum FormationType {
    SANG_MA_SANG_MA(team -> List.of(new Elephant(team), new Horse(team), new Elephant(team), new Horse(team))),
    MA_SANG_MA_SANG(team -> List.of(new Horse(team), new Elephant(team), new Horse(team), new Elephant(team))),
    MA_SANG_SANG_MA(team -> List.of(new Horse(team), new Elephant(team), new Elephant(team), new Horse(team))),
    SANG_MA_MA_SANG(team -> List.of(new Elephant(team), new Horse(team), new Horse(team), new Elephant(team)));

    private static final Position HAN_LEFT_OUTER = new Position(0, 1);
    private static final Position HAN_LEFT_INNER = new Position(0, 2);
    private static final Position HAN_RIGHT_INNER = new Position(0, 6);
    private static final Position HAN_RIGHT_OUTER = new Position(0, 7);

    private static final Position CHO_LEFT_OUTER = new Position(9, 1);
    private static final Position CHO_LEFT_INNER = new Position(9, 2);
    private static final Position CHO_RIGHT_INNER = new Position(9, 6);
    private static final Position CHO_RIGHT_OUTER = new Position(9, 7);

    private final FormationStrategy strategy;

    FormationType(FormationStrategy strategy) {
        this.strategy = strategy;
    }

    private static List<Position> extractPositionsByTeam(Team team) {
        if (team == Team.HAN) {
            return List.of(HAN_LEFT_OUTER, HAN_LEFT_INNER, HAN_RIGHT_INNER, HAN_RIGHT_OUTER);
        }
        return List.of(CHO_LEFT_OUTER, CHO_LEFT_INNER, CHO_RIGHT_INNER, CHO_RIGHT_OUTER);
    }

    public Map<Position, Piece> generateByTeam(Team team) {
        List<Position> positions = extractPositionsByTeam(team);
        List<Piece> pieces = strategy.generateByTeam(team);

        Map<Position, Piece> formation = new HashMap<>();
        for (int i = 0; i < positions.size(); i++) {
            formation.put(positions.get(i), pieces.get(i));
        }
        return formation;
    }
}
