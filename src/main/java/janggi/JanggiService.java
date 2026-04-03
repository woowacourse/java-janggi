package janggi;

import janggi.dao.GameRoom;
import janggi.dao.Piece;
import janggi.db.TransactionManager;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;
import java.util.List;

public class JanggiService {
    private final TransactionManager transactionManager;
    private final GameRoom gameRoom;
    private final Piece piece;

    public JanggiService(TransactionManager transactionManager, GameRoom gameRoom, Piece piece) {
        this.transactionManager = transactionManager;
        this.gameRoom = gameRoom;
        this.piece = piece;
    }

    public List<GameResponseDto> getEntireGame() {
        return gameRoom.findAllGames();
    }

    public int addGameData(GameDto gameDto, List<PieceDto> pieceDtos) {
        return transactionManager.sync((connection) -> {
            int gameId = gameRoom.insertGame(connection, gameDto);
            piece.updatePieces(connection, gameId, pieceDtos);

            return gameId;
        });
    }

    public void removeGame(int id) {
        transactionManager.sync((connection) -> {
            gameRoom.removeGame(connection, id);
        });
    }

    public List<PieceDto> getPieceInitInfos(int gameId) {
        return piece.getAllPieces(gameId);
    }


    public void movePiece(int gameId, Position start, Position end, Side side, PieceType pieceType, TurnDto turnDto) {
        transactionManager.sync(connection -> {
            gameRoom.updateGameTurn(connection, gameId, turnDto);
            piece.deletePiece(connection, gameId, start.getX(), start.getY());

            PieceDto pieceDto = new PieceDto(end.getX(), end.getY(), pieceType.getName(), side.getName());
            piece.updatePiece(connection, gameId, pieceDto);
        });
    }
}
