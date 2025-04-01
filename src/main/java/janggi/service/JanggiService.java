package janggi.service;


import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.domain.JanggiGame;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameResultDto;
import janggi.dto.MoveCommandDto;
import janggi.entity.BoardPieceEntity;
import janggi.entity.GameRoomEntity;
import janggi.view.PieceTypeName;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Map;

public class JanggiService {
    private final BoardPieceDao boardPieceDao;
    private final GameRoomDao gameRoomDao;
    private final JanggiGame janggiGame;
    private final int roomId;

    public JanggiService(Connection connection, JanggiGame janggiGame, int roomId) {
        this.boardPieceDao = new BoardPieceDao(connection);
        this.gameRoomDao = new GameRoomDao(connection);
        this.janggiGame = janggiGame;
        this.roomId = roomId;
    }

    public void movePiece(MoveCommandDto commandDto) {
        Position source = createPosition(commandDto.sourceRow(), commandDto.sourceCol());
        Position destination = createPosition(commandDto.destinationRow(), commandDto.destinationCol());
        PieceType pieceType = PieceTypeName.getTypeFrom(commandDto.pieceName());

        janggiGame.move(pieceType, source, destination);
        updateMoveResult(source, destination);
        updateGameRoom(janggiGame.getTurnColor(), janggiGame.getTeamScore());
    }

    private Position createPosition(char rowInput, char colInput) {
        int rowInt = Character.getNumericValue(rowInput);
        int colInt = Character.getNumericValue(colInput);

        return Position.of(rowInt, colInt);
    }

    public void updateMoveResult(Position source, Position destination) {
        boardPieceDao.deleteByPosition(roomId, destination.rowValue(), destination.columnValue());

        BoardPieceEntity sourcePieceEntity = boardPieceDao.selectByPosition(roomId, source.rowValue(), source.columnValue())
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 있는 기물이 없습니다."));

        sourcePieceEntity.updatePosition(destination.rowValue(), destination.columnValue());
        boardPieceDao.update(sourcePieceEntity);
    }

    public void updateGameRoom(TeamColor teamColor, Map<TeamColor, Integer> teamScore) {
        int redScore = teamScore.getOrDefault(TeamColor.RED, 0);
        int blueScore = teamScore.getOrDefault(TeamColor.BLUE, 0);

        GameRoomEntity gameRoomEntity = gameRoomDao.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 GameRoom이 존재하지 않습니다, roomId: " + roomId));

        String turnColor = teamColor.name();
        LocalDateTime lastUpdatedTime = LocalDateTime.now();
        gameRoomEntity.updateRoom(turnColor, redScore, blueScore, lastUpdatedTime);
        gameRoomDao.updateGameRoom(gameRoomEntity);
    }

    public void finishGame() {
        GameRoomEntity gameRoomEntity = gameRoomDao.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 GameRoom이 존재하지 않습니다, roomId: " + roomId));

        String winColor = janggiGame.getTurnColor().name();
        LocalDateTime endTime = LocalDateTime.now();
        gameRoomEntity.updateToFinish(true, winColor, endTime);
        gameRoomDao.finishGame(gameRoomEntity);
    }

    public GameResultDto getGameResult() {
        TeamColor winColor = janggiGame.getTurnColor();

        Map<TeamColor, Integer> teamScore = janggiGame.getTeamScore();
        int redScore = teamScore.getOrDefault(TeamColor.RED, 0);
        int blueScore = teamScore.getOrDefault(TeamColor.BLUE, 0);

        return new GameResultDto(winColor.name(), redScore, blueScore);
    }

    public PlayingBoard getBoard() {
        return janggiGame.getPlayingBoard();
    }

    public boolean isGameFinished() {
        return janggiGame.isFinished();
    }

    public TeamColor getCurrentTurn() {
        return janggiGame.getTurnColor();
    }
}
