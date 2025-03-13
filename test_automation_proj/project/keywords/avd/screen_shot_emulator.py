import subprocess
import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

def take_screenshot(filename):
    # 스크린샷 캡처
    subprocess.run(["adb", "shell", "screencap", "-p", f"/sdcard/{filename}"], check=True)
    # 스크린샷 파일을 로컬로 복사
    subprocess.run(["adb", "pull", f"/sdcard/{filename}"], check=True)
    # 기기에서 스크린샷 파일 삭제
    subprocess.run(["adb", "shell", "rm", f"/sdcard/{filename}"], check=True)
 
@keyword("Capture Screenshot")
def capture_screenshot():
    # AUTOMATION_HOME 환경 변수에서 경로 가져오기
    automation_home = os.environ.get('AUTOMATION_HOME')
    if not automation_home:
        print("The AUTOMATION_HOME environment variable is not set.")
        sys.exit(1)
 
    # 저장할 경로 설정
    save_path = os.path.join(automation_home, 'project', 'resources')
    if not os.path.exists(save_path):
        os.makedirs(save_path)
 
    # 스크린샷 파일명
    filename = "my_screenshot.png"
 
    # 스크린샷 캡처 및 저장
    take_screenshot(filename)
   
    # 기존 파일이 있으면 삭제
    destination_path = os.path.join(save_path, filename)
    if os.path.exists(destination_path):
        os.remove(destination_path)
 
    # 스크린샷 파일을 지정된 경로로 이동
    os.rename(filename, os.path.join(save_path, filename))
 
    print(f"The screenshot was successfully captured and saved to {save_path}.")