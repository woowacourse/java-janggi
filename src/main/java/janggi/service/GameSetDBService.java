package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameRoomDao;
import janggi.domain.JanggiGame;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.BoardDto;
import janggi.dto.GameRoomDto;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSetDBService {
    private final BoardDao boardDao;
    private final GameRoomDao gameRoomDao;

    public GameSetDBService(Connection connection) {
        this.gameRoomDao = new GameRoomDao(connection);
        this.boardDao = new BoardDao(connection);
    }

    public List<GameRoomDto> getAllPlayingRooms() {
        return gameRoomDao.findPlayingGameRooms();
    }

    public int createNewGameRoomAndGetId(TeamColor turnColor) {
        gameRoomDao.saveNewRoom(turnColor);
        return gameRoomDao.findRecentlyRoomId()
                .orElseThrow(() -> new IllegalArgumentException("가장 최근의 roomId를 찾을 수 없습니다."));
    }

    public void saveInitialBoard(int roomId, Map<Position, Piece> board) {
        boardDao.saveBoardInRoom(roomId, board);
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
        List<BoardDto> boardDtos = boardDao.selectBoardById(boardId);

        Map<Position, Piece> board = new HashMap<>();
        for (BoardDto dto : boardDtos) {
            Position position = Position.of(dto.positionRow(), dto.positionCol());

            TeamColor pieceColor = TeamColor.valueOf(TeamColor.class, dto.pieceColor());
            PieceType pieceType = PieceType.valueOf(PieceType.class, dto.pieceType());
            Piece piece = new Piece(pieceColor, pieceType);

            board.put(position, piece);
        }
        return new PlayingBoard(board);
    }
}
