package ru.geekbrains.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPriceObserver implements Observer{

    private static final Logger logger = LoggerFactory.getLogger(ProductPriceObserver.class);
    @Override
    public void update(Subject subject, Object arg) {
        logger.info("Price " + arg + "\n");
    }
}
