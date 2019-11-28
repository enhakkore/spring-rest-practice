# Spring REST practice  
>  [스프링5 레시피](https://book.naver.com/bookdb/book_detail.nhn?bid=13911953)  

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



### 정리
* __마샬링(Marshalling)__  
  메모리에 있는 객체를 특정한 데이터 형식으로 변환하는 과정  

* __@ResponseBody__  
  메서드의 실행 결과를 응답 본문으로 취하겠다고 스프링 MVC에게 밝히는 것  

* __Class ResponseEntity\<T\>__  
@ResponseBody가 설정된 메서드에서 @XmlRootElement가 설정된 클래스의 인스턴스를 반환할 때 인스턴스가 아닌 null이 반환하더라도 HTTP 응답 코드는 항상 200 이다.  
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
