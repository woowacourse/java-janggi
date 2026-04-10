package repository;

import dao.BoardPieceDao;
import dao.BoardPieceRawData;
import dao.GameRoomDao;
import dao.GameRoomRawData;
import db.PieceTypeMapper;
import domain.board.Board;
import domain.game.GameRecord;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final GameRoomDao gameRoomDao;
    private final BoardPieceDao boardPieceDao;

    public GameRepository(GameRoomDao gameRoomDao, BoardPieceDao boardPieceDao) {
        this.gameRoomDao = gameRoomDao;
        this.boardPieceDao = boardPieceDao;
    }

    public void createGame(String roomName, JanggiGame game) {
        long roomId = gameRoomDao.save(
                roomName,
                game.currentTurn().name(),
                game.getStatus().name(),
                game.getRecord().consecutivePassCount()
        );
        game.assignId(roomId);
        boardPieceDao.saveAll(roomId, toRawPieces(game.getBoard()));
    }

    public Optional<JanggiGame> loadGame(long roomId) {
        return gameRoomDao.findById(roomId).map(this::assemble);
    }

    private JanggiGame assemble(GameRoomRawData roomData) {
        List<BoardPieceRawData> pieceData = boardPieceDao.findByGameRoomId(roomData.id());
        Board board = toBoard(pieceData);
        Turn turn = Turn.of(Team.valueOf(roomData.currentTurn()));
        GameRecord record = new GameRecord(roomData.consecutivePassCount());
        GameStatus status = GameStatus.valueOf(roomData.status());
        return JanggiGame.restore(roomData.id(), turn, board, record, status);
    }

    public void saveGame(JanggiGame game) {
        long roomId = game.getId();
        gameRoomDao.update(
                roomId,
                game.currentTurn().name(),
                game.getStatus().name(),
                game.getRecord().consecutivePassCount()
        );
        boardPieceDao.deleteByGameRoomId(roomId);
        boardPieceDao.saveAll(roomId, toRawPieces(game.getBoard()));
    }

    public List<GameRoomRawData> listRooms() {
        return gameRoomDao.findAll();
    }

    private List<BoardPieceRawData> toRawPieces(Board board) {
        return Position.allPositions().stream()
                .filter(position -> board.pieceAt(position).isNotEmpty())
                .map(position -> {
                    Piece piece = board.pieceAt(position);
                    return new BoardPieceRawData(
                            position.row(),
                            position.column(),
                            PieceTypeMapper.toTypeName(piece),
                            piece.getTeam().name()
                    );
                })
                .toList();
    }

    private Board toBoard(List<BoardPieceRawData> pieceData) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (BoardPieceRawData data : pieceData) {
            Position position = new Position(data.rowPos(), data.colPos());
            Piece piece = PieceTypeMapper.toPiece(data.pieceType(), Team.valueOf(data.team()));
            pieces.put(position, piece);
        }
        return new Board(pieces);
    }
}
