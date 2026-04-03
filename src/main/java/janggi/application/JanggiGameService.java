package janggi.application;

import janggi.domain.board.Board;
import janggi.domain.board.BoardRepository;
import janggi.domain.JanggiGame;
import janggi.domain.point.Point;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.Team;
import janggi.presentation.dto.GameStatusInfo;

public class JanggiGameService {

    private final BoardRepository repository;

    public JanggiGameService(BoardRepository repository) {
        this.repository = repository;
    }

    public long startNewGame(Board initBoard) {
        JanggiGame game = new JanggiGame(initBoard, new ChoTurn());
        return repository.save(game);
    }

    public GameStatusInfo getBoardStatus(Long roomId) {
        return GameStatusInfo.from(repository.loadGame(roomId).getBoardStatus());
    }

    public boolean isFinished(Long roomId) {
        return repository.loadGame(roomId)
                .isFinished();
    }

    public void play(Long roomId, Point from, Point to) {
        JanggiGame game = repository.loadGame(roomId);
        game.play(from, to);
        repository.update(roomId, from, to, game);
    }

    public Team winner(Long roomId) {
        return repository.loadGame(roomId)
                .getWinner();
    }

    public Team currentTurn(Long roomId) {
        return repository.loadGame(roomId)
                .getTeam();
    }

    public double getHanScore(Long roomId) {
        return repository.loadGame(roomId)
                .getHanScore();
    }

    public double getChoScore(Long roomId) {
        return repository.loadGame(roomId)
                .getChoScore();
    }
}
