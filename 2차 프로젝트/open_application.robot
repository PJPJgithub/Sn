*** Settings ***
Library    AppiumLibrary

*** Variables ***
${REMOTE_URL}    http://127.0.0.1:4723/wd/hub
${PLATFORM_NAME}    Android
${DEVICE_NAME}    34inch_MY23S_API_32
${APP}    C:/Users/TZ47HH/AndroidStudioProjects/Snapnews-jungyu_CleanArchitecture/app/release/CarcontrolSim-0.1.0-release.apk
${AUTOMATION_NAME}    UiAutomator2
${PROXY_HOST}    koreaproxies.apa.gm.com
${PROXY_PORT}    80

*** Test Cases ***
Launch App
    [Documentation]    Launch the application
    Open Application    ${REMOTE_URL}    platformName=${PLATFORM_NAME}    deviceName=${DEVICE_NAME}    app=${APP}    automationName=${AUTOMATION_NAME}     proxyType=manual    httpProxy=${PROXY_HOST}:${PROXY_PORT}    sslProxy=${PROXY_HOST}:${PROXY_PORT}
    Sleep    5 seconds
    Close Application