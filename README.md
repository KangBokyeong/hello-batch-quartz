# 📦 hello-batch-quartz

Spring Batch + Quartz를 활용한 **데이터 처리 자동화 프로젝트**입니다.  
사용자 정보를 DB에서 읽고, 조건에 맞게 처리 후 CSV로 저장하고, 이메일로 결과를 발송합니다.

---

## ✅ 주요 기능

| 기능 | 설명 |
|------|------|
| 🗓 Quartz 스케줄러 | 10초마다 Batch Job 자동 실행 |
| 🧪 H2 In-Memory DB | 테스트용 DB (`data.sql`로 초기화) |
| 📄 CSV 저장 | 처리된 유저를 지정한 경로에 저장 |
| 📧 이메일 전송 | 처리 완료된 유저에게 이메일 발송 |
| ⚙ JobParameter 지원 | `joinedAfter`, `outputFile` 파라미터 CLI 입력 가능 |
| 📊 처리 통계 출력 | 처리 건수 Job Listener에서 출력 |
| 🚫 데이터 없을 경우 종료 | 처리할 유저 없으면 Job 중단 & CSV 미생성 |

---

## ⚙ 실행 방법

### 1. CLI 실행
```bash
./gradlew bootRun --args='joinedAfter=2025-04-01 outputFile=/Users/yourname/Desktop/users.csv'
```

### 2. Quartz 자동 실행
- 내부적으로 10초마다 자동 실행
- `joinedAfter`와 `outputFile` 기본값 자동 지정

---

## 🗂 예제 데이터 (`data.sql`)
```sql
INSERT INTO users (name, email, processed, joined_at)
VALUES ('Alice', 'alice@example.com', false, '2024-04-01');

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Bob', 'bob@example.com', false, '2025-04-03');
```

---

## ✉ 이메일 설정 (Gmail 기준)

`application.properties`에 다음 설정 추가:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your.email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> 🔐 Gmail 보안 - 앱 비밀번호를 생성해서 사용하세요.  
> [앱 비밀번호 생성 가이드](https://support.google.com/mail/answer/185833?hl=ko)

---

## 💡 향후 추가 예정

- 📬 관리자에게 전체 결과 요약 이메일 발송
- 🗃 CSV 업로드 기능 추가
- 📊 Web UI 통계 시각화

---

## 🙌 만든 이유

- Spring Batch와 Quartz 연동 연습
- 실전 데이터 처리 흐름을 연습하고 익히기 위해

---

## 📌 사용 기술

- Java 11
- Spring Boot 2.7.x
- Spring Batch
- Quartz Scheduler
- H2 Database
- JavaMailSender
