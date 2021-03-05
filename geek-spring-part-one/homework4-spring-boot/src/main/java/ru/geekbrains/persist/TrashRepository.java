package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TrashRepository {
    public static long CURRENT_ID = 0;
    private List<Trash> trashList = new ArrayList<>();

    public TrashRepository() {
    }

    public void addProduct(long userId, long productId) {
        trashList.add(new Trash(CURRENT_ID, userId, productId));
        CURRENT_ID++;
    }

    public List<Long> getProductsFromUser(long userId) {

        List<Long> products = trashList.stream()
                .filter(trash -> trash.getUserId() == userId)
                .map(Trash::getProductId)
                .collect(Collectors.toList());

//        for (Trash trash: trashList) {
//            if (trash.getUserId() == userId) products.add(trash.getProductId());
//        }
        return products;
    }

    public List<Long> getUsersFromProduct(long productId) {

        List<Long> users = trashList.stream()
                .filter(trash -> trash.getProductId() == productId)
                .map(Trash::getUserId)
                .collect(Collectors.toList());

//        for (Trash trash: trashList) {
//            if (trash.getUserId() == userId) products.add(trash.getProductId());
//        }
        return users;
    }

    public void clearFromUserId(long userId) {
        Iterator<Trash> iter = trashList.iterator();
        while(iter.hasNext()) {

            Trash nextTrash = iter.next();
            if (nextTrash.getUserId() == userId) {
                iter.remove();
            }
        }
    }
}
