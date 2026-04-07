package janggi.controller;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final GameRepository gameRepository = new GameRepository();
    private final PieceRepository pieceRepository = new PieceRepository();

    private JanggiGame janggiGame;
    private Board board;

    public void run() {
        initialize();
        playGame();
        printResult();
    }

    private void initialize() {
        List<JanggiGame> playingGames = gameRepository.findPlayingGames();

        if (playingGames.isEmpty()) {
            startNewGame();
            return;
        }

        int choice = readValidChoice(playingGames);
        if (choice == playingGames.size() + 1) {
            startNewGame();
            return;
        }

        resumeGame(playingGames.get(choice - 1));
    }

    private int readValidChoice(List<JanggiGame> playingGames) {
        while (true) {
            try {
                int choice = inputView.readGameChoice(playingGames);
                validateChoiceRange(choice, playingGames.size() + 1);
                return choice;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateChoiceRange(int choice, int max) {
        if (choice < 1 || choice > max) {
            throw new IllegalArgumentException("1부터 " + max + " 사이의 번호를 입력해주세요.");
        }
    }


    private void startNewGame() {
        janggiGame = new JanggiGame();
        Long gameId = gameRepository.save(janggiGame);
        janggiGame.assignId(gameId);

        board = new Board();
        pieceRepository.saveAll(gameId, board);

        outputView.printGameStart(gameId);
    }

    private void resumeGame(JanggiGame game) {
        janggiGame = game;
        board = pieceRepository.findByGameId(game.findGameId());

        outputView.printResume(game.findGameId(), game.findCurrentTeam());
    }

    private void playGame() {
        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.findCurrentTeam();
            outputView.printCurrentTurn(currentTeam);
            outputView.printBoard(board);
            attemptMove(currentTeam);

            if (!janggiGame.isFinished()) {
                janggiGame.changeTurn();
                gameRepository.updateTurn(janggiGame);
            }
        }
    }

    private void attemptMove(Team currentTeam) {
        boolean moved = false;
        while (!moved) {
            moved = tryMove(currentTeam);
        }
    }

    private boolean tryMove(Team currentTeam) {
        try {
            MoveCommand moveCommand = inputView.readMovePositions();
            Position from = moveCommand.getFrom();
            Position to = moveCommand.getTo();

            Piece captured = board.move(from, to, currentTeam);
            pieceRepository.movePiece(janggiGame.findGameId(), from, to);

            janggiGame.processCaptured(captured);
            if (janggiGame.isFinished()) {
                gameRepository.updateFinished(janggiGame);
            }

            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void printResult() {
        outputView.printGameEnd(janggiGame.findWinner());
        outputView.printScore(Team.CHO, board.calculateScore(Team.CHO));
        outputView.printScore(Team.HAN, board.calculateScore(Team.HAN));
    }
}
