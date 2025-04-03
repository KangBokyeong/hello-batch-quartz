# 📦 Hello Batch Quartz

Spring Batch + Quartz 연습 프로젝트입니다.  
Quartz 스케줄러가 일정 주기로 실행되며, `users` 테이블에서 **가입일 기준 + 처리 여부 기준**으로 사용자 데이터를 읽고 처리합니다.

---

## ✨ 주요 기능

- ✅ Spring Batch 기본 설정
- ✅ Quartz 스케줄러로 10초마다 Job 자동 실행
- ✅ H2 인메모리 DB 사용
- ✅ JobParameter로 기준 날짜(`joinedAfter`)를 외부에서 주입
- ✅ `users` 테이블에서 조건: `processed = false AND joined_at >= :joinedAfter`
- ✅ 처리 후 `processed = true` 로 DB 반영
- ✅ data.sql로 더미 데이터 자동 입력

---

## 💾 DB 테이블 구조

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

> 현재 날짜가 `2025-04-03`이고, `joinedAfter = 2025-04-02`라면 `Bob`만 처리됩니다.

---

## 🧩 향후 확장 예정

- [x] 가입일 기준 조회
- [x] JobParameter로 외부 날짜 입력
- [ ] 처리 결과를 CSV로 저장
- [ ] 이메일 전송 기능
- [ ] 처리 건수 통계 출력

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
