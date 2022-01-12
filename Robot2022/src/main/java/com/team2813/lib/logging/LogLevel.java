package com.team2813.lib.logging;

import java.io.PrintStream;

public enum LogLevel {
    DEBUG    (0, System.out),
    INFO     (1, System.out),
    WARNING  (2, System.out),
    ERROR    (3, System.err),
    CRITICAL (4, System.err);

    private final int level;

    final PrintStream printStream;

    private LogLevel(int level, PrintStream printStream) {
        this.level = level;
        this.printStream = printStream;
    }

    boolean canSee(LogLevel other){
        return this.level <= other.level;
    }

    public void logDelim(String delimeter, Object first, Object...objects){
        Logger.logDelim(this, delimeter, first, objects);
    }

    public void log(Object first, Object...objects){
        Logger.log(this, first, objects);
    }

    public void log(Throwable t) {
        Logger.log(this, t);
    }

    public void logf(String format, Object...args){
        Logger.logf(this, format, args);
    }

    public void debugLog(Object first, Object...objects){
        Logger.debugLog_(this, 2, first, objects);
    }
}
