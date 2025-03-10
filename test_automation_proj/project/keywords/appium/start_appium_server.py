import os
import sys
import subprocess
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

@keyword("Start Appium Server")
def start_appium_server(arg_can_be_located_on_here = ""):
    # Appium 서버를 시작하는 명령어
    command = "appium --base-path /wd/hub"
    
    # subprocess를 사용하여 명령어 실행
    process = subprocess.Popen(command, shell=True)

    if process.returncode == 0:
        print("Appium server start")
    else:
        print("Appium server error")

@keyword("Pass")
def passing(arg_can_be_located_on_here = ""):
    pass
