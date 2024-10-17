# 콘서트 예약 서비스 API 명세

## 1. 유저 토큰 발급 API
  - **설명** :  대기열을 위한 유저 토큰 발급 요청
  - **Request**
    - **Method** : `POST`
    - **URL** : `/users/token`
    - **Parameters**
      ```
        {
            "userId" : "Long"
        }
      ```
  - **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
        {
          "createdAt": "2024-10-11T02:33:55.6534115",
          "updateAt": null,
          "id": 1,
          "userId": 1,
          "status": "WAIT"
        }
      ```
  - **Error**
    - **HTTP Status Codes**
      - `400 Bad Request` : 유효하지 않은 요청

<br/>

## 2. 예약 가능 날짜 / 좌석 API
### 2-1. 예약 가능 날짜 API
- **설명** :  특정 콘서트의 예약 가능한 날짜 조회
- **Request**
    - **Method** : `GET`
    - **URL** : `/concerts/{concertId}/available-date`
    - **Parameters**
      ```
        {
            "concertId" : "Long"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
      {
        [
          {
            "concertOptionId":1,
            "concertDate":"2024-10-10"
          }
        ]
      }
      ```
- **Error**
    - **HTTP Status Codes**
        - `400 Not Found` : 존재하지 않은 콘서트

<br/>

### 2-2. 예약 가능 날짜 API
- **설명** :  특정 콘서트의 예약 가능한 좌석 조회
- **Request**
    - **Method** : `GET`
    - **URL** : `/concerts/{concertId}/available-seats`
    - **Parameters**
      ```
        {
            "concertId" : "Long",
            "date" : "String"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
      {
        [
          {
            "date": "20241010",
            "availableSeats": 50,
            "concertId": 1
          }
        ]
      }
      ```
- **Error**
    - **HTTP Status Codes**
        - `400 Not Found` : 존재하지 않은 콘서트

<br/>

## 3. 좌석 예약 요청 API
- **설명** :  좌석 예약을 요청
- **Request**
    - **Method** : `POST`
    - **URL** : `/reservations`
    - **Parameters**
      ```
        {
            "userId" : "Long",
            "seatId" : "Long"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
        {
          "createdAt": "2024-10-11T03:46:43.7761067",
          "updateAt": "2024-10-11T03:46:43.7761067",
          "seatId": 1,
          "id": 1,
          "userId": 1,
        }
      ```
- **Error**
    - **HTTP Status Codes**
        - `401 Unauthorized` : 유효하지 않은 토큰

<br/>

## 4. 잔액 조회/충전 API
### 4-1. 잔액 조회 API
- **설명** :  유저 포인트를 조회
- **Request**
    - **Method** : `GET`
    - **URL** : `/customers/point`
    - **Parameters**
      ```
        {
            "userId" : "Long"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
      {
        [
          {
            "balance": 10000,
            "userId": 1
          }
        ]
      }
      ```
- **Error**
    - **HTTP Status Codes**
        - `400 Not Found` : 존재하지 않은 유저

<br/>

### 4-2. 잔액 충전 API
- **설명** :  유저 포인트를 충전
- **Request**
    - **Method** : `PATCH`
    - **URL** : `/customers/point`
    - **Parameters**
      ```
        {
            "userId" : "Long",
            "chargeBalance" : "Long"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
      {
        [
          {
            "balance": 2000,
            "userId": 1
          
          }
        ]
      }
      ```
- **Error**
    - **HTTP Status Codes**
        - `400 Not Found` : 존재하지 않은 콘서트

<br/>

## 5. 결제 API
- **설명** :  결제를 요청
- **Request**
    - **Method** : `POST`
    - **URL** : `/payments`
    - **Parameters**
      ```
        {
            "reservationId" : "Long",
            "amount" : "Long"
        }
      ```
- **Response**
    - **HTTP Status Codes** : `200 OK`
      ```
        {
          "reservation_id": 1,
          "amount": 1000,
          "paymentDate": "2024-10-11T04:07:27.6842701"
        }
      ```
- **Error**
    - **HTTP Status Codes**
        - `400 Not Found` : 존재하지 않은 예약정보

