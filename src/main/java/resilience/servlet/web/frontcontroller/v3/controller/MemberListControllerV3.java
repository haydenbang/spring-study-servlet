package resilience.servlet.web.frontcontroller.v3.controller;

import resilience.servlet.domain.member.Member;
import resilience.servlet.domain.member.MemberRepository;
import resilience.servlet.web.frontcontroller.ModelView;
import resilience.servlet.web.frontcontroller.MyView;
import resilience.servlet.web.frontcontroller.v2.ControllerV2;
import resilience.servlet.web.frontcontroller.v3.ControllerV3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {


    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();

        ModelView modelView =  new ModelView("members");
        modelView.getModel().put("members", members);
        return modelView;
    }
}
