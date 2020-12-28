import re
import numpy as np
import os

from fastapi.middleware.cors import CORSMiddleware
import pandas as pd
from fastapi import FastAPI, Form, status
import requests
from pydantic import BaseModel
from fastapi import Body, FastAPI
from selenium import webdriver
import time
driver = webdriver.Chrome(executable_path=r'D:\Projects\Automation\Selenuim -Python\drivers\chromedriver.exe')


app = FastAPI()


origins = ["http://localhost", "http://localhost:8080", "*"]


app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get('/order')
def Order():
    driver.get("https://www.netmeds.com/customer/account/login")


# driver.find_element_by_xpath('/html/body/app-root/app-layout/div/main/app-login/div[1]/div/div[1]/div[2]/div/div[2]/div[1]/a/span').click()
    driver.implicitly_wait(15)


    driver.find_element_by_xpath(
        '/html/body/app-root/app-layout/div/main/app-login/div[1]/div/div[1]/div[2]/div/div[1]/form/div[1]/input').send_keys('********')
    driver.find_element_by_xpath(
        '/html/body/app-root/app-layout/div/main/app-login/div[1]/div/div[1]/div[2]/div/div[1]/form/div[2]/button[1]').click()

    driver.implicitly_wait(15)
    driver.find_element_by_xpath(
        '//*[@id="login_received_pwd"]').send_keys('********')

    driver.find_element_by_xpath(
        '/html/body/app-root/app-layout/div/main/app-login/div[1]/div/div/div[2]/div[1]/form/div[2]/div[1]/button').click()
    driver.implicitly_wait(15)
    driver.find_element_by_xpath('/html/body/div/header/div/div/div[3]/a').click()
    driver.implicitly_wait(15)
    driver.find_element_by_xpath('/html/body/app-root/app-layout/div/main/app-m2/div[1]/div/div[1]/div[1]/div[2]/div/form/div[1]/ul/li/label/input').send_keys(
        r'D:\Projects\Automation\Selenuim -Python\WhatsApp Image 2020-07-09 at 15.17.46-1.jpeg')
    driver.find_element_by_xpath(
        '/html/body/app-root/app-layout/div/main/app-m2/div[1]/div/div[2]/div[2]/button').click()
    driver.implicitly_wait(15)
    driver.find_element_by_xpath(
        '/html/body/app-root/app-layout/div/main/app-m2review/div[1]/div/div[2]/div/div[1]/div[3]/div[2]/button').click()
# driver.find_element_by_xpath('//*[@id="mynetwork-nav-item"]/a').click()
# list1=driver.find

# print(len(list1))
# for ele in list1:
# print(type(ele))

# break
# print(title)
    time.sleep(25)
    driver.quit()

    return {
        "success":True,
        
    }

    
