package domain.Manager;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.Piece;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class GameManager {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    private Player choPlayer;
    private Player hanPlayer;

    public void run() {
        Board board = initialize();
        outputView.printBoard(board.createDTO().board());
        //for문 (턴마다 반복)
        Position src = createPosition();
        //src의 피스가 같은팀인지 판단하기. 아니면 재입력.
        Position dest = createPosition();
        // 이동 - 잡았으면 플레이어에 추가
        if(board.canMove(src, dest)) {
            Piece piece = board.move(src,dest);
            if(!piece.isNone()) {
                //add.
            }
        }
        outputView.printBoard(board.createDTO().board());
        // 턴 넘기기

    }

    private <T> T retryOnInvalidInput(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position createPosition() {
        return retryOnInvalidInput(() -> {
            List<Integer> numbers = inputView.askSourcePosition();
            return new Position(numbers.getFirst(), numbers.getLast());
        });
    }

    private Board initialize() {
        String choName = inputView.askChoPlayerName();
        choPlayer = createPlayer(choName, CHO);

        String hanName = inputView.askHanPlayerName();
        hanPlayer = createPlayer(hanName, HAN);

        int choPositionInput = inputView.askChoPositionInput();
        Formation choFormation = createFormation(choPositionInput);

        int hanPositionInput = inputView.askHanPositionInput();
        Formation hanFormation = createFormation(hanPositionInput);

        return BoardFactory.createWithFormation(choFormation, hanFormation);
    }

//    private Position getSourcePosition() {
//        inputView.
//    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
