package controller;

import domain.JanggiBoard;
import domain.JanggiBoardInitializer;
import domain.dto.JanggiBoardDto;
import domain.piece.MoveablePiece;
import domain.position.Position;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiBoard janggiBoard = new JanggiBoard(new JanggiBoardInitializer());
        while (true) {
            try {
                outputView.printBoard(JanggiBoardDto.from(janggiBoard));
                Position from = inputMovePosition();
                Position to = inputTargetPosition();
                if (janggiBoard.isBlank(from)) {
                    throw new IllegalArgumentException("[ERROR] 해당 위치에는 기물이 존재하지 않습니다.");
                }
                MoveablePiece currentPiece = (MoveablePiece) janggiBoard.getPiece(from);
                if (currentPiece.getTeam() != janggiBoard.getTurn()) {
                    throw new IllegalArgumentException("[ERROR] 상대방의 기물을 이동할 수 없습니다.");
                }
                boolean movePiece = currentPiece.canMove(from, to, janggiBoard);
                if (!movePiece) {
                    throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");
                }
                janggiBoard.move(from, to, currentPiece);
                outputView.printBoard(JanggiBoardDto.from(janggiBoard));
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private Position inputMovePosition() {
        String inputMovePosition = inputView.inputMovePiece();
        String[] parts = inputMovePosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }

    private Position inputTargetPosition() {
        String inputTargetPosition = inputView.inputTargetPosition();
        String[] parts = inputTargetPosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }
}
