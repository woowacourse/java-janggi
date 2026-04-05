package domain.janggigame;

import domain.board.Board;
import domain.board.BoardStateFactory;
import domain.board.Placement;
import domain.board.Setup;
import domain.piece.Side;
import domain.position.Movement;
import domain.position.Position;
import service.BoardService;
import service.PlayerService;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.util.Retry.retry;

public class JanggiGame {
    private final BoardService boardService;
    private final PlayerService playerService;

    public JanggiGame(BoardService boardService, PlayerService playerService) {
        this.boardService = boardService;
        this.playerService = playerService;
    }

    public void run() {
        selectSide();
        placeBoardBySide();
        playGame();
    }

    private void selectSide() {
        retry(() -> {
            int sideCode = InputView.inputSideChoice();
            Side side = generateSide(sideCode);
            OutputView.printSideChoiceResult(side);
        });
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);
        return sides.get(sideCode - 1);
    }

    private void placeBoardBySide() {
        retry(() -> {
            Setup hanSetup = getSetup(Side.HAN);
            Setup choSetup = getSetup(Side.CHO);
            boardService.initialState(new BoardStateFactory(hanSetup, choSetup));
            OutputView.printBoard(boardService.findState());
        });
    }

    private Setup getSetup(Side side) {
        int hanPlacementCode = InputView.inputPlacementCodeBy(side);
        return Placement.from(hanPlacementCode).getSetup();
    }

    private void playGame() {
        retry(() -> {
            while (!boardService.isFinished()) {
                Movement movement = inputAndParseToMove();
                Side currentTurn = playerService.getWhoseTurn();
                // TODO: 보드 움직인 후 보드 현황 저장 (BoardService 로 감싸서 보드 현황과 repository 소유)
                // TODO: 진영별 점수 계산 후 점수 저장 (PlayerService 로 감싸서 플레이어 점수와 턴 관리 repository 소유)
                boardService.move(movement, currentTurn);
                // TODO: 현재 턴에 대한 정보 저장
                playerService.switchTurn();
                OutputView.printBoard(boardService.findState());
            }
        });
    }

    private Movement inputAndParseToMove() {
        Position startPosition = InputView.inputStartPosition();
        Position endPosition = InputView.inputEndPosition();
        return new Movement(startPosition, endPosition);
    }
}
