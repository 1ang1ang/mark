package com.elex.mark.interceptor;

import com.elex.mark.enums.ApiAuthorityType;
import com.elex.mark.enums.LogicErrorCode;
import com.elex.mark.model.User;
import com.elex.mark.service.UserService;
import com.elex.mark.util.AuthorityHelper;
import com.elex.mark.util.annotation.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by sun on 2017/7/10.
 */
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

//    @Resource
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("begin check authority");
        HandlerMethod handler2=(HandlerMethod) handler;
        Authority fireAuthority = handler2.getMethodAnnotation(Authority.class);

        if(null == fireAuthority){
            //没有声明权限,放行
            return true;
        }

        logger.debug("fireAuthority", fireAuthority.toString());

//        if(userService == null) {
//            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//            operatorLogService = (OperatorLogService) factory.getBean("operatorLogService");
//        }
        User user = userService.getByUid(request.getHeader("uid"));
//        HttpSession session = request.getSession();
//        Worker manager = (Worker)session.getAttribute(SessionHelper.WorkerHandler);
        boolean aflag = false;

        //用户id为空~说明为匿名登录~不需要判断是否有权限
        if(user != null) {
            for(ApiAuthorityType at:fireAuthority.authorityTypes()){
                if(AuthorityHelper.hasAuthority(at.getIndex(), user.getAuthority())==true){
                    aflag = true;
                    break;
                }
            }
        }

        if(false == aflag){

//            if (fireAuthority.resultType() == ResultTypeEnum.page) {
                //传统的登录页面
                StringBuilder sb = new StringBuilder();
                sb.append(request.getContextPath());
                sb.append("/oprst.jsp?oprst=false&opmsg=").append(URLEncoder.encode(LogicErrorCode.NO_AUTHORITY.getMsg(),"utf-8"));
                response.sendRedirect(sb.toString());
//            } else if (fireAuthority.resultType() == ResultTypeEnum.json) {
//                //ajax类型的登录提示
//                response.setCharacterEncoding("utf-8");
//                response.setContentType("text/html;charset=UTF-8");
//                OutputStream out = response.getOutputStream();
//                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
//                pw.println("{\"result\":false,\"code\":12,\"errorMessage\":\""+ControllerProperty.NOT_HAVE_AUTHORITY+"\"}");
//                pw.flush();
//                pw.close();
//            }

            return false;

        }
        return true;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
