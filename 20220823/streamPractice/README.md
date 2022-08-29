# 陳易伯

## 2022 年 8 月 23 日 作業

程式練習：
1. 請寫一個Java視窗應用程式，以List<Map<String, String>>物件初始化一個animalList，並填入以下內容：
```
{
    animalList:[
        {"name":"shark", "habitat":"ocean"},
        {"name":"bear", "habitat":"land"},
        {"name":"moose", "habitat":"land"},
        {"name":"frog", "habitat":"swamp"},
        {"name":"jelly fish", "habitat":"ocean"},
        {"name":"heron", "habitat":"swamp"},
        {"name":"whale", "habitat":"ocean"}
    ]
}
```
以habitat為key，將animalList重新包裝為Map<String, List<String>>物件，並依照以下格式在Console打印出每一個habitat底下的動物：
```
ocean: shark, jelly fish
...
```

2. 在同一應用程式中，以Map<String, String>物件初始化一個capitalMap，並填入以下內容：
```
{
    "USA":"Washington",
    "Japan":"Tokyo",
    "Thailand":"Bangkok",
    "UK":"London",
    "Australia":"Canberra",
    "Denmark":"Copenhagen",
    "Egypt":"Cairo",
    "Vietnam":"Hanoi",
    "Italy":"Rome",
    "Brazil":"Brazilia"
}
```
以for或foreach迴圈語法，將capitalMap的內容依下列格式打印在Concole上：
```
USA: Washington
...

