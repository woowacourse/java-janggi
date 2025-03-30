package janggi.service;

import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.domain.JanggiGame;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import janggi.entity.BoardPieceEntity;
import janggi.entity.GameRoomEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSetService {
    private final BoardPieceDao boardPieceDao;
    private final GameRoomDao gameRoomDao;

    public GameSetService(Connection connection) {
        this.gameRoomDao = new GameRoomDao(connection);
        this.boardPieceDao = new BoardPieceDao(connection);
    }

    public List<GameRoomDto> getAllPlayingRooms() {

        List<GameRoomEntity> playingGameRooms = gameRoomDao.findPlayingGameRooms();
        return playingGameRooms.stream()
                .map(entity -> GameRoomDto.createForShowRooms(
                        entity.getId(), entity.getTurnColor(), entity.getStartTime(), entity.getLastUpdated()
                ))
                .toList();
    }

    public int createNewGameRoomAndGetId(TeamColor turnColor) {
        GameRoomEntity newGameRoomEntity = GameRoomEntity.createNewGameRoom(turnColor.name());
        gameRoomDao.saveNewRoom(newGameRoomEntity);
        return gameRoomDao.findRecentlyRoomId()
                .orElseThrow(() -> new IllegalArgumentException("가장 최근의 roomId를 찾을 수 없습니다."));
    }

    public void saveInitialBoard(int roomId, Map<Position, Piece> board) {
        boardPieceDao.save(roomId, board);
    }

    public JanggiGame getGameByRoomId(int gameRoomId) {
        GameRoomEntity gameRoomEntity = gameRoomDao.findById(gameRoomId)
                .orElseThrow(() -> new IllegalStateException("해당 id의 게임이 존재하지 않습니다, gameRoomId: " + gameRoomId));

        TeamColor teamColor = TeamColor.valueOf(TeamColor.class, gameRoomEntity.getTurnColor());
        PlayingBoard playingBoard = getBoardById(gameRoomId);

        State gameState = State.from(teamColor, playingBoard);
        Map<TeamColor, Integer> teamScore = new HashMap<>();
        teamScore.put(TeamColor.RED, gameRoomEntity.getRedScore());
        teamScore.put(TeamColor.BLUE, gameRoomEntity.getBlueScore());

        return new JanggiGame(gameState, teamScore);
    }

    public PlayingBoard getBoardById(int boardId) {
        List<BoardPieceEntity> boardPieceEntities = boardPieceDao.selectById(boardId);

        Map<Position, Piece> board = new HashMap<>();
        for (BoardPieceEntity entity : boardPieceEntities) {
            Position position = Position.of(entity.getPositionRow(), entity.getPositionCol());

            TeamColor pieceColor = TeamColor.valueOf(TeamColor.class, entity.getPieceColor());
            PieceType pieceType = PieceType.valueOf(PieceType.class, entity.getPieceType());
            Piece piece = new Piece(pieceColor, pieceType);

            board.put(position, piece);
        }
        return new PlayingBoard(board);
    }
}
