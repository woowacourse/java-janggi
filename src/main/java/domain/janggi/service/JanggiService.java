package domain.janggi.service;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.repository.BoardRepository;
import domain.janggi.Janggi;
import domain.turn.Turn;
import domain.turn.repository.TurnRepository;

public class JanggiService {

    private final BoardRepository boardRepository;
    private final TurnRepository turnRepository;

    public JanggiService(
        final BoardRepository boardRepository,
        final TurnRepository turnRepository
    ) {
        this.boardRepository = boardRepository;
        this.turnRepository = turnRepository;
    }

    // @Transactional
    public Janggi startGame() {
        if (hasSavedGame()) {
            return loadGame();
        }

        return initializeNewGame();
    }

    private boolean hasSavedGame() {
        return boardRepository.hasAnyPiece() && turnRepository.exists();
    }

    private Janggi initializeNewGame() {
        final Janggi janggi = Janggi.initialize();

        return saveGame(janggi);
    }

    // @Transactional
    public Janggi playTurn(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Janggi janggi = loadGame();
        janggi.processTurn(selectBoardPosition, destinationBoardPosition);

        return saveGame(janggi);
    }

    private Janggi loadGame() {
        final Board board = boardRepository.load().orElseThrow(
            () -> new IllegalStateException("저장된 보드가 없습니다."));
        final Turn turn = turnRepository.findLast().orElseThrow(
            () -> new IllegalStateException("저장된 턴이 없습니다."));

        return new Janggi(board, turn);
    }

    private Janggi saveGame(final Janggi janggi) {
        boardRepository.save(janggi.getBoard());
        turnRepository.save(janggi.getTurn());

        return janggi;
    }

    // @Transactional
    public void deleteSavedGame() {
        boardRepository.deleteAll();
        turnRepository.deleteAll();
    }
}
