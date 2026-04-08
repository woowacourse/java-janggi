package service;

import domain.JanggiGame;
import domain.Position;
import factory.JanggiBoardFactory;
import factory.JanggiGameRestorer;
import repository.JanggiRepository;

public class JanggiCommandService {

    private final JanggiRepository janggiRepository;

    public JanggiCommandService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public long setupGame() {
        JanggiGame janggiGame = JanggiGame.initGame(JanggiBoardFactory.initialBoard());

        return janggiRepository.createGame(janggiGame);
    }

    public void move(long gameId, Position selected, Position target) {
        JanggiGame janggiGame = findJanggiGame(gameId);

        janggiGame.move(selected, target);
        janggiRepository.updateGame(gameId, janggiGame);
    }

    private JanggiGame findJanggiGame(long gameId) {
        return janggiRepository.loadGame(gameId);
    }
}
