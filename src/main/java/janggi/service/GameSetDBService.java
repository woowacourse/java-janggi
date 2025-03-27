package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameRoomDao;
import janggi.dao.PiecePositionDao;
import janggi.domain.JanggiGame;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import janggi.dto.PiecePositionDto;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameSetDBService {
    private final PiecePositionDao piecePositionDao;
    private final GameRoomDao gameRoomDao;
    private final BoardDao boardDao;

    public GameSetDBService(Connection connection) {
        this.piecePositionDao = new PiecePositionDao(connection);
        this.gameRoomDao = new GameRoomDao(connection);
        this.boardDao = new BoardDao(connection);
    }

    public List<GameRoomDto> getAllPlayingRooms() {
        return gameRoomDao.findPlayingGameRooms();
    }

    public int createNewBoardAndGetId() {
        boardDao.createBoard();
        return boardDao.findRecentlyBoardId()
                .orElseThrow(() -> new IllegalArgumentException("가장 최근의 BoardId를 찾을 수 없습니다."));
    }

    public int createNewGameRoomAndGetId(int boardId, TeamColor turnColor) {
        gameRoomDao.saveNewRoom(boardId, turnColor);
        return gameRoomDao.findRecentlyRoomId()
                .orElseThrow(() -> new IllegalArgumentException("가장 최근의 roomId를 찾을 수 없습니다."));
    }

    public int getBoardIdByRoom(int roomId) {
        Optional<Integer> boardId = gameRoomDao.findBoardIdByRoomId(roomId);
        return boardId.orElseThrow(() -> new IllegalArgumentException("해당 GameRoom의 Board를 찾을 수 없습니다."));
    }

    public void saveInitialBoard(int boardId, Map<Position, Piece> board) {
        piecePositionDao.saveAllInBoard(boardId, board);
    }

    public JanggiGame getGameByRoomId(int gameRoomId) {
        GameRoomDto gameRoomDto = gameRoomDao.findRoomFromId(gameRoomId)
                .orElseThrow(() -> new IllegalStateException("해당 id의 게임이 존재하지 않습니다, gameRoomId: " + gameRoomId));

        TeamColor teamColor = TeamColor.valueOf(TeamColor.class, gameRoomDto.turnColor());
        PlayingBoard playingBoard = getBoardById(gameRoomId);

        State gameState = State.from(teamColor, playingBoard);
        Map<TeamColor, Integer> teamScore = new HashMap<>();
        teamScore.put(TeamColor.RED, gameRoomDto.redScore());
        teamScore.put(TeamColor.BLUE, gameRoomDto.blueScore());

        return new JanggiGame(gameState, teamScore);
    }

    public PlayingBoard getBoardById(int boardId) {
        List<PiecePositionDto> piecePositionDtos = piecePositionDao.selectAllInBoard(boardId);

        Map<Position, Piece> board = new HashMap<>();
        for (PiecePositionDto dto : piecePositionDtos) {
            Position position = Position.of(dto.position_row(), dto.position_col());

            TeamColor pieceColor = TeamColor.valueOf(TeamColor.class, dto.piece_color());
            PieceType pieceType = PieceType.valueOf(PieceType.class, dto.piece_type());
            Piece piece = new Piece(pieceColor, pieceType);

            board.put(position, piece);
        }
        return new PlayingBoard(board);
    }
}
