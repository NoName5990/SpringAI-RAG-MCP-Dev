package com.jixiaoliu.bean;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @ClassName ModifyProductRequest
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/28 上午9:13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModifyProductRequest {

    @ToolParam(description = "商品的编号", required = false)
    private String productId;
    @ToolParam(description = "商品的名称", required = false)
    private String productName;
    @ToolParam(description = "商品的品牌", required = false)
    private String brand;
    @ToolParam(description = "商品的简介", required = false)
    private String description;
    @ToolParam(description = "具体商品价格大小", required = false)
    @Min(0)
    private Integer price;
    @ToolParam(description = "商品的库存数量", required = false)
    @Min(0)
    private Integer stock;
    @ToolParam(description = "商品的状态（上架状态的值为1/下架状态的值为0/预售状态的值为2）", required = false)
    private Integer status;

}
