package org.project.tests;

import org.project.*;
import org.project.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

public class Tests {
    public static void main(String[] args) {
        creatingAndCheckingCorrectDisplayOfAllProducts();
        testingProductManager();
    }

    private static void creatingAndCheckingCorrectDisplayOfAllProducts() {
        // Tworzenie obiektów klasy RAM
        RAM ram1 = new RAM("HyperX", 8, RamUnit.GB);
        RAM ram2 = new RAM("Corsair", 16, RamUnit.GB);
        RAM ram3 = new RAM("Kingston", 32, RamUnit.GB);

        // Tworzenie akcesoriów
        Accessory accessory1 = new Accessory("Etui", new BigDecimal(29), "Iphone 14 Pro", "Skórzane");
        Accessory accessory2 = new Accessory("Etui", new BigDecimal(99), "Iphone 16 Pro", "Plastikowe");

        // Tworzenie procesorów
        Processor processor1 = new Processor("Intel", "i7", 12, CoresUnit.GHz);
        Processor processor2 = new Processor("Intel", "i8", 12, CoresUnit.GHz);
        Processor processor3 = new Processor("Intel", "i9", 12, CoresUnit.GHz);

        // Tworzenie komputerów
        Product computer1 = new Computer(UUID.randomUUID(), "MacBook Air", new BigDecimal(1200), 10, processor1, ram1);
        Product computer2 = new Computer(UUID.randomUUID(), "MacBook Air", new BigDecimal(1200), 20, processor2, ram2);
        Product computer3 = new Computer(UUID.randomUUID(), "MacBook Pro", new BigDecimal(1200), 30, processor3, ram3);

        // Tworzenie obiektu klasy Electronics
        Product electronics = new Electronics(UUID.randomUUID(), "Samsung TV", new BigDecimal(5999), 20);

        // Tworzenie smartfonów
        Product smartphone1 = new Smartphone(UUID.randomUUID(), "Iphone 14 Pro", new BigDecimal(4999), 50, Color.PINK, 2500, accessory1);
        Product smartphone2 = new Smartphone(UUID.randomUUID(), "Iphone 16 Pro", new BigDecimal(7999), 40, Color.RED, 4500, accessory2);

        System.out.println(computer1);
        System.out.println(computer2);
        System.out.println(computer3);
        System.out.println(electronics);
        System.out.println(smartphone1);
        System.out.println(smartphone2);

    }

    private static void testingProductManager() {
        ProductManager productManager = new ProductManager();

        // Tworzenie produktów
        RAM ram1 = new RAM("HyperX", 8, RamUnit.GB);
        Processor processor1 = new Processor("Intel", "i7", 12, CoresUnit.GHz);
        Accessory accessory1 = new Accessory("Etui", new BigDecimal(29), "Iphone 14 Pro", "Skórzane");
        Product computer1 = new Computer(UUID.randomUUID(), "MacBook Air", new BigDecimal(1200), 10, processor1, ram1);
        Product smartphone1 = new Smartphone(UUID.randomUUID(), "Iphone 14 Pro", new BigDecimal(4999), 50, Color.PINK, 2500, accessory1);
        Product electronics = new Electronics(UUID.randomUUID(), "Samsung TV", new BigDecimal(5999), 20);

        // Dodawanie produktów
        productManager.addProduct(computer1);
        productManager.addProduct(smartphone1);
        productManager.addProduct(electronics);

        // Wyświetlenie wszystkich produktów
        System.out.println("Lista produktów po dodaniu:");
        productManager.getAllProducts().forEach(System.out::println);

        // Wyszukiwanie produktu po ID
        try {
            Product foundProduct = productManager.getProductById(computer1.getId());
            System.out.println("\nZnaleziono produkt: " + foundProduct);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Aktualizacja produktu
        productManager.updateProduct(smartphone1.getId(), "Smartphone Pro", new BigDecimal("2500.00"), 7);
        System.out.println("\nLista produktów po aktualizacji:");
        productManager.getAllProducts().forEach(System.out::println);

        // Usunięcie produktu
        productManager.removeProduct(electronics.getId());
        System.out.println("\nLista produktów po usunięciu:");
        productManager.getAllProducts().forEach(System.out::println);

        // Próba pobrania nieistniejącego produktu
        try {
            productManager.getProductById(UUID.randomUUID());
        } catch (ProductNotFoundException e) {
            System.out.println("\nBłąd: " + e.getMessage());

        }
    }
}