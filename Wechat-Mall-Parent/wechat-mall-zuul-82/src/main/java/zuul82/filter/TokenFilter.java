package zuul82.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lin
 * @Date 2020/01/27
 */
@Component
public class TokenFilter extends ZuulFilter {

    @Value("${server.port}")
    private String port;

    // 设置过滤类型pre 表示在请求之前执行
    @Override
    public String filterType() {
        return "pre";
    }

    // 设置过滤器的执行顺序  当一个请求在同一个阶段存在多个过滤器的时候，多个过滤器的执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    // 判断拦截器是否生效
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 编写过滤器拦截业务的代码
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        System.out.println("网关端口号是："+port);
        return null;
    }
}
