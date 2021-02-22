package ru.geekbrains.utils;

public class ProductPriceObserver implements Observer{
    @Override
    public void update(Subject subject, Object arg) {
        System.out.printf("Price %s\n", arg);
    }
}
