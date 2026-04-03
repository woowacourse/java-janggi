package repository;

import domain.JanggiGame;

public class InMemoryJanggiRepositoryImpl implements JanggiRepository{

    private JanggiGame janggiGame;

    @Override
    public void save(JanggiGame janggiGame) {
        this.janggiGame = janggiGame;
    }

    @Override
    public JanggiGame findJanggiGame() {
        return janggiGame;
    }
}
