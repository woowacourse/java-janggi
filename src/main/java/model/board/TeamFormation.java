package model.board;

import java.util.Map;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;

public record TeamFormation(Team team, FormationType type) {

    public Map<Position, Piece> generate() {
        return type.generateByTeam(team);
    }
}