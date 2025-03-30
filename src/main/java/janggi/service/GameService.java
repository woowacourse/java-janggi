package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.domain.game.Board;
import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameService(final GameDao gameDao, final PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public Game loadGameByGameId(final int gameId) {
        GameDto gameDto = gameDao.getGameById(gameId);
        Team turn = Team.valueOf(gameDto.turn());
        List<PieceDto> pieceDtos = pieceDao.getPiecesByGameId(gameId);
        Board board = createBoardFrom(pieceDtos);
        return new Game(turn, board);
    }

    private Board createBoardFrom(final List<PieceDto> pieceDtos) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Position position = createPositionFrom(pieceDto);
            Piece piece = createPieceFrom(pieceDto);
            pieces.put(position, piece);
        }
        return new Board(pieces);
    }

    private Position createPositionFrom(final PieceDto pieceDto) {
        int columnValue = pieceDto.colNum();
        int rowValue = pieceDto.rowNum();
        return new Position(Column.of(columnValue), Row.of(rowValue));
    }

    private Piece createPieceFrom(final PieceDto pieceDto) {
        Team pieceTeam = Team.valueOf(pieceDto.team());
        Type type = Type.valueOf(pieceDto.pieceType());
        return type.getConstructor().apply(pieceTeam);
    }
}
