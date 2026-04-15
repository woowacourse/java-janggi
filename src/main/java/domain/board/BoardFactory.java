package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private BoardFactory() {
    }

    public static Map<Position, Piece> createFormation(int choFormationNumber, int hanFormationNumber) {
        Formation choFormation = Formation.from(choFormationNumber);
        Formation hanFormation = Formation.from(hanFormationNumber);
        Map<Position, Piece> pieces = new HashMap<>();
        setFixedChoPieces(pieces);
        placeVariablePieces(pieces, Team.CHO, choFormation.getVariablePieces());
        setFixedHanPieces(pieces);
        placeVariablePieces(pieces, Team.HAN, hanFormation.getVariablePieces());
        return pieces;
    }

    private static void setFixedChoPieces(Map<Position, Piece> pieces) {
        placePieces(pieces, Team.CHO, 1, PieceDefinition.CHA);
        placePieces(pieces, Team.CHO, 1, PieceDefinition.SA);
        placePieces(pieces, Team.CHO, 2, PieceDefinition.GENERAL);
        placePieces(pieces, Team.CHO, 3, PieceDefinition.PHO);
        placePieces(pieces, Team.CHO, 4, PieceDefinition.BYEONG);
    }

    private static void setFixedHanPieces(Map<Position, Piece> pieces) {
        placePieces(pieces, Team.HAN, 10, PieceDefinition.CHA);
        placePieces(pieces, Team.HAN, 10, PieceDefinition.SA);
        placePieces(pieces, Team.HAN, 9, PieceDefinition.GENERAL);
        placePieces(pieces, Team.HAN, 8, PieceDefinition.PHO);
        placePieces(pieces, Team.HAN, 7, PieceDefinition.BYEONG);
    }

    private static void placePieces(Map<Position, Piece> pieces, Team team, int row, PieceDefinition type) {
        for (int column : type.getInitialColumns()) {
            pieces.put(new Position(row, column), type.createPiece(team));
        }
    }

    private static void placeVariablePieces(Map<Position, Piece> pieces, Team team, List<PieceDefinition> formation) {
        List<Integer> columns = setupColumns(team);
        int row = getRow(team);
        for (int i = 0; i < columns.size(); i++) {
            PieceDefinition pieceDefinition = formation.get(i);
            pieces.put(new Position(row, columns.get(i)), pieceDefinition.createPiece(team));
        }
    }

    private static List<Integer> setupColumns(Team team) {
        if (team == Team.CHO) {
            return List.of(2, 3, 7, 8);
        }
        return List.of(8, 7, 3, 2);
    }

    private static int getRow(Team team) {
        if (team == Team.CHO) {
            return 1;
        }
        return 10;
    }
}
