package com.payvix.common.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable //it is used to specify that a class will be embedded in another entity. In this case, the Money class will be embedded in the Merchant entity. purpose of using @Embeddable is to create a reusable component that can be embedded in multiple entities, rather than creating a separate entity for each use case. This can help reduce duplication and improve maintainability of the code. t is useful in database tables where you want to represent a complex value type, such as money, as a single column or set of columns in the table. By embedding the Money class in the Merchant entity, we can store the amount and currency of the merchant's balance in a single column in the database table, rather than creating separate columns for each field.
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//in this class we are going to create a value object for money, which will have amount and currency. We will also add some utility methods to add and subtract money.
public class Money {
    private int amountUnits;
    private String currency;

    public static Money of(int amountUnits, String currency) {
        return new Money(amountUnits, currency);
    }

    public static Money inr(int amountUnits) {
        return new Money(amountUnits, "INR");
    }
//in this method we are going to add two money objects and return a new money object with the sum of the two amounts. We will also check if the currencies are the same, if not we will throw an exception.
    //difference between this.amountUnits and other.amountUnits is that this.amountUnits refers to the amount of the current object, while other.amountUnits refers to the amount of the other object that is being added or subtracted. We are using this to access the instance variable of the current object, and other to access the instance variable of the other object.
    public Money add(Money other) {
        if(!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add Money with different currencies");
        }
        return new Money(this.amountUnits + other.amountUnits, this.currency);
    }

    public Money subtract(Money other) {
        if(!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract Money with different currencies");
        }
        return new Money(this.amountUnits - other.amountUnits, this.currency);
    }
}

