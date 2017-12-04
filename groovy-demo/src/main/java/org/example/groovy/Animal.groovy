package org.example.groovy

/**
 * @DATE 2017/11/30 15:20
 * @author yangxiuchu
 */
abstract class Animal {
    private String type;
    Animal(type) {
        this.type = type;
    }
    String getType() {
        return this.type;
    }
}
