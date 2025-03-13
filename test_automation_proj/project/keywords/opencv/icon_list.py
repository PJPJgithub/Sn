import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

@keyword("Get Icon List")
def get_icon_list():
    # AUTOMATION_HOME 환경 변수에서 경로 가져오기
    automation_home = os.environ.get('AUTOMATION_HOME')
    if not automation_home:
        raise EnvironmentError("The AUTOMATION_HOME environment variable is not set.")
 
    # 아이콘 이미지 디렉토리 경로 설정
    icon_dir = os.path.join(automation_home, "project", "test_data", "icon_image")

    # 아이콘 이미지 파일 리스트 가져오기
    icon_list = [f for f in os.listdir(icon_dir) if f.endswith('.jpg')]
 
    return icon_list
 
class IconList:
    def get_icon_list(self):
        return get_icon_list()