package com.apress.springrecipes.court.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.apress.springrecipes.court.domain.Member;
import com.apress.springrecipes.court.domain.Members;
import com.apress.springrecipes.court.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
public class RestMemberController {

    private final MemberService memberService;

    @Autowired
    public RestMemberController(MemberService memberService) {
        super();
        this.memberService = memberService;
    }

    // //MarshallingView로 XML만들기
    // @RequestMapping("/members")
    // public String getRestMembers(Model model) {
    //     Members memebers = new Members();
    //     members.addMembers(memberService.findAll());
    //     model.addAttribute("members", members);
    //     return "memeberstemplate"
    // }

    //@ResponseBody로 XML만들기
    @RequestMapping("/members")
    @ResponseBody
    public Members getRestMembers(Model model) {
        Members members = new Members();
        members.addMembers(memberService.findAll());
        model.addAttribute("members", members);
        return members;
    }

    // //@PathVariable로 결과 거르기
    // @RequestMapping("/member/{memberid}")
    // @ResponseBody
    // public Member getMember(@PathVariable("memberid") long memberID) {
    //     return memberService.find(memberID);
    // }

    //ResponseEntity로 클라이언트에게 알려주기
    @RequestMapping("/member/{memberid}")
    @ResponseBody
    public ResponseEntity<Member> getMember(@PathVariable("memberid") long memberID) {
        Member member = memberService.find(memberID);
        if(member != null) {
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
