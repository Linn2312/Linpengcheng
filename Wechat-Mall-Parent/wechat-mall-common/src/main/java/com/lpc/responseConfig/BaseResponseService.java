package com.lpc.responseConfig;

import com.lpc.constants.BaseResponseConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/5
 *
 * 封装响应状态的信息工具类
 * 自定义了成功与失败的响应信息
 */
public class BaseResponseService {

    protected Map<String, Object> setResultSuccess(){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_200, BaseResponseConstants.HTTP_RESP_CODE_200_VALUE,null);
    }
    protected Map<String, Object> setResultSuccess(String msg){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_200,msg,null);
    }
    protected Map<String, Object> setResultSuccessData(Object data){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_200,BaseResponseConstants.HTTP_RESP_CODE_200_VALUE,data);
    }
    protected Map<String, Object> setResultSuccess(String msg, Object data){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_200,msg,data);
    }
    protected Map<String, Object> setResultError(){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_500, BaseResponseConstants.HTTP_RESP_CODE_500_VALUE,null);
    }
    protected Map<String, Object> setResultError(String msg){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_500,msg,null);
    }
    protected Map<String, Object> setResultErrorData(Object data){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_500,BaseResponseConstants.HTTP_RESP_CODE_500_VALUE,data);
    }
    protected Map<String, Object> setResultError(String msg, Object data){
        return setResult(BaseResponseConstants.HTTP_RESP_CODE_500,msg,data);
    }
    protected Map<String, Object> setParametersError(String msg){
        return setResult(BaseResponseConstants.HTTP_REQ_CODE_400, msg,null);
    }

    //自定义统一的响应信息 以map集合的形式返回
    private Map<String, Object> setResult(Integer code, String msg, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put(BaseResponseConstants.HTTP_RESP_CODE_NAME,code);
        map.put(BaseResponseConstants.HTTP_RESP_CODE_MSG,msg);
        if (data != null){
            map.put(BaseResponseConstants.HTTP_RESP_CODE_DATA,data);
        }
        return map;
    }
}
