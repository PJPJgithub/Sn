*** Settings ***
Library    AppiumLibrary
Resource   %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_button.resource
Resource   %{AUTOMATION_HOME}/project/keywords/log_check.resource
Resource   %{AUTOMATION_HOME}/project/keywords/tts/voice_command.resource
Resource   %{AUTOMATION_HOME}/project/keywords/wait_seconds.resource
Resource    %{AUTOMATION_HOME}/project/keywords/verify/verify_news_image.resource
Variables  %{AUTOMATION_HOME}/project/variables/voice_speed.py
*** Test Cases ***
Senario Test
    [Documentation]    Test full senario.
    [Tags]    button click tts stt news
    Open Main Activity
    Provide Voice Command    삼성전자 뉴스 알려 줘    ${voice_speed}
    Log check    News fetched successfully
    
    Press Button    voice_button
    Provide Voice Command    다음    ${voice_speed}
    Log check    Next news headline
    
    Press Button    voice_button
    Provide Voice Command    계속 말해 줘    ${voice_speed}
    Wait    12
    
    Press Button    voice_button
    Provide Voice Command    이전    ${voice_speed}
    Log check    Previous news headline
    Press Button    news_image
    Press Button    back_button

    Press Button    voice_button
    Provide Voice Command    GM 뉴스 알려 줘    ${voice_speed}
    Log check    News fetched successfully

    Press Button    voice_button
    Provide Voice Command    내용    ${voice_speed}
    Press Button    list_button
    Press Button    headline_text_view
    Press Button    back_button
    Press Button    back_button

    Press Button    reset_button
    Press Button    next_button
    Press Button    prev_button
    Press Button    list_button
    Press Button    back_button

    Press Button    voice_button
    Provide Voice Command    AI 뉴스 알려 줘    ${voice_speed}
    Press Button    list_button
    Press Button    headline_text_view
    Press Button    back_button
    Press Button    back_button

    Press Button    voice_button
    Provide Voice Command    종료    ${voice_speed}

*** Keywords ***
#수정필요
Verify News Image
    Element Should Be Visible    android=new UiSelector().resourceId("com.gm.carcontrolsim:id/news_image")