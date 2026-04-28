package com.jixiaoliu.mcp.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jixiaoliu.bean.CreateProductRequest;
import com.jixiaoliu.bean.QueryProductRequest;
import com.jixiaoliu.enums.ListSortEnum;
import com.jixiaoliu.enums.PriceCompareEnum;
import com.jixiaoliu.mapper.ProductMapper;
import com.jixiaoliu.pojo.Product;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ProductTool
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/27 下午6:37
 */
@Component
@Slf4j
public class ProductTool implements McpTool{
    @Resource
    private ProductMapper productMapper;

    /**
     * @Description: 创建/新增商品信息记录
     * @Date 2026/4/27 下午11:44
     * @Author liujxiao
     * @param createProductRequest
     * @return java.lang.String
     */
    @Tool(description = "创建/新增商品信息记录")
    public String createNewProduct(@ToolParam(description = "商品信息") CreateProductRequest createProductRequest) {

        log.info("========== 调用MCP工具：createNewProduct() ==========");
        log.info(String.format("| 参数 createProductRequest 为： %s", createProductRequest.toString()));
        log.info("========== End ==========");

        Product product = new Product();
        BeanUtils.copyProperties(createProductRequest, product);

        // 生成12为的随机数字
        product.setProductId(RandomStringUtils.randomNumeric(12));

        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());

        productMapper.insert(product);

        return "商品信息创建成功";
    }

    /**
     * @Description: 删除商品信息
     * @Date 2026/4/27 下午11:44
     * @Author liujxiao
     * @param productId
     * @return java.lang.String
     */
    @Transactional
    @Tool(description = "删除商品信息")
    public String deleteProduct(@ToolParam(description = "商品编号") String productId) {

        log.info("========== 调用MCP工具：deleteProduct() ==========");
        log.info(String.format("| 删除商品信息，参数 productId 为： %s", productId));
        log.info("========== End ==========");

        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId);

        int deleted = productMapper.delete(wrapper);
        if (deleted == 0) {
            return "未找到该商品信息，删除失败";
        }
        return "商品信息删除成功";
    }

    @Tool(description = "把排序（正序/倒序）转为对应的枚举")
    public ListSortEnum getSortEnum(String sort) {
        log.info("========== 调用MCP工具：getSortEnum() ==========");
        log.info(String.format("| 参数 sort 为： %s", sort));
        log.info("========== End ==========");

        if (sort.equalsIgnoreCase(ListSortEnum.ASC.value)) {
            return ListSortEnum.ASC;
        } else {
            return ListSortEnum.DESC;
        }
    }

    @Tool(description = "把商品价格的比较（大于/小于/大于等于/小于等于/高于/低于/不高于/不低于/等于）转换为对应的枚举")
    public PriceCompareEnum getPriceCompareEnum(String priceCompare) {

        log.info("========== 调用MCP工具：getPriceCompareEnum() ==========");
        log.info(String.format("| 参数 priceCompare 为： %s", priceCompare));
        log.info("========== End ==========");

        if (priceCompare.equalsIgnoreCase(PriceCompareEnum.GREATER_THAN.value)) {
            return PriceCompareEnum.GREATER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LESS_THAN.value)) {
            return PriceCompareEnum.LESS_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO.value)) {
            return PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LESS_THAN_OR_EQUAL_TO.value)) {
            return PriceCompareEnum.LESS_THAN_OR_EQUAL_TO;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.HIGHER_THAN.value)) {
            return PriceCompareEnum.HIGHER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LOWER_THAN.value)) {
            return PriceCompareEnum.LOWER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.NOT_HIGHER_THAN.value)) {
            return PriceCompareEnum.NOT_HIGHER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.NOT_LOWER_THAN.value)) {
            return PriceCompareEnum.NOT_LOWER_THAN;
        } else {
            return PriceCompareEnum.EQUAL_TO;
        }

    }

    /**
     * @Description: 根据条件查询商品信息
     * @Date 2026/4/27 下午11:44
     * @Author liujxiao
     * @param queryProductRequest
     * @return java.util.List<com.jixiaoliu.pojo.Product>
     */
    @Tool(description = "根据条件查询商品（product）信息")
    public List<Product> queryProductListByCondition(@ToolParam(description = "查询条件") QueryProductRequest queryProductRequest) {

        log.info("========== 调用MCP工具：queryProductListByCondition() ==========");
        log.info(String.format("| 参数 queryProductRequest 为： %s", queryProductRequest.toString()));
        log.info("========== End ==========");

        String productId = queryProductRequest.getProductId();
        String productName = queryProductRequest.getProductName();
        String brand = queryProductRequest.getBrand();

        Integer status = queryProductRequest.getStatus();
        ListSortEnum sortEnum = queryProductRequest.getSortEnum();

        Integer price = queryProductRequest.getPrice();
        PriceCompareEnum priceCompareEnum = queryProductRequest.getPriceCompareEnum();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(productId)) {
            queryWrapper.eq("product_id", productId);
        }
        if (StringUtils.isNotBlank(productName)) {
            queryWrapper.like("product_name", productName);
        }
        if (StringUtils.isNotBlank(brand)) {
            queryWrapper.like("brand", brand);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper = priceWrapper(queryWrapper, priceCompareEnum, price);


        if (sortEnum != null && sortEnum.type.equals(ListSortEnum.ASC.type)) {
            queryWrapper.orderByAsc("price");
        }
        if (sortEnum != null && sortEnum.type.equals(ListSortEnum.DESC.type)) {
            queryWrapper.orderByDesc("price");
        }

        List<Product> productList = productMapper.selectList(queryWrapper);

        return productList;
    }

    /**
     * @Description: 封装价格
     * @Date 2026/4/27 下午11:42
     * @Author liujxiao
     * @param queryWrapper
     * @param priceCompareEnum
     * @param price
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.jixiaoliu.pojo.Product>
     */
    private QueryWrapper<Product> priceWrapper(QueryWrapper<Product> queryWrapper,
        PriceCompareEnum priceCompareEnum, Integer price) {
        if (price != null && priceCompareEnum != null) {
            if (priceCompareEnum.type.equals(PriceCompareEnum.GREATER_THAN.type)) {
                queryWrapper.gt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LESS_THAN.type)) {
                queryWrapper.lt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO.type)) {
                queryWrapper.ge("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LESS_THAN_OR_EQUAL_TO.type)) {
                queryWrapper.le("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.HIGHER_THAN.type)) {
                queryWrapper.gt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LOWER_THAN.type)) {
                queryWrapper.lt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.NOT_HIGHER_THAN.type)) {
                queryWrapper.le("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.NOT_LOWER_THAN.type)) {
                queryWrapper.ge("price", price);
            } else {
                queryWrapper.eq("price", price);
            }
        }
        return queryWrapper;
    }
}
