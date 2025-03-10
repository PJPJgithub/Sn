import os
import sys
#import platform
sys.path.append(os.environ.get('AUTOMATION_HOME'))

__all__ = [
    'appium', 
    'platform_name',
    'device_name',
    'automation_name',
    'chromedriver_executable'
]

def get_chromedriver_path():
    pass

appium = {
    "ip": "127.0.0.1",
    "port": 4723,
    "base_path": "/wd/hub",
}

appium["url"] = f"http://{appium['ip']}:{appium['port']}{appium['base_path']}"

platform_name = "Android"

device_name = "emulator-5554"

automation_name = "Uiautomator2"

chromedriver_executable = get_chromedriver_path()