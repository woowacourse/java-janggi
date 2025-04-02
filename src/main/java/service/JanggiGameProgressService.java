package service;

import db.repository.JanggiGameRepository;
import janggiGame.JanggiGame;
import janggiGame.piece.Piece;
import janggiGame.position.Position;

public class JanggiGameProgressService {
    private final Long gameId;
    private final JanggiGame game;
    private final JanggiGameRepository repository;

    public JanggiGameProgressService(Long gameId, JanggiGameRepository repository) {
        this.gameId = gameId;
        this.repository = repository;
        this.game = repository.load(gameId);
    }

    public void takeTurn(Position origin, Position destination) {
        Piece captured = game.getPieces().get(destination);
        game.takeTurn(origin, destination);

        if (checkFinished()) {
            repository.markAsFinished(gameId);
            return;
        }

        if (captured != null) {
            repository.deletePieceAt(gameId, destination);
        }

        repository.updatePiecePosition(gameId, origin, destination);
        repository.updateTurn(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public void skipTurn() {
        game.skipTurn();

        if (checkFinished()) {
            repository.markAsFinished(gameId);
            return;
        }

        repository.updateTurn(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public void undoTurn() {
        game.undoTurn();
        repository.updateTurn(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public boolean isFinished() {
        return game.isFinished();
    }

    public JanggiGame getGame() {
        return game;
    }

    private boolean checkFinished() {
        return game.isFinished();
    }
}
