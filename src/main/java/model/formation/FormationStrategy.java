package model.formation;

import model.Team;
import model.coordinate.Position;
import model.piece.Piece;

import java.util.Map;

public abstract class FormationStrategy {

    public Map<Position, Piece> generateFormation(Team team) {
        if (team == Team.HAN) {
            return generateHanFormation();
        }
        return generateChoFormation();
    }

    protected abstract Map<Position, Piece> generateHanFormation();

    protected abstract Map<Position, Piece> generateChoFormation();
}
