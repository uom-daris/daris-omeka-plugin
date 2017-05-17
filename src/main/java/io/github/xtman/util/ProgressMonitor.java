package io.github.xtman.util;

public interface ProgressMonitor {

    long total();

    void setTotal(long total);

    void progressed(long increment);

}
