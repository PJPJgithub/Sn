import os
import sys
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

    def start_suite(self, suite: running.TestSuite, result: result.TestSuite):
        self.report_data.append(f"Suite '{suite.name}' started.")

    def end_suite(self, suite: running.TestSuite, result: result.TestSuite):
        self.report_data.append(f"Suite '{suite.name}' ended with status: {result.status}.")
        self.generate_report()

    def start_test(self, test: running.TestCase, result: result.TestCase):
        self.report_data.append(f"Test '{test.name}' started.")

    def end_test(self, test: running.TestCase, result: result.TestCase):
        self.report_data.append(f"Test '{test.name}' ended with status: {result.status}.")
        if result.status == 'FAIL':
            self.report_data.append(f"  Error: {result.message}")

    def start_keyword(self, data: running.Keyword, result: result.Keyword):
        self.report_data.append(f"  Keyword '{data.name}' started.")

    def end_keyword(self, data: running.Keyword, result: result.Keyword):
        self.report_data.append(f"  Keyword '{data.name}' ended with status: {result.status}.")
        if result.status == 'FAIL':
            self.report_data.append(f"    Error: {result.message}")

    def log_message(self, message):
        self.report_data.append(f"  Log: {message.message}")

    def generate_report(self):
        automation_home = os.environ.get('AUTOMATION_HOME', '.')
        report_dir = os.path.join(automation_home, 'reports')
        os.makedirs(report_dir, exist_ok=True)
        report_path = os.path.join(report_dir, 'test_report.txt')
        with open(report_path, 'w', encoding='utf-8') as report_file:
            for line in self.report_data:
                report_file.write(line + '\n')
        print(f"Report generated at {report_path}")

    def close(self):
        print("Closing listener and generating report...")
        self.generate_report()
    '''
    def __init__(self):
        pass

    def __del__(self):
        pass

    def start_suite(self, suite: running.TestSuite, result: result.TestSuite):
        pass

    def start_test(self, test: running.TestCase, result: result.TestCase):
        pass

    def start_keyword(self, data: running.Keyword, result: result.Keyword):
        pass

    def end_suite(self, suite: running.TestSuite, result: result.TestSuite):
        pass

    def end_test(self, test: running.TestCase, result: result.TestCase):
        pass

    def end_keyword(self, data: running.Keyword, result: result.Keyword):
        pass

    def log_message(self, message):
        pass

    def message(self, message):
        pass

    def debug_file(self, path):
        pass

    def output_file(self, path):
        pass

    def xunit_file(self, path):
        pass

    def log_file(self, path):
        pass

    def report_file(self, path):
        pass

    def close(self):
        pass
'''
