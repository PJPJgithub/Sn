# run_tests.ps1
# 초기 설정 스크립트 실행
.\setup-script.ps1

# Appium 서버 시작
Start-Process -NoNewWindow -FilePath "appium" -ArgumentList "--base-path /wd/hub"

# 테스트 실행
robot test_cases/