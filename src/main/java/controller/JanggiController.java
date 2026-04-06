package controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import domain.Board;
import domain.JanggiGame;
import domain.Position;
import domain.enums.Country;
import domain.enums.MaSang;
import domain.enums.PieceType;
import service.JanggiService;
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

    private static void checkRetryLimit(int retry) {
        if (retry > MAX_RETRY) {
            throw new IllegalStateException("입력횟수를 초과했습니다.");
        }
    }

    public void run() {
        Board board = initBoard();
        JanggiGame janggiGame = initJanggiGame(board);

        boolean isGameContinue = true;
        while (!janggiGame.isGameOver() && isGameContinue) {
            outputView.printScore(janggiService.buildScoreDto(janggiGame));
            outputView.printChangeTurnMessage(janggiGame.getCountry().getName());
            playTurn(janggiGame, board);

            if (janggiGame.isGameOver()) {
                break;
            }
            isGameContinue = isGameContinue();
        }
        outputView.printScore(janggiService.buildScoreDto(janggiGame));
        outputView.printGameOverMessage(janggiService.getFinalWinner(janggiGame));
    }

    private Board initBoard() {
        outputView.printGameStartMessage();

        MaSang choMaSangChoose = initMaSang(Country.CHO);
        MaSang hanMaSangChoose = initMaSang(Country.HAN);

        return janggiService.createBoard(choMaSangChoose, hanMaSangChoose);
    }

    private MaSang initMaSang(Country country) {
        return doRetry(() -> {
            outputView.printCountry(country);
            int maSangChoice = inputView.requestMaSangPosition();
            return MaSang.getByNum(maSangChoice);
        });
    }

    private JanggiGame initJanggiGame(Board board) {
        outputView.printTurnStartMessage();
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

    private Optional<Position> requestStartPiecePosition(List<PositionDto> positionDtos) {
        return doRetry(() -> {
            int choiceStart = inputView.requestStartPiecePosition(positionDtos.size());
            if (choiceStart == InputView.CHOICE_QUIT_NUMBER) {
                return Optional.empty();
            }
            int startIdx = choiceStart - 1;
            return Optional.of(Position.create(positionDtos.get(startIdx).x(), positionDtos.get(startIdx).y()));
        });
    }

    private void requestEndPosition(Position start, JanggiGame janggiGame) {
        doRetry(() -> {
                    Optional<List<Integer>> input = inputView.requestMovePosition();
                    if (input.isEmpty()) {
                        return Optional.empty();
                    }
                    List<Integer> destination = input.get();
                    Position end = Position.create(destination.getFirst(), destination.getLast());
                    janggiService.applyMove(start, end, janggiGame);
                    return Optional.empty();
                }
        );
    }

    private void playTurn(JanggiGame janggiGame, Board board) {
        outputView.printBoard(janggiService.buildBoardDto(board), janggiService.buildColorDto(board));
        List<PositionDto> positionDtos = requestMovePiece(janggiGame);
        Optional<Position> start = requestStartPiecePosition(positionDtos);
        if (start.isEmpty()) {
            return;
        }
        List<PositionDto> availableEndPositions = janggiService.buildAvailabelPositions(board, start.get());
        outputView.printPiecePossibleEndPosition(availableEndPositions);
        requestEndPosition(start.get(), janggiGame);

        outputView.printBoard(janggiService.buildBoardDto(board), janggiService.buildColorDto(board));
    }

    private boolean isGameContinue() {
        return doRetry(inputView::askGameContinue);
    }

    private <T> T doRetry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
                retry++;
                checkRetryLimit(retry);
            }
        }
    }
}
