# Spring REST practice  
>  [스프링5 레시피](https://book.naver.com/bookdb/book_detail.nhn?bid=13911953)  

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
