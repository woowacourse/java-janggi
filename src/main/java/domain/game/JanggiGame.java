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

    public JanggiGame(Board board, MoveStrategyRegistry moveStrategyRegistry) {
        this.board = board;
        this.moveStrategyRegistry = moveStrategyRegistry;
    }

    public static JanggiGame init(ElephantSetup choElephantSetup, ElephantSetup hanElephantSetup) {
        return new JanggiGame(
                BoardInitializer.init(
                        choElephantSetup,
                        hanElephantSetup
                ),
                MoveStrategyRegistry.init()
        );
    }

    public List<Position> getPiecePositionsFor(Team team) {
        return board.getPiecePositionsFor(team);
    }

    public List<Position> getMovablePositions(Position from) {
        Piece piece = board.getPieceAt(from);
        MoveStrategy moveStrategy = moveStrategyRegistry.getMoveStrategyBy(piece);
        return moveStrategy.getMovablePositions(board, from);
    }

    public void move(Position from, Position to) {
        board.move(from, to);
    }

    public Board getBoard() {
        return board;
    }

    public Piece getPieceAt(Position position) {
        return board.getPieceAt(position);
    }
}
