package com.example.demo.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    /*
     * 解决低版本ie responsebody返回json的时候提示下载问题
     * @return

    public FastJsonHttpMessageConverter getFastJsonHttpMessageConverter() {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2处理ie浏览器保存数据时出现下载json数据问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.TEXT_PLAIN);
        //3.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        return fastJsonHttpMessageConverter;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getFastJsonHttpMessageConverter());
    }*/
}