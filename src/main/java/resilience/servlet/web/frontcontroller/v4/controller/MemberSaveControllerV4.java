package resilience.servlet.web.frontcontroller.v4.controller;

import resilience.servlet.domain.member.Member;
import resilience.servlet.domain.member.MemberRepository;
import resilience.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String userName = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(userName, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
