# 📦 hello-batch-quartz

Spring Batch + Quartz를 활용한 **데이터 처리 자동화 프로젝트**입니다.  
사용자 정보를 DB에서 읽고, 조건에 맞게 처리 후 CSV로 저장하고, 이메일로 결과를 발송합니다.

---

## ✅ 주요 기능

| 기능 | 설명 |
|------|------|
| 🗓 Quartz 스케줄러 | 특정 시각에 Batch Job 자동 실행 |
| 🧪 H2 In-Memory DB | 테스트용 DB (`data.sql`로 초기화) |
| 📄 CSV 저장 | 처리된 유저를 지정한 경로에 저장 |
| 📧 이메일 전송 | 배치 완료 통계 결과를 메일로 발송 |
| 📊 처리 통계 출력 | 처리 건수 Job Listener에서 출력 |
| 🚫 데이터 없을 경우 종료 | 처리할 유저 없으면 Job 중단 & CSV 미생성 |
| 🖥 Web UI 실행 지원 | HTML 페이지에서 파라미터 입력 & 실행 |
| 🔌 Web UI 종료 지원 | 종료 버튼 클릭 시 서버 종료 가능 |
| 🛠 실행 스크립트 제공 | `./run.sh`로 환경변수와 함께 실행 가능 |

---

## ⚙ 실행 방법

### 1. Web UI 실행
Spring Boot 실행 후 웹 브라우저에서 아래 경로 접속:

```
http://localhost:8080
```

- 가입일 (`joinedAfter`)과 CSV 경로 (`outputFile`)를 입력하고 실행 버튼 클릭
- 종료 버튼 클릭 시 애플리케이션이 정상 종료됩니다

### 2. 실행 스크립트 (`run.sh`)
```bash
# 실행 전 권한 부여 (최초 1회)
chmod +x run.sh

# 실행 (메일 비밀번호 포함)
SPRING_MAIL_PASSWORD=your-app-password ./run.sh
```

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

`.env` 또는 터미널에서 환경 변수로 설정:

```bash
SPRING_MAIL_PASSWORD=your-app-password
```

그리고 `application.properties`에는 아래처럼 설정해 주세요:

```properties
spring.mail.username=your.email@gmail.com
spring.mail.password=${SPRING_MAIL_PASSWORD}
```

🔐 앱 비밀번호 생성 가이드: https://support.google.com/mail/answer/185833?hl=ko

---

## ❌ CLI 실행 제거

기존의 `CommandLineRunner` 기반 CLI 실행 방식은 제거되었습니다.  
Batch는 웹 페이지 또는 Quartz로만 실행됩니다.

---

## 🙌 만든 이유

- Spring Batch와 Quartz 연동 연습
- Web + Batch 통합 방식 구현 테스트
- 사용자 정의 배치 UI 및 자동화 연습

---

## 📌 사용 기술

- Java 11+
- Spring Boot 2.7.x
- Spring Batch
- Quartz Scheduler
- Spring Web (MVC)
- H2 Database
- JavaMailSender
