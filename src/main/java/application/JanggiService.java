package application;

import java.util.Map;
import model.GameStatus;
import model.JanggiGame;
import model.board.Board;
import model.board.BoardFactory;
import model.board.TeamFormation;
import repository.JanggiRepository;
import repository.db.TransactionTemplate;

public class JanggiService {

    private final TransactionTemplate transactionTemplate;
    private final JanggiRepository janggiRepository;

    public JanggiService(TransactionTemplate transactionTemplate, JanggiRepository janggiRepository) {
        this.transactionTemplate = transactionTemplate;
        this.janggiRepository = janggiRepository;
    }

    public Long createJanggiGame(TeamFormation hanFormation, TeamFormation choFormation) {
        return transactionTemplate.execute(() -> {
            Board board = BoardFactory.generateDefaultPieces();
            board.arrangePieces(hanFormation.generate());
            board.arrangePieces(choFormation.generate());

            JanggiGame janggiGame = new JanggiGame(board);
            return janggiRepository.save(janggiGame);
        });
    }

    public void finishByBigJang(Long janggiId) {
        transactionTemplate.execute(() -> {
            JanggiGame janggiGame = getGame(janggiId);
            janggiGame.finishByBigJang();
            janggiRepository.update(janggiId, janggiGame);
        });
    }

    public void movePiece(Long janggiId, MoveCommand command) {
        transactionTemplate.execute(() -> {
            JanggiGame janggiGame = getGame(janggiId);
            janggiGame.movePiece(command.current(), command.next());
            janggiRepository.update(janggiId, janggiGame);
        });
    }

    public JanggiGame getGame(Long janggiId) {
        return janggiRepository.findById(janggiId)
                .orElseThrow(() -> new IllegalArgumentException(janggiId + "번 장기 게임이 존재하지 않습니다."));
    }

    public Map<Long, GameStatus> collectGameStatus() {
        return janggiRepository.collectGameStatus();
    }
}
