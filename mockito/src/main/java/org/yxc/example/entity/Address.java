package org.yxc.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by yangxiuchu on 2017/12/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String province;
    private String city;
    private String detail;
}
