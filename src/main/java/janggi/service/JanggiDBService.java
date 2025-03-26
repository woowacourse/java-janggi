package janggi.service;


import janggi.dao.BoardDao;
import janggi.dao.GameStateDao;
import janggi.domain.JanggiGame;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.gameState.State;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameStateDto;
import janggi.dto.PiecePositionDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiDBService {
    private final BoardDao boardDao = new BoardDao();
    private final GameStateDao gameStateDao = new GameStateDao();

    public void saveInitialBoard(Map<Position, Piece> board) {
        boardDao.deleteBoard();
        boardDao.saveBoard(board);
    }

    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        boardDao.updatePiecePosition(source, destination, pieceType, teamColor);
    }

    public void saveStartSate(TeamColor teamColor) {
        gameStateDao.saveStartGameState(teamColor);
    }

    public void updateGameState(TeamColor teamColor, Map<TeamColor, Integer> teamScore) {
        int gameId = gameStateDao.findInProgressGameId()
                .orElseThrow(() -> new IllegalStateException("진행 중인 게임이 없습니다."));

        int redScore = teamScore.getOrDefault(TeamColor.RED, 0);
        int blueScore = teamScore.getOrDefault(TeamColor.BLUE, 0);

        gameStateDao.updateGameState(gameId, teamColor, redScore, blueScore);
    }

    public void finishGame(TeamColor winnerColor) {
        int gameId = gameStateDao.findInProgressGameId()
                .orElseThrow(() -> new IllegalStateException("진행 중인 게임이 없습니다."));

        gameStateDao.finishGame(gameId, winnerColor);
    }

    public PlayingBoard selectBoard(int gameId) {
        List<PiecePositionDto> piecePositionDtos = boardDao.selectBoard(gameId);

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

    public Optional<Integer> getInProgressGameId() {
        return gameStateDao.findInProgressGameId();
    }

    public JanggiGame getInProgressGame(int gameId) {
        GameStateDto gameStateDto = gameStateDao.findGameStateFromId(gameId)
                .orElseThrow(() -> new IllegalStateException("해당 id의 게임이 존재하지 않습니다, gameId: " + gameId));

        TeamColor teamColor = TeamColor.valueOf(TeamColor.class, gameStateDto.turnColor());
        PlayingBoard playingBoard = selectBoard(gameId);

        State gameState = State.from(teamColor, playingBoard);
        Map<TeamColor, Integer> teamScore = new HashMap<>();
        teamScore.put(TeamColor.RED, gameStateDto.redScore());
        teamScore.put(TeamColor.BLUE, gameStateDto.blueScore());

        return new JanggiGame(gameState, teamScore);
    }
}
