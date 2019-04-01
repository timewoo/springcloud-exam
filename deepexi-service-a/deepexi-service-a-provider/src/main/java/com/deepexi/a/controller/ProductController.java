package com.deepexi.a.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.deepexi.a.extension.ApplicationException;
import com.deepexi.a.service.ProductService;
import com.deepexi.a.depend.DemoClient;
import com.deepexi.a.domain.eo.Product;
import com.deepexi.util.config.Payload;

import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by donh on 2018/11/5.
 */
@Api(value = "productcontroller",description = "商品管理")
@RestController
@RequestMapping("/provider")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    @ApiOperation(value ="过滤价格查询所有商品",notes ="",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "size",value = "每页查询数",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "price",value = "价格",required = false,dataType = "Integer")
    })
    @GetMapping(value = "/getProductList")
    public PageBean<Product> getProductList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                   @RequestParam(name = "price", required = false, defaultValue = "0") Integer price) {
        return productService.getProductList(page, size, price);
    }

    @ApiOperation(value ="根据Id查询商品",notes ="",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品标识",required = false,dataType = "String")
    })
    @GetMapping(value = "/getProductById")
    public Product getProductById(@RequestParam("id") String id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value ="新增商品",notes ="",httpMethod = "post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品标识",required = false,dataType = "String")
    })
    @PostMapping(value = "/add")
    public Integer createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id:[a-zA-Z0-9]+}")
    public Integer updateProductById(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping(value = "/delete")
    public Boolean deleteProductById(@RequestParam("id") String id) {
        return productService.deleteProductById(id);
    }

    @DeleteMapping(value = "/bathDelete")
    public Boolean bathDeleteProduct(@RequestParam("ids")List<String> ids){
        return productService.bathDeleteProductByIds(ids);
    }


    @GetMapping("/testSentinel")
    @SentinelResource(value = "testSentinel", blockHandler = "exceptionHandler")
    public Payload testSentinel() {
        logger.info("远程Sentinel测试接口成功: Hello World!!");
        return new Payload(true);
    }

    /**
     * 熔断降级处理逻辑
     * @param s
     * @param ex
     * @return
     */
    public Payload exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        logger.info("-------------熔断降级处理逻辑---------\n");
        throw new ApplicationException("100", "熔断降级处理");
    }

}
