package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.dto.GameRoomDto;
import janggi.exception.BusinessException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.InputView.MoveCommand;
import janggi.view.OutputView;
import java.util.List;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public Controller(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        while (handleMenuSelection()) {
        }
    }

    private boolean handleMenuSelection() {
        outputView.printMenu();

        String command = inputView.readMenuCommand();

        if (command.equals("1")) {
            startNewGame();
            return true;
        }
        if (command.equals("2")) {
            showGameList();
            return true;
        }
        if (command.equals("3")) {
            return false;
        }

        outputView.printErrorMessage("\n[!] 잘못된 메뉴 번호입니다. 1~3 사이의 번호를 다시 입력해주세요. (입력한 값: " + command + ")\n");
        return true;
    }

    private void startNewGame() {
        outputView.printStartMessage();
        int gameId = janggiService.createNewGame();
        Board board = new Board(BoardFactory.generate());
        playGame(gameId, board, Team.CHO);
    }

    private void showGameList() {
        List<GameRoomDto> games = janggiService.findAllGames();

        if (games.isEmpty()) {
            outputView.printEmptyListMessage();
            return;
        }

        outputView.printGameList(games);
        handleListAction();
    }

    private void handleListAction() {
        try {
            String input = inputView.readListAction();
            processCommand(input);
        } catch (BusinessException e) {
            outputView.printErrorMessage(e.getMessage());
            handleListAction();
        }
    }

    private void processCommand(String input) {
        if (input.equals("back")) {
            return;
        }
        String[] tokens = input.split(" ");
        validateCommandFormat(tokens);

        String action = tokens[0];
        int gameId = Integer.parseInt(tokens[1]);

        executeAction(action, gameId);
    }

    private void executeAction(String action, int gameId) {
        if (action.equals("play")) {
            resumeGame(gameId);
        }
        if (action.equals("delete")) {
            janggiService.deleteGame(gameId);
            outputView.printDeleteSuccess();
        }
    }

    private void resumeGame(int gameId) {
        GameRoomDto gameRoom = janggiService.getGameRoom(gameId);
        validateGamePlayable(gameRoom);

        outputView.printResumeMessage();
        Board board = janggiService.loadBoard(gameId);
        Team currentTeam = Team.valueOf(gameRoom.getCurrentTurn());

        playGame(gameId, board, currentTeam);
    }

    private void validateGamePlayable(GameRoomDto gameRoom) {
        if (gameRoom.getStatus().equals("FINISHED")) {
            throw new BusinessException("종료된 게임에는 입장할 수 없습니다. (목록에서 삭제만 가능합니다.)");
        }
    }

    private void validateCommandFormat(String[] tokens) {
        if (tokens.length != 2) {
            throw new BusinessException("올바른 명령어 형식이 아닙니다. (예: play 1)");
        }
    }

    private void playGame(int gameId, Board board, Team currentTeam) {
        while (!isGameOver(board)) {
            printCurrentState(board);
            playTurn(gameId, board, currentTeam);

            currentTeam = currentTeam.switchTeam();
        }

        printFinalResult(board);
    }

    private void printCurrentState(Board board) {
        outputView.printBoard(BoardDto.from(board));
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);
        outputView.printScore(choScore, hanScore);
    }

    private void playTurn(int gameId, Board board, Team team) {
        boolean isSuccess = false;

        while (!isSuccess) {
            isSuccess = attemptMove(gameId, board, team);
        }

        if (board.isKingCaptured()) {
            return;
        }

        notifyAnyCheck(board);
    }

    private boolean attemptMove(int gameId, Board board, Team team) {
        try {
            MoveCommand command = inputView.readMoveCommand(team.getTeam());
            janggiService.movePiece(gameId, board, command.fromPosition(), command.toPosition(), team);
            return true;
        } catch (BusinessException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }
    }

    private void notifyAnyCheck(Board board) {
        if (board.isCheck(Team.HAN)) {
            outputView.printCheckMessage(Team.HAN);
        }

        if (board.isCheck(Team.CHO)) {
            outputView.printCheckMessage(Team.CHO);
        }
    }

    private void printFinalResult(Board board) {
        outputView.printBoard(BoardDto.from(board));
        Team winner = board.getWinner();
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);
        outputView.printFinalResult(winner, choScore, hanScore);
    }

    private boolean isGameOver(Board board) {
        return board.isKingCaptured();
    }
}
