package janggi.service;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.game.Game;
import janggi.domain.game.GameRoom;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.PlacedPiece;
import janggi.dto.MoveResultDto;
import janggi.repository.GameRoomRepository;
import janggi.repository.PieceRepository;
import java.util.List;

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
        List<PlacedPiece> pieces = pieceRepository.findAllByGameRoomId(gameRoomId);
        Board board = Board.restore(pieces);
        return new Game(gameRoom, board);
    }

    public List<Long> findPlayingGameRoomIds() {
        return gameRoomRepository.findAllByGameStatus(GameStatus.PLAYING);
    }

    public void move(Game game, Position source, Position destination) {
        MoveResultDto moveResultDto = game.move(source, destination);

        if (moveResultDto.captured()) {
            removeCapturedPiece(game.getGameRoomId(), moveResultDto);
        }
        movePiece(game.getGameRoomId(), moveResultDto);

        updateGameStatus(game);
    }


    private void removeCapturedPiece(long gameRoomId, MoveResultDto moveResultDto) {
        PlacedPiece capturedPiece = pieceRepository.findByGameRoomIdAndPosition(
                gameRoomId,
                moveResultDto.destination().row(),
                moveResultDto.destination().column()
        );
        pieceRepository.delete(capturedPiece);
    }

    private void movePiece(long gameRoomId, MoveResultDto moveResultDto) {
        PlacedPiece placedPiece = pieceRepository.findByGameRoomIdAndPosition(
                gameRoomId,
                moveResultDto.source().row(),
                moveResultDto.source().column()
        );
        placedPiece.moveTo(moveResultDto.destination().row(), moveResultDto.destination().column());
        pieceRepository.update(placedPiece);
    }

    private void updateGameStatus(Game game) {
        GameRoom gameRoom = GameRoom.from(game);
        gameRoomRepository.update(gameRoom);
    }
}
