package service;

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
        return janggiRepository.isInProgress();
    }

    public List<PieceInfo> allFactors() {
        return janggiRepository.allFactors();
    }

    public String currentPlayerTurn() {
        return janggiRepository.currentPlayerTurn();
    }

    public PieceInfo findPieceInfoAt(Position selected) {
        return janggiRepository.findPieceInfoAt(selected);
    }

    public String gameStatus() {
        return janggiRepository.gameStatus();
    }
}
