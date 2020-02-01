package pay.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/30
 */
public interface PaymentTypeService {
    /**
     * 获取支付类型信息
     * @param id
     * @return
     */
    @RequestMapping("/getPaymentType")
    Map<String, Object> getPaymentType(@RequestParam("id") Long id);
}
