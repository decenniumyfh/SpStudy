package com.yang.es.bean;


import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private Integer num;

    private UserName userName;

    private Boolean sex;

    private String schoolName;

    private Integer grade;

    private Date joinDate;
    @Data
    public class UserName{
        private String first;
        private String last;
    }


}
