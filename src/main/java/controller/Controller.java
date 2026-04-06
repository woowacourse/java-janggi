package controller;

import domain.Game;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import repository.GameDao;
import repository.PieceDao;
import view.InputView;
import view.OutputView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public Controller(InputView inputView, OutputView outputView, GameDao gameDao, PieceDao pieceDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }


    public void run() {
        String menu = retry(() -> inputView.readMainMenu());

        if (menu.equals("1")) {
            startNewGame();
        } else {
            loadExistGame();
        }
    }

    private void play(Game game, long gameId) {
        while (!game.isGameEnd()) {
            outputView.printHanScore(game.getCurrentScore(Team.HAN));
            outputView.printBoard(game.getBoard());
            outputView.printChoScore(game.getCurrentScore(Team.CHO));
            outputView.printCurrentTurn(game.getTurn());

            boolean isContinue = executeMove(game);
            if (!isContinue) {
                System.out.println("게임을 중단합니다. 현재 상태는 " + gameId + "번 방에 안전하게 저장되어 있습니다.");
                return;
            }
            gameDao.updateTurn(gameId, game.getTurn().name());
            pieceDao.saveAll(gameId, game.getBoard().getPieces());
        }

        outputView.printGameResult(game.getWinnerTeam());
    }

    private void startNewGame() {
        InitializeSetting choSetting = retry(() -> inputView.readInitialSetting("초(CHO)"));
        InitializeSetting hanSetting = retry(() -> inputView.readInitialSetting("한(HAN)"));
        Board board = BoardFactory.createBoard(choSetting, hanSetting);
        Game game = new Game(board);

        long gameId = gameDao.save(game.getTurn().name());
        pieceDao.saveAll(gameId, board.getPieces());

        play(game, gameId);
    }


    private void loadExistGame() {
        Map<Long, String> savedGames = gameDao.findAll();
        outputView.printSavedGames(savedGames);

        if (savedGames.isEmpty()) {
            System.out.println("새 게임을 시작합니다.");
            startNewGame();
            return;
        }

        long gameId = retry(() -> inputView.readGameId());
        try {
            Map<Position, Piece> loadedPieces = pieceDao.findByGameId(gameId);
            Team savedTurn = gameDao.findTurn(gameId);

            Board board = new Board(loadedPieces);
            Game game = new Game(board, savedTurn);

            System.out.println("\n[" + gameId + "번 방 게임을 성공적으로 불러왔습니다!]");
            play(game, gameId);

        } catch (Exception e) {
            outputView.printError(new IllegalArgumentException("게임을 불러오는데 실패했습니다. 방 번호를 확인하세요."));
        }
    }

    private boolean executeMove(Game game) {
        while (true) {
            try {
                Optional<Position> sourcePosition = inputView.readSourcePosition();
                if (sourcePosition.isEmpty()) {
                    return false;
                }

                game.validateMoveAblePiece(sourcePosition.get());
                Position to = inputView.readTargetPosition();
                game.move(sourcePosition.get(), to);
                return true;
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printError(e);
            }
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}
