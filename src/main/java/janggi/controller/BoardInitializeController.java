package janggi.controller;

import janggi.domain.Piece;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardBuilder;
import janggi.domain.board.maSangStrategy.MaSangMaSang;
import janggi.domain.board.maSangStrategy.MaSangSangMa;
import janggi.domain.board.maSangStrategy.MaSangStrategy;
import janggi.domain.board.maSangStrategy.SangMaMaSang;
import janggi.domain.board.maSangStrategy.SangMaSangMa;
import janggi.repository.Repository;
import janggi.service.PlayingTurn;
import janggi.view.BoardInitiliazeView;
import java.util.Map;
import java.util.Set;

public class BoardInitializeController {

    private final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
        1, new MaSangSangMa(),
        2, new MaSangMaSang(),
        3, new SangMaSangMa(),
        4, new SangMaMaSang()
    );

    private final BoardInitiliazeView boardInitiliazeView;
    private final Repository repository;

    public BoardInitializeController(
        final BoardInitiliazeView view,
        final Repository repository
    ) {
        this.boardInitiliazeView = view;
        this.repository = repository;
    }

    public Board initializeBoard() {
        Set<Piece> existingPieces = repository.findAll();
        if (existingPieces.isEmpty() || !boardInitiliazeView.readRenewGame()) {
            repository.clear();
            repository.updateTurn(new PlayingTurn());

            Board board = createBoard();
            board.getPieces().values().forEach(repository::save);
            return board;
        }

        boardInitiliazeView.printContinueGame();
        return new Board(existingPieces);
    }

    private Board createBoard() {
        int hanTableSetting = boardInitiliazeView.readTableSetting(Team.HAN);
        int choTableSetting = boardInitiliazeView.readTableSetting(Team.CHO);
        return createBoard(hanTableSetting, choTableSetting);
    }

    private Board createBoard(final int hanTableSetting, final int choTableSetting) {
        final var hanBoardStrategy = boardCreateStrategy.get(hanTableSetting);
        final var choBoardStrategy = boardCreateStrategy.get(choTableSetting);

        return new BoardBuilder()
            .initTeam(Team.HAN, hanBoardStrategy)
            .initTeam(Team.CHO, choBoardStrategy)
            .build();
    }
}
