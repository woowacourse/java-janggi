package domain.board;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;

public class BoardFactory implements FormationFactory {
    @Override
    public Map<Position, Piece> createFormation(Team team, int formationNumber) {
        return Map.of();
    }


    /*
    @Override
    public Map<Position, Piece> createFormation(Team team, int formationNumber) {
        Map <Position, Piece> pieces = new HashMap<>();
        setFixedPieces(pieces, team);
    }

    private void setFixedPieces(Map<Position, Piece> pieces, Team team) {
        pieces.put(new Position(0,0), new Piece(team));
    } */
}
