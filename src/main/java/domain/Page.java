package domain;

import view.PageCommand;

public class Page {
    public final static int START_PAGE = 0;
    public final static int PAGE_INTERVAL = 5;

    private final int currPage;

    public Page(int currPage) {
        this.currPage = currPage;
    }

    public Page movePage(PageCommand pageCommand) {
        int after = this.currPage + pageCommand.getMove();
        if (after < 0) {
            return this;
        }
        return new Page(after);
    }

    public int getCurrPage() {
        return currPage;
    }
}
