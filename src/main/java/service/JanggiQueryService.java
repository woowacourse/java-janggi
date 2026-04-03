package service;

import domain.JanggiGame;
import domain.Position;
import dto.PieceInfo;
import java.util.List;
import repository.JanggiRepository;

public class JanggiQueryService {

    private final JanggiRepository janggiRepository;

    public JanggiQueryService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public boolean isInProgress() {
        return !findJanggiGame().isGameFinished();
    }

    public List<PieceInfo> allFactors() {
        return findJanggiGame().allFactors();
    }

    public String currentPlayerTurn() {
        return findJanggiGame().currentPlayerTurn();
    }

    public PieceInfo findPieceInfoAt(Position selected) {
        return findJanggiGame().findPieceInfoAt(selected);
    }

    public String gameStatus() {
        return findJanggiGame().gameStatus();
    }

    private JanggiGame findJanggiGame() {
        return janggiRepository.findJanggiGame();
    }
}
