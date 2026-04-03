package service;

import domain.JanggiGame;
import domain.Position;
import factory.JanggiBoardFactory;
import repository.JanggiRepository;

public class JanggiCommandService {

    private final JanggiRepository janggiRepository;

    public JanggiCommandService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public void setupGame() {
        JanggiGame janggiGame = JanggiGame.initGame(JanggiBoardFactory.initialBoard());

        // 서비스, 리포지토리 계층 도입 후 리팩토링 예정
        janggiRepository.save(janggiGame);
    }

    public void move(Position selected, Position target) {
        JanggiGame janggiGame = findJanggiGame();

        janggiGame.move(selected, target);
    }

    private JanggiGame findJanggiGame() {
        return janggiRepository.findJanggiGame();
    }
}
