package org.project;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID orderId;
    private Customer customer;
    private Cart cart;
    private List<Product> products;
    private BigDecimal totalPrice;
    private ZonedDateTime orderTime;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal finalPrice;

    public Order(UUID orderId, Customer customer, Cart cart, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.customer = customer;
        this.cart = cart;
        this.totalPrice = cart.calculateTotalPrice();
        this.orderTime = ZonedDateTime.now();
    }

    public ZonedDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(ZonedDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void updateOrderTime() {
        this.orderTime = orderTime;
    }

    public void markOrderAsProcessed() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime timeNow = ZonedDateTime.now();
        System.out.println("Order time (" + zoneId + "): " + timeNow);
        this.orderTime = OffsetDateTime.of(LocalDateTime.from(timeNow), zoneId.getRules().getOffset(Instant.from(timeNow))).atZoneSameInstant(ZoneId.of("Europe/Warsaw"));
        System.out.println("Converted order time (Europe/Warsaw): " + this.orderTime);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void applyPercentageDiscount(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) <= 0 || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Invalid discount percentage. Must be between 0 and 100.");
        }
        BigDecimal discount = totalPrice.multiply(percentage).divide(new BigDecimal("100"));
        totalPrice = totalPrice.subtract(discount);
    }

    public void applyFixedDiscount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(totalPrice) > 0) {
            throw new IllegalArgumentException("Invalid discount amount. Must be greater than 0 and less than total price.");
        }
        totalPrice = totalPrice.subtract(amount);
    }

    private void updateFinalPrice() {
        this.finalPrice = cart.calculateTotalPrice().subtract(discountAmount);
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void applyPromotions() {
        if (totalPrice.compareTo(new BigDecimal("500")) > 0) {
            applyPercentageDiscount(new BigDecimal("10"));
        }
    }


    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderID=" + orderId +
                ", customer data=" + customer +
                ", cart items=" + cart +
                ", total amount=" + cart.calculateTotalPrice() +
                ", discount applied=" + discountAmount +
                ", final amount=" + finalPrice +
                '}';
    }
}