package resilience.servlet.web.frontcontroller.v5;

import resilience.servlet.web.frontcontroller.ModelView;
import resilience.servlet.web.frontcontroller.MyView;
import resilience.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import resilience.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import resilience.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import resilience.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import resilience.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import resilience.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import resilience.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import resilience.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handleMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();



    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handleMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handleMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handleMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        handleMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handleMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handleMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView modelView = adapter.handle(request, response, handler);

        String viewName = modelView.getViewName();
        MyView view = viewResolver(viewName);

        view.render(modelView.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if ( adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handleMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
