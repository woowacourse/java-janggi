package domain.board;

import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory implements FormationFactory {

    @Override
    public Map<Position, Piece> createFormation(Team team, int formationNumber) {
        Map<Position, Piece> pieces = new HashMap<>();
        setFixedPieces(pieces, team);
        return pieces;
    }

    private void setFixedPieces(Map<Position, Piece> pieces, Team team) {
        placePieces(pieces, team, team.getBackRow(), PieceType.CHA);
        placePieces(pieces, team, team.getBackRow(), PieceType.SA);
        placePieces(pieces, team, team.getGeneralRow(), PieceType.GENERAL);
        placePieces(pieces, team, team.getCannonRow(), PieceType.PHO);
        placePieces(pieces, team, team.getSoldierRow(), PieceType.BYEONG);
    }

    private void placePieces(Map<Position, Piece> pieces, Team team, int row, PieceType type) {
        for (int column : type.getInitialColumns()) {
            pieces.put(new Position(row, column), type.createPiece(team));
        }
    }
}
