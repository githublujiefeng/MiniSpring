package com.minis.test;

import com.minis.beans.factory.annotation.Autowired;

public class BaseBaseService {
    @Autowired
    private AServiceImpl as;

    public AServiceImpl getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }
}
