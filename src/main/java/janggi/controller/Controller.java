package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.piece.Team;
import janggi.dto.BoardDto;
import janggi.dto.GameInformationDto;
import janggi.dto.PositionInputDto;
import janggi.dto.TeamInputDto;
import janggi.exception.business.BusinessException;
import janggi.exception.input.InputException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class Controller {
    private static final PositionInputDto EXIT = null;
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public Controller(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        outputView.printStartMessage();

        while (true) {
            try {
                outputView.printMainMenu();
                String menu = inputView.readMenu();

                if ("3".equals(menu)) {
                    break;
                }

                handleMenuSelection(menu);

            } catch (InputException e) {
                outputView.printInputErrorMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printBusinessErrorMessage(e.getMessage());
            }
        }
    }

    private void handleMenuSelection(String menu) {
        if ("1".equals(menu)) {
            JanggiGame newGame = janggiService.createNewGame();
            playGame(newGame);
            return;
        }

        if ("2".equals(menu)) {
            List<GameInformationDto> games = janggiService.findAllGames();
            outputView.printGameList(games);

            int gameId = inputView.readGameId();
            if (gameId == -1) {
                return;
            }

            JanggiGame loadedGame = janggiService.loadGame(gameId);
            playGame(loadedGame);
            return;
        }

        throw new AssertionError("도달할 수 없는 메뉴 번호입니다: " + menu);
    }

    private void playGame(JanggiGame janggiGame) {
        outputView.printBoard(BoardDto.from(janggiGame.getBoard()));

        if (janggiGame.isFinished()) {
            outputView.printReadOnlyModeMessage();
            TeamInputDto winnerDto = new TeamInputDto(janggiGame.getWinner().get());
            outputView.printGameOver(winnerDto.getTeamName());

            inputView.waitBeforeReturning();
            return;
        }

        while (janggiGame.getWinner().isEmpty()) {
            boolean isUserQuit = playSingleTurn(janggiGame);

            if (isUserQuit) {
                outputView.printNotification();
                return;
            }
        }

        TeamInputDto winnerDto = new TeamInputDto(janggiGame.getWinner().get());
        outputView.printGameOver(winnerDto.getTeamName());
        inputView.waitBeforeReturning();
    }

    private boolean playSingleTurn(JanggiGame janggiGame) {
        while (true) {
            try {
                TeamInputDto teamInputDto = new TeamInputDto(janggiGame.getCurrentTeam());

                PositionInputDto moveInputDto = inputView.playTurn(teamInputDto.getTeamName());

                if (moveInputDto == EXIT) {
                    return true;
                }

                Position from = Position.of(Row.of(moveInputDto.getFromRow()), Column.of(moveInputDto.getFromCol()));
                Position to = Position.of(Row.of(moveInputDto.getToRow()), Column.of(moveInputDto.getToCol()));

                janggiService.move(janggiGame, from, to);

                outputView.printBoard(BoardDto.from(janggiGame.getBoard()));
                int choScore = janggiGame.calculateScore(Team.CHO);
                int hanScore = janggiGame.calculateScore(Team.HAN);
                outputView.printScore(choScore, hanScore);

                return false;

            } catch (InputException e) {
                outputView.printInputErrorMessage(e.getMessage());
            } catch (BusinessException e) {
                outputView.printBusinessErrorMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printUndefinedErrorMessage();
            }
        }
    }
}
