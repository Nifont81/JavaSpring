package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Client;
import ru.geekbrains.persist.ClientRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main06 {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration() // Входная точка Hibernate
                .configure("hibernate.cfg.xml") // emF - потокобезопасный
                .buildSessionFactory();

        ProductRepository pr = new ProductRepository(emFactory);
        ClientRepository cl = new ClientRepository(emFactory);

//        Product product = new Product("Клавиатура 101 кл","A4", 1100);
//        pr.insert(product);
//
//        Client client = new Client("Клиент 1");
//        cl.insert(client);

//        product = new Product("Мышь a4","Мышь Genius", 500);
//        product.setId(3L);
//        pr.update(product);
//        pr.delete(15);
//        System.out.println(pr.findById(4));

//        System.out.println(pr.findAll());
//
//

        // Получаем список всех товаров, купленных покупателем с id=1:
        List<Product> listPr = pr.findProductsByClientId(1);
        System.out.println(listPr);
        listPr = pr.findProductsByClientId(2);
        System.out.println(listPr);

        // Получаем список всех клиентов, купивших товар с id=2:
        List<Client> listCl = cl.findProductsByClientId(2);
        System.out.println(listCl);

        pr.close();
        cl.close();


        emFactory.close();
    }
}
/* Результаты:
mysql> select * from clients;
+----+----------+
| id | name     |
+----+----------+
|  1 | Клиент 1 |
|  2 | Клиент 2 |
|  3 | Клиент 3 |
|  4 | Клиент 4 |
|  5 | Клиент 5 |
+----+----------+
mysql> select * from products;
+----+----------------+-------------------+-------+------------+
| id | description    | name              | price | product_id |
+----+----------------+-------------------+-------+------------+
|  1 | Intel i7-9700K | Процессор i7      | 25000 |       NULL |
|  2 | A4             | Клавиатура 101 кл |  1100 |       NULL |
|  3 | MSI 370H       | Материнская плата |  7000 |       NULL |
|  4 | Genius         | Мышь              |   450 |       NULL |
+----+----------------+-------------------+-------+------------+
mysql> select * from clients_products;
+-----------+------------+
| client_id | product_id |
+-----------+------------+
|         1 |          2 |
|         1 |          3 |
|         2 |          2 |
|         2 |          3 |
|         2 |          4 |
+-----------+------------+

Hibernate:
    SELECT
        *
    FROM
        products as p
    WHERE
        p.id IN (
            SELECT
                product_id
            FROM
                clients_products
            WHERE
                client_id=?
        )
[Product{id=2, name=Клавиатура 101 кл, description=A4, price=1100
, Product{id=3, name=Материнская плата, description=MSI 370H, price=7000
]
Hibernate:
    SELECT
        *
    FROM
        products as p
    WHERE
        p.id IN (
            SELECT
                product_id
            FROM
                clients_products
            WHERE
                client_id=?
        )
[Product{id=2, name=Клавиатура 101 кл, description=A4, price=1100
, Product{id=3, name=Материнская плата, description=MSI 370H, price=7000
, Product{id=4, name=Мышь, description=Genius, price=450
]
Hibernate:
    SELECT
        *
    from
        clients as c
    where
        c.id IN (
            SELECT
                client_id
            FROM
                clients_products
            where
                product_id=?
        )
[Client {id=1, name='Клиент 1}, Client {id=2, name='Клиент 2}]
 */
