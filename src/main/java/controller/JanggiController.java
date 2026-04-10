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
import exception.Validator;
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

    public void run() {
        int gameId = initOrGetGame();
        playJanggiGame(gameId);
        outputView.printScore(janggiService.buildScoreDto(gameId));
        outputView.printGameOverMessage(janggiService.getFinalWinner(gameId));
    }

    private int initOrGetGame() {
        return doRetry(() -> {
            int choose = inputView.chooseGameStartNewOrAgain();
            if (choose == 2) {
                int num = inputView.requestGameId();
                janggiService.validateExistGame(num);
                return num;
            }
            Board board = initBoard();
            JanggiGame janggiGame = initJanggiGame(board);
            return janggiService.initializeData(janggiGame);
        });
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

    private void playJanggiGame(int gameId) {
        boolean isGameContinue = true;
        while (!janggiService.isGameOver(gameId) && isGameContinue) {
            outputView.printScore(janggiService.buildScoreDto(gameId));
            outputView.printChangeTurnMessage(janggiService.getNowTurnCountry(gameId));
            playTurn(gameId);
            outputView.printBoard(janggiService.buildBoardDto(gameId), janggiService.buildColorDto(gameId));

            if (janggiService.isGameOver(gameId)) {
                break;
            }
            isGameContinue = isGameContinue();
        }
    }

    private List<PositionDto> requestMovePiece(int gameId) {
        return doRetry(() -> {
            PieceType pt = PieceType.of(inputView.requestPiece());
            List<PositionDto> dtos = janggiService.getPiecePositions(gameId, pt);
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

    private void requestEndPosition(Position start, int gameId) {
        doRetry(() -> {
                    Optional<List<Integer>> input = inputView.requestMovePosition();
                    if (input.isEmpty()) {
                        throw new IllegalArgumentException("올바르지 않은 입력입니다.");
                    }
                    List<Integer> destination = input.get();
                    Position end = Position.create(destination.getFirst(), destination.getLast());
                    janggiService.applyMove(start, end, gameId);
                    return Optional.empty();
                }
        );
    }

    private void playTurn(int gameId) {
        doRetry(() -> {
                    outputView.printBoard(janggiService.buildBoardDto(gameId), janggiService.buildColorDto(gameId));
                    List<PositionDto> positionDtos = requestMovePiece(gameId);
                    Optional<Position> start = requestStartPiecePosition(positionDtos);
                    Validator.validateDataExist(start.isPresent(), "올바르지 않은 입력입니다. 번호를 다시 입력해주세요.");

                    List<PositionDto> availableEndPositions = janggiService.buildAvailabelPositions(gameId, start.get());
                    Validator.validateDataExist(!availableEndPositions.isEmpty(), "이동 가능한 좌표가 없습니다.");
                    outputView.printPiecePossibleEndPosition(availableEndPositions);
                    requestEndPosition(start.get(), gameId);
                    return Optional.empty();
                }
        );
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

    private void checkRetryLimit(int retry) {
        if (retry > MAX_RETRY) {
            throw new IllegalStateException("입력횟수를 초과했습니다.");
        }
    }
}
