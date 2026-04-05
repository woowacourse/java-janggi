package janggi.infra;

import janggi.domain.board.Board;
import janggi.domain.board.BoardRepository;
import janggi.domain.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.status.GameStatusFactory;
import janggi.domain.status.Team;
import janggi.infra.dao.GameRoomDao;
import janggi.infra.dao.PiecesDao;
import janggi.infra.dto.GameRoomData;
import janggi.infra.dto.PieceData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcBoardRepository implements BoardRepository {

    private final GameRoomDao roomDao;
    private final PiecesDao piecesDao;

    public JdbcBoardRepository(GameRoomDao roomDao, PiecesDao piecesDao) {
        this.roomDao = roomDao;
        this.piecesDao = piecesDao;
    }

    @Override
    public long save(JanggiGame game) {
        long roomId = roomDao.save(GameRoomData.from(game));
        Map<Point, Piece> pieces = game.getBoardStatus();
        List<PieceData> data = new ArrayList<>();
        for (Point point : pieces.keySet()) {
            Piece piece = pieces.get(point);
            data.add(new PieceData(piece.getType().name(), piece.getTeam().name(), point.getRow(), point.getColumn()));
        }
        piecesDao.save(roomId, data);
        return roomId;
    }

    @Override
    public void update(long roomId, Point from, Point to, JanggiGame game) {
        roomDao.update(roomId, GameRoomData.from(game));
        piecesDao.delete(roomId, to.getRow(), to.getColumn());
        piecesDao.update(roomId, from.getRow(), from.getColumn(), to.getRow(), to.getColumn());
    }

    @Override
    public Optional<JanggiGame> findJanggiGameByRoomId(long roomId) {
        Optional<GameRoomData> roomData = roomDao.findRoomById(roomId);
        if (roomData.isEmpty()) {
            return Optional.empty();
        }
        List<PieceData> pieceDatas = piecesDao.findAllByRoomId(roomId);
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieceDatas.forEach(pieceData -> {
            PieceType type = PieceType.valueOf(pieceData.pieceName());
            Team team = Team.valueOf(pieceData.teamName());
            Point point = Point.of(pieceData.column(), pieceData.row());
            Piece piece = PieceFactory.createPiece(team, type);
        pieces.put(point, piece);
        });
        Board board = new Board(pieces);
        return Optional.of(new JanggiGame(board, GameStatusFactory.create(Team.valueOf(roomData.get().currentTurn()))));
    }
}
