package com.jixiaoliu.bean;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @ClassName CreateProductRequest
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/27 下午6:38
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @ToolParam(description = "商品的名称")
    private String productName;
    @ToolParam(description = "商品的品牌")
    private String brand;
    @ToolParam(description = "商品的简介（可以为空）")
    private String description;

    @ToolParam(description = "商品的价格")
    @Min(0)
    private Integer price;
    @ToolParam(description = "商品的库存数量")
    @Min(0)
    private Integer stock;
    @ToolParam(description = "商品的状态（上架状态的值为1/下架状态的值为0/预售状态的值为2）")
    private Integer status;
}
