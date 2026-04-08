import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import domain.game.Game;
import domain.game.Status;
import domain.vo.Position;
import entity.GameEntity;
import entity.PieceEntity;
import repository.GameDao;
import repository.PieceDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public List<GameEntity> findAllGames() {
        return gameDao.findAll();
    }

    public Game loadGame(GameEntity gameEntity, Status status) {
        List<PieceEntity> savedPieces = pieceDao.findAllByGameId(gameEntity.getId());
        Board board = convertPieceEntitiesToBoard(savedPieces);

        Team team = Team.valueOf(gameEntity.getCurrentTurn());
        return Game.loadGame(board, team, status);
    }

    public void moveAndSave(Game game, Long gameId, Position from, Position to) {
        boolean hasTargetPiece = game.getBoard().findPieceByPosition(to).isPresent();
        game.tryToMove(from, to);
        updatePiecePositions(gameId, hasTargetPiece, from, to);
        gameDao.update(gameId, game.getCurrentTeam().name(), game.getStatus().toString());
    }

    public GameEntity saveGame(Game game) {
        GameEntity gameEntity = gameDao.save(
                new GameEntity(game.getCurrentTeam().name(), game.getStatus().toString())
        );

        List<PieceEntity> pieces = convertBoardToPieceEntities(gameEntity.getId(), game.getBoard());
        pieceDao.saveAll(pieces);
        return gameEntity;
    }

    public void updatePiecePositions(Long gameId, boolean hasTargetPiece, Position from, Position to) {
        if (hasTargetPiece) {
            pieceDao.deleteByPosition(gameId, to.getRow(), to.getCol());
        }
        pieceDao.updatePosition(gameId,
                from.getRow(), from.getCol(), to.getRow(), to.getCol());
    }

    private Board convertPieceEntitiesToBoard(List<PieceEntity> findPieces) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceEntity piece : findPieces) {
            Position position = Position.of(piece.getPositionRow(), piece.getPositionCol());
            Type type = Type.valueOf(piece.getPieceType());
            board.put(position, Piece.of(Team.valueOf(piece.getTeam()), type, type.createStrategy()));
        }
        return Board.of(board);
    }

    private List<PieceEntity> convertBoardToPieceEntities(Long gameId, Board board) {
        List<PieceEntity> pieces = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                Position position = Position.of(row, col);
                board.findPieceByPosition(position)
                        .ifPresent(piece -> pieces.add(PieceEntity.from(gameId, piece, position)));
            }
        }
        return pieces;
    }
}
