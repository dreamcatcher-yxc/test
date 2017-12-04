package org.example.groovy;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxiuchu
 * @DATE 2017/11/30 15:03
 */
@Data
@EqualsAndHashCode
public class Student {
    private String username;
    private int age;
    private String gender;
    private String email;
    private String address;
}
