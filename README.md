시작이유:  **좋아하는 게임**에서 레이팅 점수의 정확성을 높이고자 사이드 프로젝트 시작 <br>

## 만든 api<br>
<img width="158" height="136" alt="image" src="https://github.com/user-attachments/assets/6180e882-04de-430e-8d35-4759a79b229c" />
<br><br>

### GET 유저 존재 확인


```
http://localhost:8081/check/noCap_noSpot_noHelp_Fxxk
====================================================

{
    "userId": "2020639284",
    "nickname": "nocap_nospot_nohelp_fxxk"
}
```
<br>

### GET 유저 레이팅 확인


```
http://localhost:8081/rate/nocap_nospot_nohelp_fxxk
====================================================

{
    "tatal": {
        "numOfGames": 189,
        "ratingScore": 1303,
        "wins": 43.51961184469367,
        "killRate": 79.10116748374936
    },
    "today": {
        "numOfGames": 4,
        "ratingScore": 1397,
        "wins": 39.015151515151516,
        "killRate": 225.40482967601716
    },
    "week": {
        "numOfGames": 7,
        "ratingScore": 1547,
        "wins": 31.902290688390032,
        "killRate": 0.0
    },
    "month": {
        "numOfGames": 10,
        "ratingScore": 1031,
        "wins": 40.17683825836001,
        "killRate": 23.493547397613558
    },
    "over": {
        "numOfGames": 165,
        "ratingScore": 1297,
        "wins": 44.16970308559735,
        "killRate": 78.25422147299611
    }
}
```
<br>

### PUT 유저 레이팅 갱신


```
http://localhost:8081/rate/noCap_noSpot_noHelp_Fxxk/2020639284
====================================================
```
<br>

### GET 유저 히스토리 조회


```
http://localhost:8081/history/2020639284)
====================================================

[
    {
        "shipName": "North Carolina",
        "winsRate": 0.0,
        "damage": 0.0,
        "killRate": 631.8675023172284,
        "tanking": 0.0,
        "rating": 1066,
        "lastPlayTime": 1762876415,
        "battleType": "solo"
    },
    {
        "shipName": "Missouri",
        "winsRate": 0.7,
        "damage": 0.0,
        "killRate": 269.75181638684023,
        "tanking": 0.0,
        "rating": 1639,
        "lastPlayTime": 1762875140,
        "battleType": "solo"
    },
    {
        "shipName": "Conqueror",
        "winsRate": 0.3939393939393939,
        "damage": 0.0,
        "killRate": 0.0,
        "tanking": 0.0,
        "rating": 1191,
        "lastPlayTime": 1762866944,
        "battleType": "solo"
    },
    {
        "shipName": "Musashi",
        "winsRate": 0.4666666666666667,
        "damage": 0.0,
        "killRate": 0.0,
        "tanking": 0.0,
        "rating": 1625,
        "lastPlayTime": 1762864067,
        "battleType": "solo"
    },
    ...
    ...
]
```

<br><br><br>

### 느낀 한계점
<br>

[내용]
- 게임에서 제공되는 api의 정보가 정확하지 않음
- 때문에 레이팅 수치를 정확하게 보정해주려면 모든 히스토리를 추적하여 모든 플레이어의 모든 함선별 누적 통계를 저장해야 한다.
- 통계 데이터를 기준으로 현재 조회한 데이터와 통계 데이터간의 비교하여 최종 레이팅을 계산하는것이 가장 정확하다.

<br>

[비용]
- 막대한 유저들의 데이터를 항상 수집하고 보유하고 있어야만이 정확한 레이팅 산출이 가능하다.
- 모든 활성 플레이어의 통계를 가져오기 위해 주기적으로 Wargaming API를 호출해서 저장해야한다. => 단기간에 수백GB, 이후 TB 단위가 예상됨 => 비싼 DB 인스턴스의 비용이 예상됨
- 내부적으로 캐시를 사용하기 때문에 캐시 인프라 구축 비용도 추가 => 대부분이 유료

[생각]
- 나의 아이디어를 단순히 코딩으로 녹여내는것에 나아가서 배포 운영까지 판단했을때 과연 현실성이 있는가? 에 대해 많이 생각하는 계기가 되었다
- 최대한 적은 비용으로 수익을 낼 수 있는 아이디어를 떠올리면 좋겠다.
- 적게 코딩하고 빠르게 실행해서 프로토 타입을 많이 만들어보자.





```


