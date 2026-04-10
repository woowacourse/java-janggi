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
import domain.state.ChoPlayingState;
import domain.state.FinishedState;
import domain.state.GameState;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private static final GameState FIRST_GAME_STATE = new ChoPlayingState();

    private final Board board;
    private final MoveStrategyRegistry moveStrategyRegistry;
    private GameState gameState;

    public JanggiGame(final Board board, final MoveStrategyRegistry moveStrategyRegistry, final GameState gameState) {
        this.board = board;
        this.moveStrategyRegistry = moveStrategyRegistry;
        this.gameState = gameState;
    }

    public static JanggiGame init(
            final ElephantSetup choElephantSetup,
            final ElephantSetup hanElephantSetup
    ) {
        return new JanggiGame(
                BoardInitializer.init(
                        choElephantSetup,
                        hanElephantSetup
                ),
                MoveStrategyRegistry.init(),
                FIRST_GAME_STATE
        );
    }

    public static JanggiGame of(
            final Board board,
            final GameState gameState
    ) {
        return new JanggiGame(
                board,
                MoveStrategyRegistry.init(),
                gameState
        );
    }

    public List<Position> getCurrentPlayerPiecePositions() {
        return gameState.getPiecePositions(board);
    }

    public List<Position> getMovablePositions(final Position from) {
        PieceType pieceType = board.getPieceType(from);
        MoveStrategy moveStrategy = moveStrategyRegistry.getMoveStrategy(pieceType);
        return moveStrategy.getMovablePositions(board, from);
    }

    public void move(final Position from, final Position to) {
        board.move(from, to);
        gameState = nextState();
    }

    private GameState nextState() {
        if (board.isOnlyOneGeneralRemaining()) {
            return new FinishedState(gameState.getTeam());
        }
        return gameState.nextTurn();
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

    public boolean isFinished() {
        return gameState.isFinished();
    }

    public Team getWinnerTeam() {
        if (!isFinished()) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }
        return gameState.getTeam();
    }

    public double getScoreBy(Team team) {
        return board.getScoreBy(team);
    }

    public Team getCurrentTeam() {
        return gameState.getTeam();
    }
}
