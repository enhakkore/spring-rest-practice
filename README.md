# Spring REST practice  
>  [스프링5 레시피](https://book.naver.com/bookdb/book_detail.nhn?bid=13911953)  

---  
### 프로젝트 환경  
* Java  
    ```  
    openjdk 11.0.4 2019-07-16
    OpenJDK Runtime Environment (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3)
    OpenJDK 64-Bit Server VM (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3, mixed mode, sharing)  
    ```  

* Gradle  
    ```  
    ------------------------------------------------------------
    Gradle 5.6.4
    ------------------------------------------------------------

    Build time:   2019-11-01 20:42:00 UTC
    Revision:     dd870424f9bd8e195d614dc14bb140f43c22da98

    Kotlin:       1.3.41
    Groovy:       2.5.4
    Ant:          Apache Ant(TM) version 1.9.14 compiled on March 12 2019
    JVM:          11.0.4 (Ubuntu 11.0.4+11-post-Ubuntu-1ubuntu218.04.3)
    OS:           Linux 4.15.0-70-generic amd64  
    ```  

---
### 실행방법  
Step 1. Terminal  
```
$ gradle appRun
```  
Step 2. Browser
```
http://{hostname}:{port}/{rootProject.name}
```  

Example  
```bash
$ curl -i http://localhost:8080/spring-rest-practice/members
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/xml
Transfer-Encoding: chunked
Date: Thu, 28 Nov 2019 12:29:00 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?><members><member><email>marten@deinum.biz</email><name>Marten Deinum</name><phone>00-31-1234567890</phone></member><member><email>john@doe.com</email><name>John Doe</name><phone>1-800-800-800</phone></member><member><email>jane@doe.com</email><name>Jane Doe</name><phone>1-801-802-803</phone></member></members>
```  


---
### 정리
* __마샬링(Marshalling)__  
  메모리에 있는 객체를 특정한 데이터 형식으로 변환하는 과정  

* __@ResponseBody__  
  메서드의 실행 결과를 응답 본문으로 취하겠다고 스프링 MVC에게 밝히는 것  

* __Class ResponseEntity\<T\>__  
@ResponseBody가 설정된 메서드에서 @XmlRootElement가 설정된 클래스의 인스턴스를 반환할 때 null을 반환하더라도 HTTP 응답 코드는 항상 200 이다.  
ResponseEntity 클래스는 응답 본문과 HTTP 응답 코드를 포함하는 클래스이며, @ResponseBody가 설정된 메서드에서 ResponseEntity 클래스의 인스턴스를 반환하면 @XmlRootElement가 설정된 클래스의 인스턴스 존재 여부를 HTTP 응답 코드로 클라이언트가 알 수 있다.  

    ```java
    @XmlRootElement
    public class Member { ... }

    @RequestMapping("/member/{memberid}")
    @ResponseBody
    public ResponseEntity<Member> getMember(@PathVariable("memberid") long memberID) {
        Member member = memberService.find(memberID);
        if(member != null) {
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    ```  
* Xml과 Json 관련 라이브러리가 classpath에 있다면 Controller에서 @ResponseBody로 객체를 응답 본문으로 넘겨주면 @Configuration이 설정된 RestConfoguration에서 뷰 관련 메서드를 정의하지 않아도 Spring이 알아서 적합한 HttpMessageConverter를 등록해 처리한다.  

* Json 관련 라이브러리로 잭슨 외에 GSON 라이브러리도 있다.  
    ```  
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    compile group: 'com.google.code.gson', name: 'gson', version: '2.8.6'  
    ```  
* RestTemplate 활용 예  
    ```java
    public Members getRest() {
        final String url = "http://localhost:8080/spring-rest-practice/members";
        RestTemplate restTemplate = new RestTemplate();
        Members members = restTemplate.getForOnject(url, Members.class);
        System.out.print(members);
        return members;
    }
    ```  

* RSS/아톰 피드  
    * 아톰 피드  
        아톰 피드는 하나의 feed에 여러 entry를 포함하고 있다.  
        하나의 feed에는 title, id, updated 등의 메타데이터가 있고, 각 entry에는 title, id, updated, summary 등의 데이터가 있다.  

    * RSS 피드
        RSS 피드는 하나의 channel에 여러 item을 포함하고 있다.  
        하나의 channel에는 title, link, description, lastBuilDate 등의 메타데이터가 있고, 각 item에는 title, link, pubDate, author 등의 데이터가 있다.  
    * 작동 흐름  
        사용자가 브라우저에서 RSS or 아톰 피드를 요청하는 주소값을 입력하면  
        controller는 주소값에 해당하는 메서드를 실행한다.  
        메서드는 RSS or 아톰 피드에 넣을 데이터들을 만들고 이 데이터들의 리스트를 Model에 속성으로 추가한 Model을 View로 넘긴다.  
        아톰 피드는 AbstractAtomFeedView를 상속한 View 클래스로, RSS 피드는 AbstractRssFeedView를 상속한 View 클래스로 View를 구현한다.  
        각 View 클래스는 buildFeedMetadata 메서드와 buildFeedEntries(or buildFeedItems) 메서드를 오버라이드한다.  
        각 메서드들은 Model의 속성에서 데이터들의 리스트를 가져와 entry와 item에 테이터를 채운다.  
        사용자는 브라우저에서 RSS or 아톰 피드를 xml형식으로 볼 수 있다.

    
---
### 이슈  
* Java 11에는 이전 버전에 있었던 Java EE 모듈이 없다.  
  따라서 Java EE 모듈 라이브러리 의존성을 추가해야한다.  

  __build.gradle :__  
  ```
  dependencies {

      ...

      //javax.xml.*
      compile group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'

      //javax.annotation.*
      compile group: 'javax.annotation', name: 'javax.annotation-api', version: '1.3.2'

      // https://mvnrepository.com/artifact/com.sun.xml.bind/jaxb-impl
      compile group: 'com.sun.xml.bind', name: 'jaxb-impl', version: '2.3.2'

      // https://mvnrepository.com/artifact/com.sun.xml.bind/jaxb-core
      compile group: 'com.sun.xml.bind', name: 'jaxb-core', version: '2.3.0.1'
  }
  ```  

* MappingJackson2JsonView 뷰는 잭슨2 라이브러리를 이용해 객체를 JSON으로 바꾸거나 JSON을 객체로 바꾸는데, 잭슨 JSON 핸들러 라이브러리가 필요하다.  

  __build.gradle :__  
  ```
  dependencies {

      ...

      // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
      compile group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.10.1'

      // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
      compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.10.1'

  }
  ```  
