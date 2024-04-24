package id.ac.ui.cs.advprog.cart.enums;

import lombok.Getter;

@Getter
public enum Availability {
    PREORDER("PREORDER"),
    READY("READY");

    private final String value;

    Availability(String value) {
        this.value = value;
    }
}