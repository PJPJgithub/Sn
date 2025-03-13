*** Settings ***
Library    AppiumLibrary
Library    Process

*** Keywords ***
Run Setup
    Run Process    powershell.exe    -File    setup/setup-script.ps1    shell=True