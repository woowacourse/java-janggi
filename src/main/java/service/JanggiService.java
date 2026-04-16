package service;

import domain.Team;
import domain.board.JanggiBoard;
import domain.board.JanggiBoardInitializer;
import domain.board.JanggiBoardLoader;
import domain.board.JanggiGame;
import domain.dto.JanggiBoardDto;
import domain.position.Position;
import repository.JanggiRepository;

public class JanggiService {
    private final JanggiRepository repository;
    private JanggiGame janggiGame;
    private long boardId;

    public JanggiService(JanggiRepository repository) {
        this.repository = repository;
    }

    public void startOrLoadGame() {
        long latestId = repository.findLatestPlayingId();

        if (latestId != -1) {
            System.out.println("이전 게임을 불러옵니다.");
            loadGame(latestId);
            return;
        }

        System.out.println("새 게임을 시작합니다.");
        createNewGame();
    }

    private void createNewGame() {
        JanggiBoard board = new JanggiBoard(new JanggiBoardInitializer());
        this.janggiGame = new JanggiGame(board);
        this.boardId = repository.save(JanggiBoardDto.from(board));
    }

    private void loadGame(long boardId) {
        JanggiBoardDto boardDto = repository.findAllPieces(boardId);
        Team turn = Team.valueOf(repository.findTurn(boardId));
        JanggiBoard board = new JanggiBoard(new JanggiBoardLoader(boardDto), turn);
        this.janggiGame = new JanggiGame(board);
        this.boardId = boardId;
    }

    public void play(Position from, Position to) {
        janggiGame.playTurn(from, to);

        // 영속화
        repository.updateMove(boardId, from.row(), from.col(), to.row(), to.col());
        repository.updateTurn(boardId, janggiGame.getTurn().name());

        // 게임 종료 체크
        if (janggiGame.isGameOver()) {
            repository.finish(boardId);
        }
    }

    public boolean isGameOver() {
        return janggiGame.isGameOver();
    }

    public JanggiBoardDto getBoardDto() {
        return janggiGame.getBoardDto();
    }

    public double calculateScore(Team team) {
        return janggiGame.calculateScore(team);
    }
}
