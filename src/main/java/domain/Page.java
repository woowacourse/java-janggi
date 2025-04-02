package domain;

import domain.dao.JanggiGameDao;
import domain.dto.GameRoomDto;
import view.PageCommand;

import java.util.List;

public class Page {
    public final static int START_PAGE = 0;
    public final static int PAGE_INTERVAL = 5;

    private final int currPage;

    public Page(final int currPage) {
        this.currPage = currPage;
    }

    public Page movePage(final PageCommand pageCommand) {
        int after = this.currPage + pageCommand.getMove();
        if (after < 0) {
            return this;
        }
        return new Page(after);
    }

    public List<GameRoomDto> getCurrPageGames(final JanggiGameDao gameDao) {
        return gameDao.findGames(this.currPage * PAGE_INTERVAL, PAGE_INTERVAL);
    }

    public int getCurrPage() {
        return currPage;
    }
}
