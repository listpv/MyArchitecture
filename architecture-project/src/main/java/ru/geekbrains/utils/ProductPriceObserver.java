package ru.geekbrains.utils;

import lombok.extern.java.Log;

@Log
public class ProductPriceObserver implements Observer{

    @Override
    public void update(Subject subject, Object arg) {
        log.info("Price " + arg + "\n");
    }
}
