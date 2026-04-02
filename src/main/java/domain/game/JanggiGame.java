package domain.game;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.board.Position;
import domain.movestrategy.MoveStrategy;
import domain.movestrategy.MoveStrategyRegistry;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;

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

    public List<Position> getPiecePositionsFor(final Team team) {
        return board.getPiecePositionsFor(team);
    }

    public List<Position> getMovablePositions(final Position from) {
        Piece piece = board.getPieceAt(from);
        MoveStrategy moveStrategy = moveStrategyRegistry.getMoveStrategyBy(piece);
        return moveStrategy.getMovablePositions(board, from);
    }

    public void move(final Position from, final Position to) {
        board.move(from, to);
    }

    public Board getBoard() {
        return board;
    }

    public Piece getPieceAt(final Position position) {
        return board.getPieceAt(position);
    }
}
