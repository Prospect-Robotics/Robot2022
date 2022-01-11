package com.team2813.lib.controls;

public abstract class ControlFilter implements ControlInput {
    ControlInput next;

    public ControlFilter (ControlInput next) {
        this.next = next;
    }
}
