package service;

import domain.Game;
import domain.coordinate.Position;
import repository.PieceRepository;

public class PieceService {
    private final PieceRepository pieceRepository;

    public PieceService(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    public void movePiece(Game game, Position start, Position destination) {
        try {
            game.move(start, destination);
            pieceRepository.updatePiecesPosition(game.getBoardSnapshotMap());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
