import os
import sys
import subprocess
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot import result, running
from robot.api.deco import library

# GLOBAL, SUITE, TEST
@library(scope='GLOBAL', listener='SELF')
class RobotLogListener:
    ROBOT_LISTENER_API_VERSION = 3

    def __init__(self):
        print("Robotlistener initialized...")
        self.report_data = []
        self.recording_process = None

    def start_suite(self, suite: running.TestSuite, result: result.TestSuite):
        self.report_data.append(f"Suite '{suite.name}' started.")

    def end_suite(self, suite: running.TestSuite, result: result.TestSuite):
        self.report_data.append(f"Suite '{suite.name}' ended with status: {result.status}.")

    def start_test(self, test: running.TestCase, result: result.TestCase):
        self.report_data.append(f"Test '{test.name}' started.")
        self.start_recording(test.name)

    def end_test(self, test: running.TestCase, result: result.TestCase):
        self.report_data.append(f"Test '{test.name}' ended with status: {result.status}.")
        if result.status == 'FAIL':
            self.report_data.append(f"  Error: {result.message}")
        self.stop_recording()
        self.generate_report(test.name)
        self.report_data = []  # Clear report data after generating the report

    def start_keyword(self, data: running.Keyword, result: result.Keyword):
        self.report_data.append(f"  Keyword '{data.name}' started.")

    def end_keyword(self, data: running.Keyword, result: result.Keyword):
        self.report_data.append(f"  Keyword '{data.name}' ended with status: {result.status}.")
        if result.status == 'FAIL':
            self.report_data.append(f"    Error: {result.message}")

    def log_message(self, message):
        self.report_data.append(f"  Log: {message.message}")

    def generate_report(self, file_name):
        automation_home = os.environ.get('AUTOMATION_HOME', '.')
        report_dir = os.path.join(automation_home, 'reports')
        os.makedirs(report_dir, exist_ok=True)
        report_path = os.path.join(report_dir, f'{file_name}_report.txt')
        with open(report_path, 'w', encoding='utf-8') as report_file:
            for line in self.report_data:
                report_file.write(line + '\n')
        print(f"Report generated at {report_path}")

    def start_recording(self, file_name):
        automation_home = os.environ.get('AUTOMATION_HOME', '.')
        video_dir = os.path.join(automation_home, 'videos')
        os.makedirs(video_dir, exist_ok=True)
        video_path = os.path.join(video_dir, f'{file_name}.wmv')
        self.recording_process = subprocess.Popen(
            [
                'ffmpeg', '-y',  # 기존 파일을 덮어쓰도록 설정합니다.
                '-f', 'gdigrab',  # Windows 화면 캡처를 위해 gdigrab 입력 장치를 사용합니다.
                '-framerate', '30',  # 초당 30 프레임으로 녹화합니다.
                '-offset_x', '-1920',  # 첫 번째 모니터의 너비만큼 왼쪽으로 이동합니다.
                '-offset_y', '0',  # Y축 오프셋을 0으로 설정합니다.
                '-video_size', '1920x1080',  # 두 번째 모니터의 해상도를 지정합니다.
                '-i', 'desktop',  # 전체 데스크탑을 입력 소스로 사용합니다.
                '-f', 'dshow',  # DirectShow 입력 장치를 사용합니다.
                '-i', 'audio=스테레오 믹스(Realtek(R) Audio)',  # 스테레오 믹스를 입력 소스로 사용합니다.
                '-vcodec', 'wmv2',  # 비디오 코덱으로 wmv2를 사용하여 Windows Media Video 형식으로 저장합니다.
                video_path  # 출력 파일 경로입니다.
            ],
            stdin=subprocess.PIPE,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            shell=True
        )
        print(f"Recording started for {file_name}")

    def stop_recording(self):
        if self.recording_process:
            self.recording_process.communicate(input=b'q')
            self.recording_process.wait()
            print("Recording stopped")

    def close(self):
        print("Closing listener and generating report...")
        self.generate_report('final_report')