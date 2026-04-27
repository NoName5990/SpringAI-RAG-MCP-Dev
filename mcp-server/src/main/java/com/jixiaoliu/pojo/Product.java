package com.jixiaoliu.pojo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName Product
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午11:17
 */
@Data
@ToString
public class Product {
    private String productId;
    private String productName;
    private String brand;
    private String description;

    private Integer price;
    private Integer stock;
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}