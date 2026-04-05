package janggi.application;

import janggi.domain.board.Board;
import janggi.domain.board.BoardRepository;
import janggi.domain.JanggiGame;
import janggi.domain.point.Point;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.Team;
import janggi.infra.transaction.TransactionTemplate;
import janggi.presentation.dto.GameStatusInfo;

public class JanggiGameService {

    private final TransactionTemplate template;
    private final BoardRepository repository;

    public JanggiGameService(TransactionTemplate template, BoardRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    public long startNewGame(Board initBoard) {
        return template.executeInTransaction(() -> {
            JanggiGame game = new JanggiGame(initBoard, new ChoTurn());
            return repository.save(game);
        });
    }

    public GameStatusInfo getBoardStatus(long roomId) {
        JanggiGame game = repository.loadGame(roomId);
        return GameStatusInfo.from(
                game.getHanScore(),
                game.getChoScore(),
                game.getBoardStatus()
        );
    }

    public boolean isFinished(long roomId) {
        return repository.loadGame(roomId)
                    .isFinished();
    }

    public GameStatusInfo play(long roomId, Point from, Point to) {
        return template.executeInTransaction(() -> {
                JanggiGame game = repository.loadGame(roomId);
                game.play(from, to);
                repository.update(roomId, from, to, game);
                return GameStatusInfo.from(game.getHanScore(), game.getChoScore(), game.getBoardStatus());
            }
        );
    }

    public Team winner(long roomId) {
        return repository.loadGame(roomId)
                        .getWinner();
    }

    public Team currentTurn(long roomId) {
        return repository.loadGame(roomId)
                        .getTeam();
    }
}
