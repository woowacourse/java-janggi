package janggi.service;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.game.Game;
import janggi.domain.game.GameRoom;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PlacedPiece;
import janggi.dto.MoveResultDto;
import janggi.repository.GameRoomRepository;
import janggi.repository.PieceRepository;
import java.util.List;
import java.util.Map;

public class GameService {

    private final GameRoomRepository gameRoomRepository;
    private final PieceRepository pieceRepository;

    public GameService(GameRoomRepository gameRoomRepository, PieceRepository pieceRepository) {
        this.gameRoomRepository = gameRoomRepository;
        this.pieceRepository = pieceRepository;
    }

    public Game createGame(Board board) {
        GameRoom gameRoom = GameRoom.create();
        long gameRoomId = gameRoomRepository.save(gameRoom);

        board.getBoard().forEach((position, piece) -> {
            PlacedPiece placedPiece = new PlacedPiece(
                    gameRoomId, piece.campType(), piece.pieceRule(),
                    position.row(), position.column()
            );
            pieceRepository.save(placedPiece);
        });
        return loadGame(gameRoomId);
    }

    public Game loadGame(long gameRoomId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId);
        Map<Position, Piece> pieces = pieceRepository.findByGameRoomId(gameRoomId);
        return new Game(
                gameRoom.getGameRoomId(),
                gameRoom.getCurrentTurn(),
                gameRoom.getGameStatus(),
                gameRoom.getStartAt(),
                gameRoom.getEndAt(),
                gameRoom.getLastUpdatedAt(),
                Board.restore(pieces)
        );
    }

    public List<Long> findPlayingGameRoomIds() {
        return gameRoomRepository.findAllByGameStatus(GameStatus.PLAYING);
    }

    public void progressMove(long gameRoomId, MoveResultDto moveResultDto) {
        if (moveResultDto.captured()) {
            removeCapturedPiece(gameRoomId, moveResultDto);
        }
        movePiece(gameRoomId, moveResultDto);
    }

    private void removeCapturedPiece(long gameId, MoveResultDto moveResultDto) {
        PlacedPiece capturedPiece = pieceRepository.findByGameIdAndPosition(
                gameId,
                moveResultDto.destination().row(),
                moveResultDto.destination().column()
        );
        pieceRepository.delete(capturedPiece);
    }

    private void movePiece(long gameRoomId, MoveResultDto moveResultDto) {
        PlacedPiece placedPiece = pieceRepository.findByGameIdAndPosition(
                gameRoomId,
                moveResultDto.source().row(),
                moveResultDto.source().column()
        );
        placedPiece.moveTo(moveResultDto.destination().row(), moveResultDto.destination().column());
        pieceRepository.update(placedPiece);
    }

    public void changeTurn(Game game) {
        game.changeTurn();
        GameRoom gameRoom = GameRoom.from(game);
        gameRoomRepository.update(gameRoom);
    }

    public void finishGame(Game game) {
        game.finish();
        GameRoom gameRoom = GameRoom.from(game);
        gameRoomRepository.update(gameRoom);
    }
}
