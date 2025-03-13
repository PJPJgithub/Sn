*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${URL}    https://www.naver.com
${BROWSER}    Chrome
${PROXY}    http://koreaproxies.apa.gm.com:80

*** Test Cases ***
Open Naver With Proxy
    ${options}=    Evaluate    sys.modules['selenium.webdriver'].ChromeOptions()    sys, selenium.webdriver
    Call Method    ${options}    add_argument    ${PROXY}
    Create WebDriver    Chrome    options=${options}
    Go To    ${URL}
    Title Should Be    NAVER
    Close Browser