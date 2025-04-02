package service;

import db.dao.JanggiGameDao.GameDto;
import db.repository.JanggiGameRepository;
import janggiGame.JanggiGame;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import java.util.List;
import java.util.Map;

public class JanggiGameStartService {
    private final JanggiGameRepository repository;

    public JanggiGameStartService(JanggiGameRepository repository) {
        this.repository = repository;
    }

    public Long getNewGameId(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangePieces(hanStrategy, choStrategy);

        String currentDynasty = janggiGame.getCurrentDynasty().name();
        Long gameId = repository.saveGame(currentDynasty);

        Map<Position, Piece> pieces = janggiGame.getPieces();
        repository.saveAllPieces(gameId, pieces);

        return gameId;
    }

    public List<GameDto> getSavedGames() {
        return repository.findNotFinishedGames();
    }
}
