package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFormationFactory {

    private static final Map<FormationType, AbstractFormationFactory> factories = Map.of(
            FormationType.LEFT_GIWMA, new LeftGwimaFactory(),
            FormationType.RIGHT_GIWMA, new RightGwimaFactory(),
            FormationType.WONANGMA, new WonangmaFactory(),
            FormationType.YANGGWIMA, new YanggwimaFactory()
    );

    public static AbstractFormationFactory from(FormationType type) {
        AbstractFormationFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalStateException("해당 타입의 팩토리가 등록되지 않았습니다.");
        }
        return factory;
    }

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
