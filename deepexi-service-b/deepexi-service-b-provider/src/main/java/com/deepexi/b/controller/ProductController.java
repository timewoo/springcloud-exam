package com.deepexi.b.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.deepexi.b.extension.ApplicationException;
import com.deepexi.b.depend.DemoClient;
import com.deepexi.b.domain.eo.Product;
import com.deepexi.b.page.PageFegin;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by donh on 2018/11/5.
 */
@Api(value = "productcontroller",description = "商品管理")
@RestController
@RequestMapping("/deepexi-user/api/v1/components")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoClient demolient; // feign让跨服务调用能够看起来像本地调用

    @ApiOperation(value ="过滤价格查询所有商品",notes ="",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "size",value = "每页查询数",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "price",value = "价格",required = false,dataType = "Integer")
    })
    @GetMapping
    public Payload getProductList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                  @RequestParam(name = "price", required = false, defaultValue = "0") Integer price) {

        return new Payload(demolient.getProductList(page, size, price));
    }

    @GetMapping("/{id:[a-zA-Z0-9]+}")
    public Payload getProductById(@PathVariable("id") String id) {
        return new Payload(demolient.getProductById(id));
    }

    @PostMapping
    public Payload createProduct(@RequestBody Product product) {
        return new Payload(demolient.createProduct(product));
    }

    @PutMapping("/{id:[a-zA-Z0-9]+}")
    public Payload updateProductById(@PathVariable("id") String id, @RequestBody Product product) {
        product.setId(id);
        return new Payload(demolient.updateProduct(product));
    }

    @DeleteMapping("/{id:[a-zA-Z0-9]+}")
    public Payload deleteProductById(@PathVariable("id") String id) {
        return new Payload(demolient.deleteProductById(id));
    }

    @DeleteMapping
    public Payload bathDeleteProduct(@RequestParam("ids")List<String> ids){
        return new Payload(demolient.bathDelete(ids));
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
