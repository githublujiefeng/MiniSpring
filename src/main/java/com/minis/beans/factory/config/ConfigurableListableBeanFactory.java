package com.minis.beans.factory.config;

import com.minis.beans.factory.annotation.AutowiredCapableBeanFactory;

public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowiredCapableBeanFactory, ConfigurableBeanFactory {
}
