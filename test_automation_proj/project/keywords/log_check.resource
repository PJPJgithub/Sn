*** Settings ***
Library    AppiumLibrary
Library    Process

*** Keywords ***
Log check
    [Arguments]    ${text}
    Sleep    2seconds
    Wait Until Keyword Succeeds    10 sec    1 sec    Check    ${text}

Check
    [Arguments]    ${text}
    ${result}=    Run Process    adb logcat -d | findstr /c:"${text}"    shell=True
    ${output}=    Convert To String    ${result.stdout}
    Should Contain    ${output}    ${text}
    Run Process    adb logcat -c    shell=True    # 로그 초기화