# Musinsa Gateway
- 8080 포트로 수신되는 요청들을 상황에 맞게 서비스로 분기를 수행합니다.

---

## 분기 처리
- 두 가지 서비스에 대한 분기를 수행합니다.
  - backoffice
  - search
- [라우팅](src/main/java/com/devh/project/musinsa/gateway/config/GatewayConfiguration.java#L31-L36)

---

## 예외 처리
- 각 도메인의 에러는 일괄 처리되어 수신되지만, 도메인과 무관한 에러 핸들링이 필요합니다.
  - [도메인 외적인 에러 핸들링](src/main/java/com/devh/project/musinsa/gateway/handler/GlobalExceptionHandler.java#L31-L45)