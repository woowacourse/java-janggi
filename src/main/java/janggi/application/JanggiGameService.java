package janggi.application;

import janggi.domain.board.Board;
import janggi.domain.board.BoardRepository;
import janggi.domain.JanggiGame;
import janggi.domain.point.Point;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.Team;
import janggi.presentation.dto.GameStatusInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JanggiGameService {

    private final BoardRepository repository;
    private final Map<Long, JanggiGame> games;

    public JanggiGameService(BoardRepository repository) {
        this.repository = repository;
        this.games = new ConcurrentHashMap<>();
    }

    public Long startNewGame(Board initBoard) {
        JanggiGame game = new JanggiGame(initBoard, new ChoTurn());
        Long roomId = repository.save(game);
        games.put(roomId, game);
        return roomId;
    }

    public void loadExistsBoard(Long gameRoomId) {
        if(gameRoomId == null) {
            throw new IllegalArgumentException("[ERROR] 잘못된 게임방 ID 입력입니다.");
        }
        this.games.put(gameRoomId, repository.loadGame(gameRoomId));
    }

    public GameStatusInfo getBoardStatus(Long gameRoomId) {
        return GameStatusInfo.from(games.get(gameRoomId).getBoardStatus());
    }

    public boolean isFinished(Long gameRoomId) {
        return games.get(gameRoomId).isFinished();
    }

    public void play(Long roomId, Point from, Point to) {
        JanggiGame game = games.get(roomId);
        game.play(from, to);
        repository.update(roomId, from, to, game);
    }

    public Team winner(Long roomId) {
        return games.get(roomId).getWinner();
    }

    public Team currentTurn(Long roomId) {
        return games.get(roomId).getTeam();
    }

    public double getHanScore(Long roomId) {
        return games.get(roomId).getHanScore();
    }

    public double getChoScore(Long roomId) {
        return games.get(roomId).getChoScore();
    }
}
