package repository;

import domain.JanggiGame;
import domain.Position;
import dto.PieceInfo;
import java.util.List;

public interface JanggiRepository {
    void save(JanggiGame janggiGame);
    JanggiGame findJanggiGame();

    boolean isInProgress();
    List<PieceInfo> allFactors();
    String currentPlayerTurn();
    PieceInfo findPieceInfoAt(Position selected);
    String gameStatus();
}
