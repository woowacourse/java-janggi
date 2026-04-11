package janggi;

import janggi.domain.GameRepository;
import janggi.infrastructure.GameRepositoryImpl;

public class JanggiApplication {

    public static void main(String[] args) {
        GameRepository gameRepository = new GameRepositoryImpl();
        JanggiGame.from(gameRepository).start();
    }
}
