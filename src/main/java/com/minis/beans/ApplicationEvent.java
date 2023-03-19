package com.minis.beans;

import java.io.Serializable;
import java.util.EventObject;

public class ApplicationEvent extends EventObject implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
