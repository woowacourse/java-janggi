package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.piece.Team;
import janggi.dto.BoardDto;
import janggi.dto.GameRoomDto;
import janggi.dto.PositionInputDto;
import janggi.dto.TeamInputDto;
import janggi.exception.business.BusinessException;
import janggi.exception.input.InputException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

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
        GameRoomDto janggiGame = janggiService.joinGame();

        outputView.printStartMessage();
        outputView.printBoard(BoardDto.from(janggiGame.getBoard()));

        while (janggiGame.getWinner().isEmpty()) {
            playSingleTurn(janggiGame);
        }
        TeamInputDto teamInputDto = new TeamInputDto(janggiGame.getWinner().get());
        outputView.printGameOver(teamInputDto.getTeamName());
    }

    private void playSingleTurn(GameRoomDto gameRoomDto) {
        while (true) {
            try {
                TeamInputDto teamInputDto = new TeamInputDto(gameRoomDto.getCurrentTeam());
                PositionInputDto moveInputDto = inputView.playTurn(teamInputDto.getTeamName());

                Position from = Position.of(Row.of(moveInputDto.getFromRow()), Column.of(moveInputDto.getFromCol()));
                Position to = Position.of(Row.of(moveInputDto.getToRow()), Column.of(moveInputDto.getToCol()));

                int currentGameId = gameRoomDto.getGameId();
                janggiService.move(gameRoomDto.getGame(), currentGameId, from, to);
                outputView.printBoard(BoardDto.from(gameRoomDto.getBoard()));

                int choScore = gameRoomDto.calculateScore(Team.CHO);
                int hanScore = gameRoomDto.calculateScore(Team.HAN);
                outputView.printScore(choScore, hanScore);
                break;

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
