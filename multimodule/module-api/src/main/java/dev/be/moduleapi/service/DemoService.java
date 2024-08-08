package dev.be.moduleapi.service;

import dev.be.moduleapi.exception.CustomException;
import dev.be.modulecommon.domain.Member;
import dev.be.modulecommon.enums.CodeEnum;
import dev.be.modulecommon.repositories.MemberRepository;
import dev.be.modulecommon.service.CommonDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoService {

    @Value("${profile-name}")
    private String name;

    private final CommonDemoService commonDemoService;

    private final MemberRepository memberRepository;

    public String save(){
        log.info(name);

        memberRepository.save(Member.builder()
                .name(Thread.currentThread().getName())
                .build());


        //module-common 에서 가져온 코드
        //순수자바객체만 가져와보기
        System.out.println(CodeEnum.SUCCESS.getCode());

        //컴포넌트 스캔으로 가지고와야하는데 ModuleApiApplication 클래스를 module-common부분과
        //맞추지않으면 가지고올수 없다 dev.be 하위의 모든 컴포넌트를 스캔하기 위해서
        //ModuleApiApplication의 위치를 옮겨주면 해결!
        //빈으로 가지고온것  빈으로 가지고 오는부분만 컴포넌트 스캔부분을 신경써주면된다
        System.out.println(commonDemoService.commonService());

        //하지만 직접 옮기는것보다
        //@SpringBootApplication(
        //		scanBasePackages = { "dev.be.moduleapi", "dev.be.modulecommon" }
        //) 스캔할 컴포넌트 추가해주면 해결!


        return "save";
    }

    public String find(){
       int size =  memberRepository.findAll().size();
        System.out.println("DB size : " + size);
        return "find";
    }

    public String exception(){
        if(true){
            throw new CustomException(CodeEnum.UNKNOWN_ERROR);
        }
        return "exception";
    }
}
