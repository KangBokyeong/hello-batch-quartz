# 📦 Hello Batch Quartz

Spring Batch + Quartz 연습 프로젝트입니다.  
10초마다 Quartz가 실행되어, Spring Batch Job을 트리거하고 사용자 목록을 읽어 처리합니다.

---

## ✨ 주요 기능

- ✅ Spring Batch 기본 설정
- ✅ Quartz 스케줄러로 10초마다 Job 실행
- ✅ H2 인메모리 DB 사용
- ✅ users 테이블에서 processed = false인 사용자만 읽기
- ✅ 처리 완료 후 processed = true로 DB에 반영
- ✅ INSERT/CREATE 조건 처리를 위한 data.sql 사용

---

## 💾 DB 구성

- 테이블: `users`
- 컬럼: `id`, `name`, `email`, `processed`

```sql
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  processed BOOLEAN DEFAULT false
);
```

---

## 🚀 실행 방법

```bash
./gradlew bootRun
```

- 콘솔 출력 예시:

```
🕐 Quartz 트리거 실행됨!
✅ 처리 중: Alice
✅ 처리 중: Bob
```

---

## 📁 향후 확장 예정

- [x] DB 기반 사용자 읽기
- [ ] 가입일 기준 조건 추가
- [ ] CSV/Excel로 저장
- [ ] 이메일 발송

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
