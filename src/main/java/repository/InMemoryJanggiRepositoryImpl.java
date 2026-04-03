package repository;

import domain.JanggiGame;
import domain.Position;
import dto.PieceInfo;
import java.util.List;

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

    @Override
    public boolean isInProgress() {
        return !janggiGame.isGameFinished();
    }

    @Override
    public List<PieceInfo> allFactors() {
        return janggiGame.allFactors();
    }

    @Override
    public String currentPlayerTurn() {
        return janggiGame.currentPlayerTurn();
    }

    @Override
    public PieceInfo findPieceInfoAt(Position selected) {
        return janggiGame.findPieceInfoAt(selected);
    }

    @Override
    public String gameStatus() {
        return janggiGame.gameStatus();
    }
}
