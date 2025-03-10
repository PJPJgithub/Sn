import os
import sys
import subprocess
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

@keyword("Run Unit Test")
def run_unit_test():
    try:
        # 작업 디렉토리 설정
        project_dir = r'C:\Users\TZ47HH\AndroidStudioProjects\Snapnews_project1\Snapnews'
        os.chdir(project_dir)
        print(f"Changed working directory to {project_dir}")

        # gradlew 파일 경로 설정
        gradlew_path = 'C:/Users/TZ47HH/AndroidStudioProjects/Snapnews_project1/Snapnews/gradlew'
        print(f"Using gradlew at {gradlew_path}")

        # Gradle을 사용하여 테스트 실행
        result = subprocess.run([gradlew_path, 'test'], capture_output=True, text=True, shell=True)
        print("Gradle test execution completed")

        # 결과 파일 경로 설정
        result_file_path = r'C:\\Users\\TZ47HH\\AndroidStudioProjects\\Snapnews_project1\\Snapnews\\test_automation_proj\\reports\\unit_test_result.txt'
        print(f"Saving results to {result_file_path}")

        # 실행 결과를 파일로 저장
        with open(result_file_path, 'w', encoding='utf-8') as f:
            f.write(result.stdout)
            f.write(result.stderr)
        print("Results saved")

    except Exception as e:
        error_message = f"An error occurred: {str(e)}"
        print(error_message)
        # 에러 발생 시 결과 파일에 에러 메시지 기록
        result_file_path = r'C:\\Users\\TZ47HH\\AndroidStudioProjects\\Snapnews_project1\\Snapnews\\test_automation_proj\\reports\\unit_test_result.txt'
        with open(result_file_path, 'w', encoding='utf-8') as f:
            f.write(error_message)
        print("Error details saved to result file")