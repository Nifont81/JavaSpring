package ru.geekbrains;

import ru.geekbrains.persist.TrashRepository;

public class testTrash {
    public static void main(String[] args) {
        TrashRepository tr = new TrashRepository();

        tr.addProduct(1,2);
        tr.addProduct(1,5);
        tr.addProduct(1,25);
        tr.addProduct(2,3);
        tr.addProduct(2,4);
        tr.addProduct(3,15);

        System.out.println(tr.getProductsFromUser(1));
        System.out.println(tr.getProductsFromUser(2));
        System.out.println(tr.getProductsFromUser(3));

    }
}
