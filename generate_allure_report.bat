@echo off
set path=C:\Users\stc\.m2\repository\allure\allure-2.13.2\bin;%path%
allure serve allure-results
pause
exit