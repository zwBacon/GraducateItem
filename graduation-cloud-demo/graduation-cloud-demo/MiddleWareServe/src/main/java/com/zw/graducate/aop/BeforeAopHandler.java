package com.zw.graducate.aop;

import com.zw.graducate.entity.Log;
import com.zw.graducate.mapper.LogHandlerMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/22 9:58
 */
@Aspect
@Component
public class BeforeAopHandler {

    @Autowired
    private LogHandlerMapper mapper;

    @Pointcut("@annotation(com.zw.graducate.interfaceAop.CustomerAopNote)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        this.saveLog(point, time,result);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time,Object result ) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += args[i];
            }
            log.setParams(params);
        }
        // 获取request
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 设置IP地址
        log.setIp(getIpAddress(request));

        String callType = request.getMethod();
        log.setCallType(callType);

        // 获取请求的URL
        String url = request.getRequestURL().toString();
        log.setUrl(url);

        //获取请求方法
        String requestMethod = request.getRequestURI();
        log.setMethod(requestMethod);

        //        获取时间
        LocalDateTime now = LocalDateTime.now();
        log.setCallTime(now);

//        获取响应信息
        Object response = joinPoint.proceed();
        log.setResponse(response.toString());

//        设置唯一标识
        log.setFollowId("Follow-D-" + RandomStringUtils.randomAlphanumeric(30));

        String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth(): "" + now.getDayOfMonth();
        // 保存系统日志
        mapper.save("url_aopcall_log_"+day,log);

    }

    /**
     * 获取request中的IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     *
     * @param method 具体的方法
     * @param args   参数列表
     * @return
     */
//    private Object getParameter(Method method, Object[] args) {
//        List<Object> argList = new ArrayList<>();
//        Parameter[] parameters = method.getParameters();
//        for (int i = 0; i < parameters.length; i++) {
//            // 将RequestBody注解修饰的参数作为请求参数
//            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
//            if (requestBody != null) {
//                argList.add(args[i]);
//            }
//            // 将RequestParam注解修饰的参数作为请求参数
//            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
//            if (requestParam != null) {
//                Map<String, Object> map = new HashMap<>();
//                String key = parameters[i].getName();
//                if (!StringUtils.isEmpty(requestParam.value())) {
//                    key = requestParam.value();
//                }
//                map.put(key, args[i]);
//                argList.add(map);
//            }
//        }
//        if (argList.size() == 0) {
//            return null;
//        } else if (argList.size() == 1) {
//            return argList.get(0);
//        } else {
//            return argList;
//        }
//    }


}
