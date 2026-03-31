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
    private JanggiGame game;

    public JanggiGameService(BoardRepository repository) {
        this.repository = repository;
    }

    public Long startNewGame(Board initBoard) {
        this.game = new JanggiGame(initBoard, new ChoTurn());
        return repository.save(this.game);
    }

    public void loadExistsBoard(Long gameRoomId) {
        this.game = repository.loadGame(gameRoomId);
    }

    public GameStatusInfo getBoardStatus() {
        return GameStatusInfo.from(game.getBoardStatus());
    }

    public boolean isFinished() {
        return game.isFinished();
    }

    public void play(Long roomId, Point from, Point to) {
        game.play(from, to);
        repository.update(roomId, from, to, game);
    }

    public Team winner() {
        return game.getWinner();
    }

    public Team currentTurn() {
        return game.getTeam();
    }

    public double getHanScore() {
        return game.getHanScore();
    }

    public double getChoScore() {
        return game.getChoScore();
    }
}
