package janggi.service;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.Position;
import janggi.repository.JanggiRepository;
import janggi.view.dto.GameResult;
import janggi.view.dto.PieceStatus;

import java.util.List;
import java.util.Optional;

public class JanggiService {
    private final JanggiRepository janggiRepository;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public Long start(int choFormation, int hanFormation) {
        Janggi janggi = Janggi.start(choFormation, hanFormation);
        return janggiRepository.save(janggi);
    }

    public void move(Long gameId, Position from, Position to) {
        Janggi janggi = loadJanggi(gameId);

        janggi.movePiece(from, to);

        janggiRepository.update(gameId, janggi);
    }

    public List<PieceStatus> getBoardStatus(Long gameId) {
        Janggi janggi = loadJanggi(gameId);

        return janggi.getBoardSnapshot().entrySet().stream()
                .map(entry -> PieceStatus.from(
                        entry.getKey(),
                        entry.getValue().getCamp(),
                        entry.getValue().displayName()
                ))
                .toList();
    }

    public GameResult giveUpGame(Long gameId) {
        Janggi janggi = loadJanggi(gameId);
        janggi.giveUpGame();
        janggiRepository.update(gameId, janggi);
        return GameResult.fromGameResult(janggi.currentTurn());
    }

    public GameResult drawGame(Long gameId) {
        Janggi janggi = loadJanggi(gameId);
        janggi.drawGame();
        janggiRepository.update(gameId, janggi);
        if (janggi.isOnGoing()) {
            throw new IllegalArgumentException("게임이 종료되지 않았습니다.");
        }
        return GameResult.fromDrawGameResult(
                janggi.calculateTotalScore(Camp.CHO),
                janggi.calculateTotalScore(Camp.HAN)
        );
    }

    public Optional<GameResult> checkMatchResult(Long gameId) {
        Janggi janggi = loadJanggi(gameId);
        if (janggi.isOnGoing()) {
            return Optional.empty();
        }
        return Optional.of(GameResult.fromGameResult(janggi.currentTurn()));
    }

    public boolean isOngoing(Long gameId) {
        return loadJanggi(gameId).isOnGoing();
    }

    public Camp currentTurn(Long gameId) {
        return loadJanggi(gameId).currentTurn();
    }

    private Janggi loadJanggi(Long gameId) {
        return janggiRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다. ID: " + gameId));
    }
}
