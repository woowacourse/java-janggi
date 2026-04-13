package service;

import domain.JanggiGame;
import domain.Position;
import dto.PieceInfo;
import dto.UnfinishedGameInfo;
import exception.JanggiBusinessException;
import java.util.List;
import repository.JanggiRepository;

public class JanggiQueryService {

    private final JanggiRepository janggiRepository;

    public JanggiQueryService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public boolean isInProgress(long gameId) {
        return !findJanggiGameById(gameId).isFinished();
    }

    public List<PieceInfo> allFactors(long gameId) {
        return findJanggiGameById(gameId).allFactors();
    }

    public String currentPlayerTurn(long gameId) {
        return findJanggiGameById(gameId).currentPlayerTurn();
    }

    public PieceInfo findPieceInfoAt(long gameId, Position selected) {
        return findJanggiGameById(gameId).findPieceInfoAt(selected);
    }

    public String gameStatus(long gameId) {
        return findJanggiGameById(gameId).gameStatus();
    }

    private JanggiGame findJanggiGameById(long gameId) {
        return janggiRepository.loadGame(gameId)
                .orElseThrow(() -> new JanggiBusinessException("[ERROR] 존재하지 않는 게임 ID입니다."));
    }

    public List<UnfinishedGameInfo> findUnfinishedGameInfos() {
        return janggiRepository.findUnfinishedGameInfos();
    }

    public boolean hasUnfinishedGameId() {
        return janggiRepository.hasUnfinishedGame();
    }

    public long findLatestUnfinishedGameId() {
        return janggiRepository.findLatestUnfinishedGameId().get();
    }

    public int currentPlayerPiecesPointSum(long gameId) {
        return findJanggiGameById(gameId).currentPlayerPiecesPointSum();
    }
}
