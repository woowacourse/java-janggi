package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.piece.Team;
import janggi.dto.BoardDto;
import janggi.dto.PositionInputDto;
import janggi.dto.TeamInputDto;
import janggi.exception.business.BusinessException;
import janggi.exception.input.InputException;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = new Board(BoardFactory.generate());
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printStartMessage();
        outputView.printBoard(BoardDto.from(janggiGame.getBoard()));

        while (janggiGame.getWinner().isEmpty()) {
            playSingleTurn(janggiGame);
        }
        TeamInputDto teamInputDto = new TeamInputDto(janggiGame.getWinner().get());
        outputView.printGameOver(teamInputDto.getTeamName());
    }

    private void playSingleTurn(JanggiGame janggiGame) {
        while (true) {
            try {
                TeamInputDto teamInputDto = new TeamInputDto(janggiGame.getCurrentTeam());
                PositionInputDto moveInputDto = inputView.playTurn(teamInputDto.getTeamName());

                Position from = Position.of(Row.of(moveInputDto.getFromRow()), Column.of(moveInputDto.getFromCol()));
                Position to = Position.of(Row.of(moveInputDto.getToRow()), Column.of(moveInputDto.getToCol()));

                janggiGame.move(from, to);
                outputView.printBoard(BoardDto.from(janggiGame.getBoard()));

                int choScore = janggiGame.calculateScore(Team.CHO);
                int hanScore = janggiGame.calculateScore(Team.HAN);
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
