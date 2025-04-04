# 📦 Hello Batch Quartz

Spring Batch + Quartz 연습 프로젝트입니다.  
Quartz 스케줄러가 일정 주기로 실행되며, 조건에 맞는 사용자 정보를 읽어 **CSV로 저장**합니다.  
**처리할 데이터가 없을 경우, 기존 CSV 파일은 덮어쓰지 않고 그대로 유지됩니다.**

---

## ✨ 주요 기능

- ✅ Spring Batch 기본 설정
- ✅ Quartz 스케줄러로 10초마다 자동 실행
- ✅ H2 인메모리 DB 사용
- ✅ JobParameter로 날짜(`joinedAfter`) 및 저장 경로(`outputFile`) 외부에서 주입
- ✅ 처리 조건: `processed = false` AND `joined_at >= :joinedAfter`
- ✅ 결과를 지정된 CSV 파일로 저장
- ✅ 처리할 데이터가 없으면 CSV Step 건너뜀 (기존 파일 유지됨)

---

## 💾 DB 구성

```sql
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  processed BOOLEAN DEFAULT false,
  joined_at DATE
);
```

---

## 📁 데이터 예시 (`src/main/resources/data.sql`)

```sql
DELETE FROM users;

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Bob', 'bob@example.com', false, '2025-04-03');
```

> 위 데이터는 `joinedAfter=2025-04-01` 조건을 만족하므로 처리됩니다.

---

## 🚀 실행 방법

```bash
./gradlew bootRun
```

- 콘솔에 Quartz 로그 출력
- 지정된 경로에 `users.csv` 파일 생성
- 예: `/Users/yourname/Desktop/users.csv`

---

## 🧠 동작 흐름

1. `checkDataStep`에서 조건에 맞는 유저 존재 여부 확인
2. 존재하면 `exportToCsvStep` 실행
3. 존재하지 않으면 Job 정상 종료 (CSV Step은 실행 안 됨)

---

## 📌 확장 아이디어

- [x] 가입일 조건 필터링
- [x] 처리 결과 CSV 저장
- [x] 처리할 데이터 없을 때 Step 건너뛰기
- [ ] JobParameter를 CLI로 직접 넘기기
- [ ] CSV → DB 반대 방향 처리
- [ ] 이메일 발송 기능

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
