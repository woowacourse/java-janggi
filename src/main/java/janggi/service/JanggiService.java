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
import janggi.entity.BoardEntity;
import janggi.entity.PieceEntity;
import janggi.repository.BoardRepository;
import janggi.repository.JanggiRepository;

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
        return janggiRepository.findByRedAndGreenPlayerNameAndGameStatus(
                        redPlayerName, greenPlayerName, GameStatus.CONTINUE)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 게임입니다: " + redPlayerName + " vs " + greenPlayerName));
    }

    public JanggiGame initJanggiGame(final String redPlayerName,
                                     final String greenPlayerName,
                                     final SetupType redSetupType,
                                     final SetupType greenSetupType) {
        JanggiGame janggiGame = new JanggiGame(new Board(Pieces.createPieces(redSetupType, greenSetupType).getPieces()),
                new Player(redPlayerName, Team.RED),
                new Player(greenPlayerName, Team.GREEN));
        janggiRepository.save(janggiGame);
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        boardRepository.saveBoard(BoardEntity.from(janggiId));
        long boardId = findBoardId(janggiId);
        boardRepository.savePieceAll(boardId, janggiGame.getBoard());
        return janggiGame;
    }

    public void savePiece(final String redPlayerName,
                          final String greenPlayerName,
                          final Position departure,
                          final Position destination,
                          final Piece piece,
                          final boolean isAlive) {
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        long boardId = findBoardId(janggiId);
        boardRepository.savePiece(PieceEntity.of(boardId, piece, destination, isAlive), departure);
    }

    private long findBoardId(final long janggiId) {
        return boardRepository.findByJanggiId(janggiId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다: " + janggiId));
    }

    public void updateDiedPiece(final String redPlayerName,
                                final String greenPlayerName,
                                final Position destination,
                                final Piece removed) {
        long janggiId = findJanggiId(redPlayerName, greenPlayerName);
        long boardId = findBoardId(janggiId);
        boardRepository.savePiece(PieceEntity.of(boardId, removed, destination, false), destination);
    }

    public void saveJanggiGame(final JanggiGame janggiGame) {
        janggiRepository.save(janggiGame);
    }

    private long findJanggiId(final String redPlayerName, final String greenPlayerName) {
        return janggiRepository.findJanggiIdByRedAndGreenPlayerNameAndGameStatus(
                        redPlayerName, greenPlayerName, GameStatus.CONTINUE)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 게임입니다: " + redPlayerName + " vs " + greenPlayerName));
    }
}
