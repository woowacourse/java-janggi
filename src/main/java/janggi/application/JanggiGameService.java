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
        return template.executeInTransaction(connection -> {
            JanggiGame game = new JanggiGame(initBoard, new ChoTurn());
            return repository.save(game, connection);
        });
    }

    public GameStatusInfo getBoardStatus(long roomId) {
        return template.executeInTransaction(connection -> {
            JanggiGame game = repository.loadGame(roomId, connection);
            return GameStatusInfo.from(
                    game.getHanScore(),
                    game.getChoScore(),
                    game.getBoardStatus()
                );
            }
        );
    }

    public boolean isFinished(long roomId) {
        return template.executeInTransaction(connection -> {
            return repository.loadGame(roomId, connection)
                    .isFinished();
            }
        );
    }

    public GameStatusInfo play(long roomId, Point from, Point to) {
        return template.executeInTransaction(connection -> {
                JanggiGame game = repository.loadGame(roomId, connection);
                game.play(from, to);
                repository.update(roomId, from, to, game, connection);
                return GameStatusInfo.from(game.getHanScore(), game.getChoScore(), game.getBoardStatus());
            }
        );
    }

    public Team winner(long roomId) {
        return template.executeInTransaction(connection -> {
            return repository.loadGame(roomId, connection)
                    .getWinner();
            }
        );
    }

    public Team currentTurn(long roomId) {
        return template.executeInTransaction(connection -> {
            return repository.loadGame(roomId, connection)
                    .getTeam();
            }
        );
    }
}
