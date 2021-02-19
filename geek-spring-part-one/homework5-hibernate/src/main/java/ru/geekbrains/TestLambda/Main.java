package ru.geekbrains.TestLambda;

import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        Consumer<String> printer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        printer.accept("Test string");

        Consumer<String> pr1 = s -> System.out.println(s);
        pr1.accept("Test pr1");

        Consumer<String> pr2 = System.out::println;
        pr2.accept("Test pr2");
    }

}
