package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoardFactory {

    private static final Map<Integer, BoardFactory> FACTORIES = Map.of(
            1, new LeftGwimaFactory(),
            2, new RightGwimaFactory(),
            3, new WonangmaFactory(),
            4, new YanggwimaFactory()
    );

    public static Map<Position, Piece> createFormation(int choFormationNumber, int hanFormationNumber) {
        BoardFactory choBoardFactory = BoardFactory.from(choFormationNumber);
        BoardFactory hanBoardFactory = BoardFactory.from(hanFormationNumber);
        Map<Position, Piece> pieces = new HashMap<>();
        setFixedChoPieces(pieces);
        choBoardFactory.setVariablePieces(pieces, Team.CHO);
        setFixedHanPieces(pieces);
        hanBoardFactory.setVariablePieces(pieces, Team.HAN);
        return pieces;
    }

    public static BoardFactory from(int input) {
        return FACTORIES.get(input);
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

    protected void placeVariablePieces(Map<Position, Piece> pieces, Team team, List<PieceDefinition> formation) {
        List<Integer> columns;
        if (team == Team.CHO) {
            columns = List.of(2, 3, 7, 8);
        } else {
            columns = List.of(8, 7, 3, 2);
        }
        for (int i = 0; i < columns.size(); i++) {
            PieceDefinition pieceDefinition = formation.get(i);
            pieces.put(new Position(getRow(team), columns.get(i)), pieceDefinition.createPiece(team));
        }
    }

    protected int getRow(Team team) {
        if (team.equals(Team.CHO)) {
            return 1;
        }
        return 10;
    }

    protected abstract void setVariablePieces(Map<Position, Piece> pieces, Team team);
}
