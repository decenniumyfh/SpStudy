package com.yang.simulator.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private String account;

    private String name;
    private String password;
}
