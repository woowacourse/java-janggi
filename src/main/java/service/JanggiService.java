package service;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import game.JanggiGame;
import repository.BoardRepository;
import repository.GameRepository;

import java.util.HashMap;

public class JanggiService {

    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;
    private JanggiGame janggiGame;

    public JanggiService(BoardRepository boardRepository, GameRepository gameRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public void initializeGame(boolean resume) {
        if (resume) {
            this.janggiGame = new JanggiGame(new JanggiBoard(boardRepository.findAll()), gameRepository.findCurrentTurn());
            return;
        }
        clearAllRepository();
        this.janggiGame = new JanggiGame(new JanggiBoard(new HashMap<>()), Team.CHO);
    }

    public void move(Position currentPosition, Position targetPosition) {
        janggiGame.progress(currentPosition, targetPosition);
        saveStatus();
    }

    public boolean canResume() {
        return gameRepository.isNotFinished();
    }

    public void clearAllRepository() {
        boardRepository.deleteAll();
        gameRepository.deleteAll();
    }

    public void saveStatus() {
        JanggiBoard board = janggiGame.getJanggiBoard();
        boardRepository.save(board.getJanggiBoard());
        gameRepository.save(
                janggiGame.getCurrentTeam(),
                janggiGame.isFinished(),
                board.calculateScore(Team.CHO),
                board.calculateScore(Team.HAN)
        );
    }

    public boolean isFinished() {
        return janggiGame.isFinished();
    }

    public JanggiBoard getBoard() {
        return janggiGame.getJanggiBoard();
    }

    public double calculateScore(Team team) {
        return janggiGame.getJanggiBoard().calculateScore(team);
    }

    public String getCurrentTurnName() {
        return janggiGame.getCurrentTeam().getName();
    }

    public String getWinnerName() {
        return janggiGame.getWinner().getName();
    }
}
