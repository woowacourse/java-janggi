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
import janggi.view.BoardInitiliazeView;
import java.util.Collection;
import java.util.Map;

public class BoardInitializer {

    private final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
        1, new MaSangSangMa(),
        2, new MaSangMaSang(),
        3, new SangMaSangMa(),
        4, new SangMaMaSang()
    );

    private final BoardInitiliazeView boardInitiliazeView;

    public BoardInitializer(final BoardInitiliazeView view) {
        this.boardInitiliazeView = view;
    }

    public void initializeRepository(Repository repository) {
        boolean remainingPieces = repository.allPieces().isEmpty();
        if (!remainingPieces && boardInitiliazeView.readRenewGame()) {
            boardInitiliazeView.printContinueGame();
            return;
        }

        repository.clear();

        Board board = createBoard();
        Collection<Piece> pieces = board.getPieces().values();
        repository.saveAll(pieces);
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
