# 📦 Hello Batch Quartz

Spring Batch와 Quartz를 활용한 배치 자동화 연습 프로젝트입니다.  
10초마다 Quartz가 실행되어, Spring Batch Job을 트리거하고 로그에 메시지를 출력합니다.

---

## ✨ 주요 기능

- Spring Batch 기본 설정
- Quartz Scheduler 연동
- 콘솔에 "Hello, Batch!" 메시지 주기적으로 출력
- H2 메모리 DB 설정 포함
- 향후 DB 읽기 기반 처리로 확장 가능

---

## 🚀 실행 방법

```bash
./gradlew bootRun
```

> 실행 후 콘솔에 10초마다 아래 메시지 출력:
```
🕐 Quartz 트리거 실행됨!
Hello, Batch!
```

---

## 📁 향후 확장 예정

- [x] 기본 콘솔 출력 배치
- [ ] DB 읽기 기반 처리
- [ ] CSV 저장
- [ ] 이메일 발송

---

## 👤 개발자

- [@KangBokyeong](https://github.com/KangBokyeong)
