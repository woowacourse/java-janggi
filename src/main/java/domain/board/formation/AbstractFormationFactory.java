package domain.board.formation;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        TeamLayout layout = TeamLayout.of(team);
        placePieces(pieces, team, layout.backRow(), List.of(1, 9), Chariot::new);
        placePieces(pieces, team, layout.backRow(), List.of(4, 6), Guard::new);
        placePieces(pieces, team, layout.generalRow(), List.of(5), General::new);
        placePieces(pieces, team, layout.cannonRow(), List.of(2, 8), Cannon::new);
        placePieces(pieces, team, layout.soldierRow(), List.of(1, 3, 5, 7, 9), Soldier::new);
    }

    private void placePieces(Map<Position, Piece> pieces, Team team, int row,
                             List<Integer> columns, Function<Team, Piece> factory) {
        for (int column : columns) {
            pieces.put(new Position(row, column), factory.apply(team));
        }
    }

    protected abstract void setVariablePieces(Map<Position, Piece> pieces, Team team);
}
