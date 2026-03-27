package controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import domain.Board;
import domain.Country;
import domain.JanggiGame;
import domain.PieceType;
import domain.Position;
import service.JanggiService;
import service.dto.BoardDto;
import service.dto.PositionDto;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private static final int MAX_RETRY = 10;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;


    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = new JanggiService();
    }

    public void run() {
        Board board = initBoard();
        JanggiGame janggiGame = initJanggiGame(board);

        while(true){
            outputView.printChangeTurnMessage(janggiGame.getCountry().getName());
            List<PositionDto> positionDtos = requestMovePiece(janggiGame);
            playTurn(positionDtos, janggiGame, board);
        }
    }

    private void playTurn(List<PositionDto> positionDtos, JanggiGame janggiGame, Board board) {
        Position start = requestStartPiecePosition(positionDtos);
        requestEndPosition(start, janggiGame);
        outputView.printBoard(janggiService.getBoard(board));
    }

    private void requestEndPosition(Position start, JanggiGame janggiGame) {
        doRetry(() -> {
                    List<Integer> destination = inputView.requestMovePosition();
                    Position end = Position.create(destination.getFirst(), destination.getLast());
                    janggiService.applyMove(start, end, janggiGame);
                    return Optional.empty();
                }
        );
    }

    private Board initBoard() {
        outputView.printGameStartMessage();
        outputView.printCountry(Country.CHO);
        int choMasangChoice = doRetry(inputView::requestMaSangPosition);

        outputView.printCountry(Country.HAN);
        int hanMasangChoice = doRetry(inputView::requestMaSangPosition);

        return janggiService.createBoard(choMasangChoice, hanMasangChoice);
    }

    private JanggiGame initJanggiGame(Board board) {
        outputView.printTurnStartMessage();

        BoardDto boardDto = janggiService.getBoard(board);
        outputView.printBoard(boardDto);

        return janggiService.createJanggiGame(board);
    }

    private List<PositionDto> requestMovePiece(JanggiGame janggiGame) {
        return doRetry(() -> {
            PieceType pt = PieceType.of(inputView.requestPiece());
            List<PositionDto> dtos = janggiService.getPiecePositions(janggiGame, pt);
            outputView.printPiecePossiblePosition(pt, dtos);
            return dtos;
        });
    }

    private Position requestStartPiecePosition(List<PositionDto> positionDtos) {
        return doRetry(() -> {
            int choiceStart = inputView.requestStartPiecePosition() - 1;
            return Position.create(positionDtos.get(choiceStart).x(), positionDtos.get(choiceStart).y());
        });
    }


    private <T> T doRetry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                retry++;
                if (retry > MAX_RETRY) {
                    throw new IllegalStateException("입력횟수를 초과했습니다.");
                }
            }
        }
    }
}
