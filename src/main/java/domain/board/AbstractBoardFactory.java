package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBoardFactory {

    private static final Map<Integer, AbstractBoardFactory> factories = Map.of(
            1, new LeftGwimaFactory(),
            2, new RightGwimaFactory(),
            3, new WonangmaFactory(),
            4, new YanggwimaFactory()
    );

    public static Map<Position, Piece> createFormation(AbstractBoardFactory choAbstractBoardFactory,
                                                       AbstractBoardFactory hanAbstractBoardFactory) {
        Map<Position, Piece> pieces = new HashMap<>();
        setFixedChoPieces(pieces);
        choAbstractBoardFactory.setVariablePieces(pieces, Team.CHO);
        setFixedHanPieces(pieces);
        hanAbstractBoardFactory.setVariablePieces(pieces, Team.HAN);
        return pieces;
    }

    public static AbstractBoardFactory from(int input) {
        return factories.get(input);
    }

    private static void setFixedChoPieces(Map<Position, Piece> pieces) {
        placePieces(pieces, Team.CHO, 1, PieceType.CHA);
        placePieces(pieces, Team.CHO, 1, PieceType.SA);
        placePieces(pieces, Team.CHO, 2, PieceType.GENERAL);
        placePieces(pieces, Team.CHO, 3, PieceType.PHO);
        placePieces(pieces, Team.CHO, 4, PieceType.BYEONG);
    }

    private static void setFixedHanPieces(Map<Position, Piece> pieces) {
        placePieces(pieces, Team.HAN, 10, PieceType.CHA);
        placePieces(pieces, Team.HAN, 10, PieceType.SA);
        placePieces(pieces, Team.HAN, 9, PieceType.GENERAL);
        placePieces(pieces, Team.HAN, 8, PieceType.PHO);
        placePieces(pieces, Team.HAN, 7, PieceType.BYEONG);
    }

    private static void placePieces(Map<Position, Piece> pieces, Team team, int row, PieceType type) {
        for (int column : type.getInitialColumns()) {
            pieces.put(new Position(row, column), type.createPiece(team));
        }
    }

    protected void placeVariablePieces(Map<Position, Piece> pieces, Team team, List<PieceType> formation) {
        List<Integer> columns = team == Team.CHO ? List.of(2, 3, 7, 8) : List.of(8, 7, 3, 2);
        for (int i = 0; i < columns.size(); i++) {
            PieceType pieceType = formation.get(i);
            pieces.put(new Position(getRow(team), columns.get(i)), pieceType.createPiece(team));
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
