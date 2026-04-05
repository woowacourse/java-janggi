package domain.game;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.board.Position;
import domain.movestrategy.MoveStrategy;
import domain.movestrategy.MoveStrategyRegistry;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final MoveStrategyRegistry moveStrategyRegistry;

    public JanggiGame(final Board board, final MoveStrategyRegistry moveStrategyRegistry) {
        this.board = board;
        this.moveStrategyRegistry = moveStrategyRegistry;
    }

    public static JanggiGame init(final ElephantSetup choElephantSetup, final ElephantSetup hanElephantSetup) {
        return new JanggiGame(
                BoardInitializer.init(
                        choElephantSetup,
                        hanElephantSetup
                ),
                MoveStrategyRegistry.init()
        );
    }

    public List<Position> getPositionsBy(final Team team) {
        return board.getPositionsByTeam(team);
    }

    public List<Position> getMovablePositions(final Position from) {
        PieceType pieceType = board.getPieceType(from);
        MoveStrategy moveStrategy = moveStrategyRegistry.getMoveStrategy(pieceType);
        return moveStrategy.getMovablePositions(board, from);
    }

    public void move(final Position from, final Position to) {
        board.move(from, to);
    }

    public PieceType getPieceType(final Position position) {
        return board.getPieceType(position);
    }

    public Team getTeam(final Position position) {
        return board.getTeam(position);
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }
}
