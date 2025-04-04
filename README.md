# 📦 Hello Batch Quartz

Spring Batch + Quartz 연습 프로젝트입니다.  
Quartz 또는 CLI로 Job을 실행하며, 조건에 맞는 사용자 정보를 읽어 **CSV로 저장**합니다.  
처리할 데이터가 없을 경우, 기존 CSV 파일을 덮어쓰지 않고 **파일은 그대로 유지**됩니다.

---

## ✨ 주요 기능

- ✅ Spring Batch + Quartz 연동
- ✅ H2 인메모리 DB 사용
- ✅ JobParameter로 날짜(`joinedAfter`) 및 저장 경로(`outputFile`) 주입
- ✅ 처리 조건: `processed = false` AND `joined_at >= :joinedAfter`
- ✅ 결과를 지정된 CSV 파일로 저장
- ✅ 처리할 데이터가 없으면 Step 건너뜀 (기존 파일 유지됨)
- ✅ CLI로 직접 파라미터 넘겨 Job 실행 가능

---

## 🧪 CLI 실행 방법

```bash
./gradlew bootRun --args='joinedAfter=2025-04-01 outputFile=/Users/b.k.kang/Desktop/users.csv'
```

- `joinedAfter`: 조회 시작일
- `outputFile`: 저장할 CSV 파일 경로

---

## 💾 DB 예시 데이터 (`data.sql`)

```sql
DELETE FROM users;

INSERT INTO users (name, email, processed, joined_at)
VALUES ('Bob', 'bob@example.com', false, '2025-04-03');
```

---

## 🧠 Job 흐름

1. `checkDataStep`: 조건에 맞는 유저 존재 여부 확인
2. 유저 있으면 → `exportToCsvStep` 실행
3. 유저 없으면 → Job 종료 (파일 건너뜀)

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
