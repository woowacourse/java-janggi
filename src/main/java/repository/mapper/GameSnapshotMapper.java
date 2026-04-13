package repository.mapper;

import domain.coordination.Coordination;
import domain.game.GameState;
import domain.game.JanggiGame;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.snapshot.GameSnapshot;
import repository.snapshot.GameStatus;
import repository.snapshot.PieceSnapshot;

public class GameSnapshotMapper {

    private static final int COLUMN_MIN = 1;
    private static final int COLUMN_MAX = 9;
    private static final int ROW_MIN = 1;
    private static final int ROW_MAX = 10;
    private static final int COLUMN_INDEX = 0;
    private static final int ROW_INDEX = 1;

    private final PieceMapper pieceMapper = new PieceMapper();

    public GameSnapshot from(Long gameId, JanggiGame game) {
        return new GameSnapshot(gameId, game.turn(), gameStatus(game), pieceSnapshots(game.snapshot()));
    }

    public JanggiGame toGame(GameSnapshot snapshot) {
        return JanggiGame.restore(new GameState(snapshot.currentTurn(), restoredBoard(snapshot)));
    }

    private Map<Coordination, Piece> createEmptyBoard() {
        Map<Coordination, Piece> board = new HashMap<>();
        for (int row = ROW_MIN; row <= ROW_MAX; row++) {
            for (int column = COLUMN_MIN; column <= COLUMN_MAX; column++) {
                board.put(Coordination.of(column, row), new EmptyPiece(Team.NONE));
            }
        }
        return board;
    }

    private GameStatus gameStatus(JanggiGame game) {
        if (game.isGameEnd()) {
            return GameStatus.FINISHED;
        }
        return GameStatus.IN_PROGRESS;
    }

    private List<PieceSnapshot> pieceSnapshots(GameState gameState) {
        return gameState.board().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .sorted(pieceOrder())
                .map(this::toPieceSnapshot)
                .toList();
    }

    private Comparator<Map.Entry<Coordination, Piece>> pieceOrder() {
        return Comparator.comparing(
                        (Map.Entry<Coordination, Piece> entry) -> entry.getKey().coordination().get(ROW_INDEX))
                .thenComparing(entry -> entry.getKey().coordination().get(COLUMN_INDEX));
    }

    private PieceSnapshot toPieceSnapshot(Map.Entry<Coordination, Piece> entry) {
        List<Integer> coordination = entry.getKey().coordination();
        Piece piece = entry.getValue();
        return new PieceSnapshot(
                coordination.get(COLUMN_INDEX),
                coordination.get(ROW_INDEX),
                pieceMapper.toPieceType(piece),
                piece.team()
        );
    }

    private Map<Coordination, Piece> restoredBoard(GameSnapshot snapshot) {
        Map<Coordination, Piece> board = createEmptyBoard();
        snapshot.pieces().forEach(pieceSnapshot -> board.put(positionOf(pieceSnapshot), restoredPiece(pieceSnapshot)));
        return board;
    }

    private Coordination positionOf(PieceSnapshot pieceSnapshot) {
        return Coordination.of(pieceSnapshot.column(), pieceSnapshot.row());
    }

    private Piece restoredPiece(PieceSnapshot pieceSnapshot) {
        return pieceMapper.toPiece(pieceSnapshot.pieceType(), pieceSnapshot.team());
    }
}
