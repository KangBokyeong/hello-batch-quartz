# 📦 hello-batch-quartz

Spring Batch + Quartz를 활용한 **데이터 처리 자동화 프로젝트**입니다.  
사용자 정보를 DB에서 읽고, 조건에 맞게 처리 후 CSV로 저장하고, 관리자에게 결과를 이메일로 전송합니다.

---

## ✅ 주요 기능

| 기능 | 설명 |
|------|------|
| ⏰ Quartz 스케줄러 | 특정 시간 이후 한 번만 Job 실행 |
| 🧪 H2 In-Memory DB | 테스트용 DB (`data.sql`로 초기화) |
| 📄 CSV 저장 | 처리된 유저를 지정한 경로에 저장 |
| ✉ 관리자 이메일 전송 | 처리 통계 결과를 **한 번만** 전송 |
| ⚙ JobParameter 지원 | `joinedAfter`, `outputFile` 파라미터 CLI 입력 가능 |
| 📊 처리 통계 출력 | JobListener를 통해 처리 건수 출력 |
| 🚫 처리 대상 없으면 종료 | 유저 없을 경우 Job 중단 & CSV 미생성 |

---

## ⚙ 실행 방법

### 1. CLI 실행
```bash
./gradlew bootRun --args='joinedAfter=2025-04-01 outputFile=/Users/yourname/Desktop/users.csv'
```

### 2. Quartz 자동 실행
- 애플리케이션 시작 시, `QuartzConfig`에 지정된 시각 이후 **한 번만 자동 실행**
- 기본값으로 하드코딩된 날짜 및 저장 경로 사용 가능

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

> 🔐 **앱 비밀번호**를 발급받아야 합니다.  
> 👉 [앱 비밀번호 생성 가이드](https://support.google.com/mail/answer/185833?hl=ko)

---

## 📧 이메일 관련 설명

- ✅ **처리 완료 후**, 관리자(지정된 메일)에만 통계 결과를 이메일로 전송합니다.
- ❌ 각 유저에게 개별 메일은 전송하지 않습니다.
- 📊 이메일에는 처리된 총 건수, 읽은 건수, 쓴 건수가 포함됩니다.

---

## 📌 사용 기술

- Java 11
- Spring Boot 2.7.x
- Spring Batch
- Quartz Scheduler
- H2 Database
- JavaMailSender

---

## 🙌 만든 이유

- Spring Batch & Quartz 실무 흐름 학습
- 조건 필터링, CSV 저장, 통계 수집, 이메일 전송 등 배치 전 과정 구현
