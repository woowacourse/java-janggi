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

    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiController(InputView inputView, OutputView outputView,
                            GameRepository gameRepository, PieceRepository pieceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

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
        janggiGame = gameRepository.save(new JanggiGame());

        board = new Board();
        pieceRepository.saveAll(janggiGame.findGameId(), board);
        outputView.printGameStart(janggiGame.findGameId());
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
                gameRepository.update(janggiGame);
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
                gameRepository.update(janggiGame);
            }

            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void printResult() {
        outputView.printResult(
                janggiGame.findWinner(),
                board.calculateScore(Team.CHO),
                board.calculateScore(Team.HAN)
        );
    }
}
