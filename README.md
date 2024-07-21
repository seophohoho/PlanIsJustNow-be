# Plan Is Just Now - BE

## Setting

해당 코드들은 `application.properties`의 영향을 받고 있기 때문에 Sample 값으로 해당 파일을 push 했음.

`application.properties` 파일이 없으면 테스트 진행시 어려움이 있기 때문에 임시로 생성함.

> 실제 배포시에는 해당 값들 수정 필요\
`jwt.key` 해당 값의 size는 256bit를 맞추는 것을 추천

```bash
spring.jpa.hibernate.dll-auto=update
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3306/pijn
spring.datasource.username=root
spring.datasource.password=root

jwt.key=yl9Dp6VY7Y2x8KuL+90bj7OXN1RDeYdR45yqXQZTcJo=
jwt.expiry=3600000

spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.debug=true

cloud.aws.region.static=your_aws_region
cloud.aws.credentials.accessKey=your_access_key
cloud.aws.credentials.secretKey=your_secret_key

cloud.aws.s3.bucket=my-s3-bucket-name
```

## Usage

- 터미널 빌드(안될 때도 있어서 `IntelliJ IDEA`로 Build 추천)

```bash
./gradlew build
```

- IntelliJ IDEA
  - 해당 방법으로 빌드는 `IntelliJ IDEA` 들어가서 빌드하면 바로 성공함.
