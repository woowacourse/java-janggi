package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBoardFactory implements BoardFactory {

    private static final Map<Integer, AbstractBoardFactory> factories = Map.of(
            1, new LeftGwimaFactory(),
            2, new RightGwimaFactory(),
            3, new WonangmaFactory(),
            4, new YanggwimaFactory()
    );

    public static AbstractBoardFactory from(int input) {
        return factories.get(input);
    }

    @Override
    public Map<Position, Piece> createFormation(Team team) {
        Map<Position, Piece> pieces = new HashMap<>();
        setFixedPieces(pieces, team);
        setVariablePieces(pieces, team);
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

    protected abstract void setVariablePieces(Map<Position, Piece> pieces, Team team);
}
