import os
import sys
import subprocess
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword
'''
@keyword("Close Appium Server")
def close_appium_server(arg_can_be_located_on_here = ""):
    # Appium 서버를 종료하는 명령어
    command = "taskkill /F /IM node.exe"
    
    # subprocess를 사용하여 명령어 실행
    process = subprocess.Popen(command, shell=True)

    # 프로세스가 성공적으로 종료되었는지 확인
    if process.returncode == 0:
        print("Appium server closed")
    
'''
def get_latest_node_pid():
    # tasklist 명령어를 사용하여 모든 node.exe 프로세스 목록 가져오기
    result = subprocess.run(['tasklist', '/FI', 'IMAGENAME eq node.exe'], stdout=subprocess.PIPE, text=True)
    lines = result.stdout.splitlines()
    
    # PID 추출
    pids = []
    for line in lines:
        if 'node.exe' in line:
            parts = line.split()
            pids.append(int(parts[1]))  # PID는 두 번째 열에 위치

    # 가장 큰 PID 반환 (가장 최근에 생성된 프로세스)
    if pids:
        return pids[1]
    return None

@keyword("Close Appium Server")
def close_appium_server(arg_can_be_located_on_here = ""):
    pid = get_latest_node_pid()
    # Appium 서버를 종료하는 명령어
    command = f"taskkill /F /PID {pid}"
    
    # subprocess를 사용하여 명령어 실행
    process = subprocess.Popen(command, shell=True)

    # 프로세스가 성공적으로 종료되었는지 확인
    if process.returncode == 0:
        print("Appium server closed")
