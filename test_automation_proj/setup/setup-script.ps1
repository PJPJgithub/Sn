# proxy설정
$env:HTTP_PROXY="http://koreaproxies.apa.gm.com:80"
$env:HTTPS_PROXY="http://koreaproxies.apa.gm.com:80"
 
# 1. C drive에 venvs 폴더 생성하고 그 내부에 test-automation-env 가상환경 생성
$venvPath = "C:\venvs\test-automation-env"
if (-Not (Test-Path $venvPath)) {
    New-Item -ItemType Directory -Path "C:\venvs"
    python -m venv $venvPath
}
 
# 2. 해당 가상환경 내부에 python 패키지 설치
$packages = @(
    "robotframework",
    "robotframework-appiumlibrary",
    "robotframework-pythonlibcore",
    "robotframework-seleniumlibrary",
    "opencv-contrib-python",
    "openai-whisper",
    "gTTS",
    "Appium-Python-Client",
    "pygame"
)
 
& $venvPath\Scripts\Activate
foreach ($package in $packages) {
    pip install $package
}
 
# 설치된 패키지 목록 확인
#pip list
 
# 가상환경 비활성화
& $venvPath\Scripts\deactivate.bat
 
 
# 3. scoop 사용하여 7zip, cmake, ffmpeg 설치
if (-Not (Get-Command scoop -ErrorAction SilentlyContinue)) {
    Invoke-Expression (New-Object System.Net.WebClient).DownloadString('https://get.scoop.sh')
    [System.Environment]::SetEnvironmentVariable("Path", "$env:USERPROFILE\scoop\shims;$env:Path", [System.EnvironmentVariableTarget]::User)
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path", [System.EnvironmentVariableTarget]::User)
}
 
$scoopPackages = @("7zip", "cmake", "ffmpeg")
foreach ($scoopPackage in $scoopPackages) {
    scoop install $scoopPackage
}
 
# 4. nvm 설치
if (-Not (Get-Command nvm -ErrorAction SilentlyContinue)) {
    Invoke-WebRequest -Uri "https://github.com/coreybutler/nvm-windows/releases/download/1.1.9/nvm-setup.zip" -OutFile "$env:TEMP\nvm-setup.zip"
    Expand-Archive -Path "$env:TEMP\nvm-setup.zip" -DestinationPath "$env:TEMP\nvm-setup"
    Start-Process -FilePath "$env:TEMP\nvm-setup\nvm-setup.exe" -Wait
}
 
# 5. nvm을 통해 v20.17.0 node 설치하고 default 노드로 설정
nvm install 20.17.0
nvm use 20.17.0
#nvm alias default 20.17.0#수정
 
# 6. 해당 노드에 appium와 uiautomator2 설치 (수정)
npm install -g appium
npm install -g appium-uiautomator2-driver
 
#7. 영구적으로 지정되는 환경변수 설정
# 관리자 권한으로 실행할 명령어를 스크립트 블록으로 정의
$scriptBlock = {
    [System.Environment]::SetEnvironmentVariable("APPIUM_MAIN_JS_PATH", "C:\Users\TZ47HH\AppData\Local\nvm\v20.17.0\node_modules\appium\build\lib\main.js", [System.EnvironmentVariableTarget]::Machine)
    [System.Environment]::SetEnvironmentVariable("AUTOMATION_HOME", "C:\Users\TZ47HH\AndroidStudioProjects\Snapnews_project1\Snapnews\test_automation_proj", [System.EnvironmentVariableTarget]::Machine)
}
 
# 관리자 권한으로 PowerShell 실행
Start-Process powershell -ArgumentList "-NoProfile -ExecutionPolicy Bypass -Command $scriptBlock" -Verb RunAs
 
# 8. vscode 설치
if (-Not (Get-Command code -ErrorAction SilentlyContinue)) {
    Invoke-WebRequest -Uri "https://update.code.visualstudio.com/latest/win32-x64/stable" -OutFile "$env:TEMP\vscode-setup.exe"
    Start-Process -FilePath "$env:TEMP\vscode-setup.exe" -Wait
}
 
# 9. vscode에 InternsAutomation 프로파일 생성
$profileDir = "$env:APPDATA\Code\User"
$profilePath = "$profileDir\profiles.json"
 
if (-Not (Test-Path $profileDir)) {
    New-Item -ItemType Directory -Path $profileDir -Force
}
 
$profileName = "InternsAutomation"
 
# 프로파일 JSON 생성
$profileData = @"
{
    "profiles": {
        "$profileName": {
            "settings": {},
            "extensions": {
                "recommendations": [
                    "ms-python.python",
                    "ms-python.vscode-pylance",
                    "ms-python.debugpy",
                    "robocorp.robotframework-lsp",
                    "charliermarsh.ruff"
                ]
            }
        }
    }
}
"@
 
# 프로파일 JSON 파일 작성
$profileData | Set-Content -Path $profilePath -Encoding UTF8
Write-Output "vs code profile success: $profilePath"
 
# 2️⃣ InternsAutomation 프로파일을 활성화한 상태에서 확장 프로그램 설치
Start-Process "code" -ArgumentList "--profile $profileName" -Wait
 
$extensions = @(
    "ms-python.python",
    "ms-python.vscode-pylance",
    "ms-python.debugpy",
    "robocorp.robotframework-lsp",
    "charliermarsh.ruff"
)
 
foreach ($extension in $extensions) {
    Start-Process "code" -ArgumentList "--install-extension $extension --profile $profileName" -Wait
}
 
Write-Output "success"
 
# 3️⃣ 설치된 확장 프로그램 확인
Write-Output "extension lists:"
code --list-extensions --profile $profileName
 