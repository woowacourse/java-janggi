package janggi.domain;

import io.hypersistence.tsid.TSID;

public class IdGenerator {

    private IdGenerator() {}

    public static Long createId() {
        return TSID.fast().toLong();
    }
}
