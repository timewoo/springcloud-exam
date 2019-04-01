package com.deepexi.b.depend;

import com.deepexi.b.domain.eo.Product;
import com.deepexi.b.page.PageFegin;
import com.deepexi.util.config.Payload;
import com.deepexi.util.pageHelper.PageBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by donh on 2018/11/5.
 */
@FeignClient(value = "deepexi-service-a-provider",path = "/deepexi-service-a")
public interface DemoClient {

    @RequestMapping(method = RequestMethod.GET,value = "/provider/getProductList")
    PageFegin<Product> getProductList(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size, @RequestParam(value = "price") Integer price);

    @RequestMapping(method = RequestMethod.GET,value = "/provider/getProductById")
    Product getProductById(@RequestParam("id") String id);

    @RequestMapping(method = RequestMethod.POST,value = "/provider/add")
    Integer createProduct(@RequestBody Product product);

    @RequestMapping(method = RequestMethod.PUT,value = "/provider/update")
    Integer updateProduct(@RequestBody Product product);

    @RequestMapping(method = RequestMethod.DELETE,value = "/provider/delete")
    Boolean deleteProductById(@RequestParam("id") String id);

    @RequestMapping(method = RequestMethod.DELETE,value = "/provider/bathDelete")
    Boolean bathDelete(@RequestParam("ids")List<String> ids);

}