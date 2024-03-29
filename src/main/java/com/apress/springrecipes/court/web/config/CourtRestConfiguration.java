package com.apress.springrecipes.court.web.config;

import java.util.Collections;

import com.apress.springrecipes.court.feeds.AtomFeedView;
import com.apress.springrecipes.court.feeds.RSSFeedView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.apress.springrecipes.court.domain.Member;
import com.apress.springrecipes.court.domain.Members;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.apress.springrecipes.court")
public class CourtRestConfiguration {
//    //MappingJackson2JsonView로 XML 만들기
//    @Bean
//    public View jsonmembertemplate() {
//        MappingJackson2JsonView view = new MappingJackson2JsonView();
//        view.setPrettyPrint(true);
//        return view;
//    }
//
//    @Bean
//    public View xmlmembertemplate() {
//        return new MarshallingView(jaxb2Marshaller());
//    }
//
//     //MarshallingView로 XML만들기
//     @Bean
//     public View membertemplate() {
//         return new MarshallingView(jaxb2Marshaller());
//     }
//
//     @Bean
//     public Marshaller jaxb2Marshaller() {
//         Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//         marshaller.setClassesToBeBound(Members.class, Member.class);
//         marshaller.setMarshallerProperties(Collections.singletonMap(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
//         return marshaller;
//     }
//
    //RSS/아톰 피드 발행하기
    @Bean
    public AtomFeedView atomfeedtemplate() {
        return new AtomFeedView();
    }

    @Bean
    public RSSFeedView rssfeedtemplate() {
        return new RSSFeedView();
    }

     @Bean
     public ViewResolver viewResolver() {
         return new BeanNameViewResolver();
     }

}
