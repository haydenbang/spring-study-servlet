package resilience.servlet.basic.reponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import resilience.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-type: text/html;charset=utf-8;
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        HelloData helloData = new HelloData();
        helloData.setUsername("hayden");
        helloData.setAge(50);

        String helloDataJsonString = objectMapper.writeValueAsString(helloData);

        writer.println(helloDataJsonString);
    }
}
