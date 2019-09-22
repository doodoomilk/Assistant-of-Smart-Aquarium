# Assistant-of-Smart-Aquarium
Final Project of Cloud Programming class in NTHU

這個Final project其實是`Raspberry`、`Arduino`、`AWS cloud`、`Android app`的結合
但我主要負責Android app及AWS cloud的結合

Here, only talking about my code.
For more information, please go to [Wiki](https://github.com/doodoomilk/Assistant-of-Smart-Aquarium/wiki) page.

## AWS Services I used
![](https://i.imgur.com/BklhSP4.png)

## Code Introduction

### 註冊
![](https://i.imgur.com/L4z1tcs.png)


1. APP會透過Cognito來進行身份管理
2. 當接收到新身份時，Cognito會還通知到Lambda function
3. Lambda 便會觸發SNS service
4. SNS會送出驗證身份的email到註冊信箱
5. 使用者要到註冊信箱收認證信，確認身份

相關程式碼：
- AuthenticatorActivity.java


### 搜集資料＆查看
![](https://i.imgur.com/feIGac5.png)

1. 水位、溫度、水質sensor都會感測環境資訊、感測到之後傳到Arduino
2. 之後arduino會再傳至Raspberry pi做後續的資料傳遞
3. Rapberry pi可以用MQTT傳資料至IoT Core
4. 再由IoT Core將資料存到DynamoDB(database)
5. 由app使用者觸發到DB取資料

相關程式碼：
- HackLevelDO.java
- HackTempDO.java
- HackTurbidDO.java
- level.java
- temperature.java
- turbid.java
- MyAdapter.java
- MainActivity.java

### Sensor超標警示
![](https://i.imgur.com/sx1YwsO.png)

1. 水位、溫度、水質sensor都會感測環境資訊、感測到之後傳到Arduino
2. 之後arduino會再傳至Raspberry pi做後續的資料傳遞
3. Rapberry pi可以用MQTT傳資料至IoT Core
4. 再由IoT Core將資料存到DynamoDB(database)
5. 由DynamoDB將資料傳到Lambda
6. Lambda function若是發現資料超標的話，便會觸發SNS
7. SNS會送出警示到使用者email
8. ~10. APP的推播通知是由Firebase實作，因此會使用到Pinpoint service來傳送警告訊息

相關程式碼：
- MyInstanceIDService.java

### Data Training
![](https://i.imgur.com/mecP8IT.png)

1. ~4.都跟"Sensor超標警示一樣"
5. 用cloudwatch設定，特定時間進行training，時間到會觸發Lambda
6. Lambda會到DB取資料出來
7. 因為SageMaker是透過S3取資料，因此要先丟到S3
8. SageMaker從S3取資料進行training，並將結果回傳

相關程式碼：
此段與app無關，故無。

### Sensor偵測預警
![](https://i.imgur.com/9p8xHx6.png)

1. ~4.都跟"Sensor超標警示一樣" （這張圖有些微畫錯，4.應該接到DB）
5. 將上一階段training的結果傳到Lambda
6. 若training結果發現，"即將"(不久後)環境可能發生問題，便會觸發SNS
7. SNS會送出警示到使用者email
8. ~9. APP的推播通知是由Firebase實作，因此會使用到Pinpoint service來傳送警告訊息

相關程式碼：
- MyInstanceIDService.java

### 設備故障警示
![](https://i.imgur.com/OmTym5L.png)

1. 當設備故障時，狀態會傳到Arduino
2. 之後arduino會再傳至Raspberry pi做後續的資料傳遞
3. Rapberry pi可以用MQTT傳資料至IoT Core
4. 故障資訊不必存到DB，所以直接觸發Lambda
5. Lambda會觸發SNS
6. SNS會送出警示到使用者email
7. ~8. APP的推播通知是由Firebase實作，因此會使用到Pinpoint service來傳送警告訊息

相關程式碼：
- MyInstanceIDService.java

### User Control
![](https://i.imgur.com/i0GhJJI.png)

1. 由APP觸發，APP需要綁API gateway
2. 會由API gateway觸發Lambda
3. Lambda會連接至IoT Core，畢竟是由iot core管理全部的iot devices
4. ~6. 將user control message傳達到device那邊做開關控制

相關程式碼：
- MyAdapter.java
- menu.java
- MainActivity.java


### Camera
![](https://i.imgur.com/poYjhHB.png)

1. 由rapberry camera進行影像拍攝，並傳到pi上面
2. 由pi傳到Kinesis做影像處理，處理成即時串流
3. 從APP端開啟相關的串流web進行即時串流播放

相關程式碼：
- MyAdapter.java
- MainActivity.java
- menu.java
- set.java

