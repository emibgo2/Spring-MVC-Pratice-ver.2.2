package hello.exception;

import hello.exception.errorProcess.filter.LogFilter;
import hello.exception.errorProcess.interceptor.LogInterceptor;
import hello.exception.errorProcess.resolver.MyHandlerExceptionResolver;
import hello.exception.errorProcess.resolver.UserHandlerExceptionResolver;
import hello.exception.typeconverter.converter.IntegerToStringConverter;
import hello.exception.typeconverter.converter.IpPortToStringConverter;
import hello.exception.typeconverter.converter.StringToIntegerConverter;
import hello.exception.typeconverter.converter.StringToIpPortConverter;
import hello.exception.typeconverter.formatter.MyNumberFormatter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","*.ico","/error","/error-page/**");// 오류페이지 경로
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());

    }

    //    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return filterRegistrationBean;
    }
    // Exception 처리


    // Covnerter
    @Override
    public void addFormatters(FormatterRegistry registry) {
//      주석 처리 우선순위
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());


        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
