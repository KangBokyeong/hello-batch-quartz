# 📦 Hello Batch Quartz

Spring Batch + Quartz 연습 프로젝트입니다.  
Quartz 스케줄러가 일정 주기로 실행되며, `users` 테이블에서 가입일 조건을 만족하는 사용자만 읽고 처리하는 구조입니다.

---

## ✨ 주요 기능

- ✅ Spring Batch 기본 설정
- ✅ Quartz 스케줄러로 10초마다 Job 실행
- ✅ H2 인메모리 DB 사용
- ✅ `users` 테이블에서 `processed = false` AND `joined_at >= 어제`인 사용자만 조회
- ✅ 처리 후 `processed = true` 로 DB 업데이트
- ✅ `data.sql`로 더미 데이터 자동 삽입
- ✅ `spring.jpa.hibernate.ddl-auto=update`로 스키마 유지

---

## 💾 DB 구성

- 테이블: `users`
- 컬럼: `id`, `name`, `email`, `processed`, `joined_at`

```sql
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  processed BOOLEAN DEFAULT false,
  joined_at DATE
);
```

---

## 🧪 예시 데이터 (`src/main/resources/data.sql`)

```sql
DELETE FROM users;

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Alice', 'alice@example.com', false, '2024-04-01');

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Bob', 'bob@example.com', false, '2025-04-03');

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Charlie', 'charlie@example.com', false, '2023-12-01');
```

> 현재 날짜가 `2025-04-03`이라면, `Bob`만 처리됩니다.

---

## 🚀 실행 방법

```bash
./gradlew bootRun
```

- 콘솔 출력 예시:

```
🕐 Quartz 트리거 실행됨!
✅ 처리 중: Bob
```

---

## 📁 향후 확장 예정

- [x] 가입일 기준 조건 조회
- [ ] 가입일을 동적으로 파라미터로 받기
- [ ] 처리 결과를 CSV로 저장
- [ ] 이메일 알림 발송

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
