package demo;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import models.PersonModel;
import models.ProductModel;

import entities.Person;
import entities.Product;

import java.time.LocalDate;

public class AdminApp {
    private static final String PERSISTENCE_UNIT = "admin-jpa";

    public static void main(String[] args) {
        Person naruto = new Person(
            "Naruto Uzumaki",
            "narutouzumaki@mail.com",
            "000.000.000-00",
            LocalDate.of(1993, 10, 4)
        );

        Person sasuke = new Person(
            "Sasuke Uchiha",
            "sasukeuchiha@mail.com",
            "000.000.000-00",
            LocalDate.of(1993, 6, 17)
        );

        Product kunai = new Product("Kunai", 15, 5.0d, true);
        Product ninjaStar = new Product("Ninja star", 30, 2.0d, true);

        try (
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                PERSISTENCE_UNIT
            )
        ) {
            PersonModel personModel = new PersonModel(entityManagerFactory);
            ProductModel productModel = new ProductModel(entityManagerFactory);

            personModel.create(naruto);
            personModel.create(sasuke);

            productModel.create(kunai);
            productModel.create(ninjaStar);

            System.out.println(
                personModel.findById(naruto.getId()).orElseThrow(
                    () -> new RuntimeException("person not found")
                )
            );
            System.out.println(
                personModel.findById(sasuke.getId()).orElseThrow(
                    () -> new RuntimeException("person not found")
                )
            );

            System.out.println();
            System.out.println(
                productModel.findById(kunai.getId()).orElseThrow(
                    () -> new RuntimeException("product not found")
                )
            );
            System.out.println(
                productModel.findById(ninjaStar.getId()).orElseThrow(
                    () -> new RuntimeException("product not found")
                )
            );

            System.out.println();
            System.out.println(personModel.findAll());
            System.out.println(productModel.findAll());

            personModel.deleteById(sasuke.getId());
            productModel.deleteById(ninjaStar.getId());

            System.out.println();
            System.out.println(personModel.findAll());
            System.out.println(productModel.findAll());

            naruto.setCpf("111.111.111-11");
            kunai.setQuantity(20);

            naruto = personModel.update(naruto);
            kunai = productModel.update(kunai);

            System.out.println();
            System.out.println(personModel.findAll());
            System.out.println(productModel.findAll());

            System.out.println();

            boolean isPersonEquals = naruto.equals(
                personModel.findById(naruto.getId()).orElseThrow(
                    () -> new RuntimeException("person not found")
                )
            );
            boolean isProductEquals = kunai.equals(
                productModel.findById(kunai.getId()).orElseThrow(
                    () -> new RuntimeException("product not found")
                )
            );

            if (isPersonEquals) {
                System.out.println("equals");
            } else {
                System.out.println("not equals");
            }

            if (isProductEquals) {
                System.out.println("equals");
            } else {
                System.out.println("not equals");
            }
        }
    }
}