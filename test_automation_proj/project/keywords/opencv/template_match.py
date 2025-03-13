import cv2
import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

@keyword("Match Icons")
def match_icons(icon_list):
    # AUTOMATION_HOME 환경 변수에서 이미지 경로 가져오기
    automation_home = os.environ.get('AUTOMATION_HOME')
    if not automation_home:
        print("The AUTOMATION_HOME environment variable is not set.")
        sys.exit(1)
 
    # 스크린샷 이미지 경로 설정
    screen_image_path = os.path.join(automation_home, "project", "resources", "my_screenshot.png")
 
    # 스크린샷 이미지 로드
    screen_img = cv2.imread(screen_image_path)
    if screen_img is None:
        print(f"Unable to load screenshot images: {screen_image_path}")
        sys.exit(1)
 
    # 아이콘 리스트를 순회하며 매칭 수행
    for icon_filename in icon_list:
        icon_image_path = os.path.join(automation_home, "project", "test_data", "icon_image", icon_filename)
 
        # 아이콘 이미지 로드
        icon_img = cv2.imread(icon_image_path, cv2.IMREAD_UNCHANGED)  # 알파 채널 포함하여 로드
        if icon_img is None:
            print(f"Unable to load icon images: {icon_image_path}")
            continue
        
        # XML에서 지정한 크기 (50dp를 픽셀로 변환, 여기서는 1dp = 1px로 가정)
        if icon_filename in ['left_image.jpg', 'right_image.jpg']:
            xml_width = 180
            xml_height = 180
        else:
            xml_width = 150
            xml_height = 150
 
        # 아이콘 이미지 리사이즈 (XML에서 지정한 크기로)
        icon_img_resized = cv2.resize(icon_img, (xml_width, xml_height))
 
        # 알파 채널이 있는지 확인
        if len(icon_img_resized.shape) == 3 and icon_img_resized.shape[2] == 4:
            continue
        else:
            # 알파 채널이 없는 경우
            icon_img_bgr = icon_img_resized
 
        # 템플릿 매칭 수행 (TM_CCOEFF_NORMED 방법 사용)
        result = cv2.matchTemplate(screen_img, icon_img_bgr, cv2.TM_CCOEFF_NORMED)
        min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
 
        # 임계값 설정 (예: 0.8 이상이면 동일한 이미지로 간주)
        threshold = 0.9
 
        if max_val >= threshold:
            print(f"{icon_filename}, The icon image is the same.")
        else:
            print(f"{icon_filename}, The icon image is not the same.")
 
        print(f"Matching Score: {max_val:.2f}")
 
        # 매칭된 부분에 사각형 그리기
        top_left = max_loc  # 최대 매칭 위치
        h, w = icon_img_bgr.shape[:2]  # 아이콘 이미지의 높이와 너비
 
        # 매칭된 부분을 빨간색으로 사각형 표시 (cv2.rectangle)
        cv2.rectangle(screen_img, top_left, (top_left[0] + w, top_left[1] + h), (0, 0, 255), 2)
 
    # 크기 조정하여 화면에 띄우기 (이미지가 너무 크면 화면에 맞게 크기를 줄이기)
    scale_percent = 50  # 50% 크기로 축소
    width = int(screen_img.shape[1] * scale_percent / 100)
    height = int(screen_img.shape[0] * scale_percent / 100)
    dim = (width, height)
 
    # 이미지를 새 크기로 리사이즈
    resized_screen_img = cv2.resize(screen_img, dim, interpolation=cv2.INTER_AREA)
 
    # 크롭된 이미지와 아이콘 이미지 화면에 띄우기
    cv2.imshow("Cropped Image with Match", resized_screen_img)
    cv2.waitKey(2000)
    cv2.destroyAllWindows()
 
    # 저장할 경로 설정
    output_dir = os.path.join(automation_home, "reports", "output")
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
 
    output_path = os.path.join(output_dir, "button_match.png")
 
    # 기존 파일이 있으면 삭제
    if os.path.exists(output_path):
        os.remove(output_path)
 
    # 리사이즈된 이미지를 파일로 저장
    cv2.imwrite(output_path, resized_screen_img)
 
    print(f"The resized image has been saved to the {output_path}.")