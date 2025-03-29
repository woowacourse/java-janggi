package janggi.service;

import janggi.domain.Board;
import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Position;
import janggi.domain.SetupType;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.repository.BoardRepository;
import janggi.repository.JanggiRepository;
import java.util.Optional;

public class JanggiService {

    private final JanggiRepository janggiRepository;
    private final BoardRepository boardRepository;

    public JanggiService(final JanggiRepository janggiRepository, final BoardRepository boardRepository) {
        this.janggiRepository = janggiRepository;
        this.boardRepository = boardRepository;
    }

    public boolean existsContinuedJanggiGame(final String redPlayerName, final String greenPlayerName) {
        return janggiRepository.existsByRedAndGreenPlayerNameAndGameStatus(redPlayerName,
                greenPlayerName,
                GameStatus.CONTINUE);
    }

    public JanggiGame loadJanggiGame(final String redPlayerName, final String greenPlayerName) {
        Optional<JanggiGame> janggiGameOptional = janggiRepository.findByRedAndGreenPlayerNameAndGameStatus(
                redPlayerName, greenPlayerName, GameStatus.CONTINUE);
        validateExistJanggiGame(redPlayerName, greenPlayerName, janggiGameOptional);
        return janggiGameOptional.get();
    }

    public JanggiGame initJanggiGame(final String redPlayerName,
                                     final String greenPlayerName,
                                     final SetupType redSetupType,
                                     final SetupType greenSetupType) {
        JanggiGame janggiGame = new JanggiGame(
                new Board(Pieces.createPieces(redSetupType, greenSetupType).getPieces()),
                new Player(redPlayerName, Team.RED),
                new Player(greenPlayerName, Team.GREEN));
        janggiRepository.save(janggiGame);
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        boardRepository.saveAll(janggiId, janggiGame.getBoard());
        return janggiGame;
    }

    public void savePiece(final String redPlayerName,
                          final String greenPlayerName,
                          final Position departure,
                          final Position destination,
                          final Piece piece,
                          final boolean isAlive) {
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        boardRepository.save(janggiId, departure, destination, piece, isAlive);
    }

    public void updateDiedPiece(final String redPlayerName,
                                final String greenPlayerName,
                                final Position destination,
                                final Piece removed) {
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        boardRepository.save(janggiId, destination, destination, removed, false);
    }

    public void saveJanggiGame(final JanggiGame janggiGame) {
        janggiRepository.save(janggiGame);
    }

    private long findJanggiId(final String redPlayerName, final String greenPlayerName) {
        Optional<Long> janggiIdOptional = janggiRepository.findJanggiIdByRedAndGreenPlayerNameAndGameStatus(
                redPlayerName, greenPlayerName, GameStatus.CONTINUE);
        validateExistJanggiGame(redPlayerName, greenPlayerName, janggiIdOptional);
        return janggiIdOptional.get();
    }

    private <T> void validateExistJanggiGame(final String redPlayerName,
                                             final String greenPlayerName,
                                             final Optional<T> gameOptional) {
        if (gameOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 게임입니다: " + redPlayerName + " vs " + greenPlayerName);
        }
    }
}
