<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Paragraph Variable</title>
</head>
<body>
    <div class="indexmain">
        <h1>Paragraph Variable 1.0</h1>
        <!-- <p>각 파트마다의 번역 데이터를 검색할 수 있습니다.</p> -->
        <p>
            <a href="https://www.notion.so/asttcs/27fe812d68f546998044f76e8186b615" target="_blank">사용 설명서</a> | 
            <a href="https://www.notion.so/asttcs/6a9f64525f8140efbbe7a641d39ef206" target="_blank">업데이트 이력</a> |
            <a href="https://wp2.astkorea.net/task/doc/#view/202101260858100225b" target="_blank">수정 요청</a> |
            <a href="multisearch.html" target="_blank">다중 언어 검색</a>
        </p>
        <form id="tmxSearch" action="result.html" method="GET" autocomplete="off">
            <input name="byCase" id="byCase" type="checkbox" checked/><lable for="byCase">대소문자 구분</lable>
            <select name="part" id="part">
                <option lang="defalut" value="defalut" selected>파트를 선택하세요.</option>
                <option lang="EBT" value="EBT">EBT</option>
                <option lang="TV" value="TV">TV</option>
                <option lang="MOBIS" value="MOBIS">MOBIS</option>
                <option lang="MA-Device" value="MA-Device">MA-Device</option>
                <option lang="MA-AC" value="MA-AC">MA-AC</option>
                <option lang="MA-Warranty" value="MA-Warranty">MA-Warranty</option>
                <option lang="MA-Feature" value="MA-Feature">MA-Feature</option>
                <option lang="HA" value="HA">HA</option>
                <!-- <option lang="HY-Auto" value="HY-Auto">HY-Auto</option> -->
            </select>
            <select name="lang" id="lang">
                <option lang="defalut" value="defalut" selected>언어를 선택하세요.</option>
                <option lang="ALB" value="ALB">Albanian</option>
                <option lang="ARA" value="ARA">Arabic</option>
                <option lang="ARA-AS" value="ARA-AS">Arabic(Asia)</option>
                <option lang="ARA-EU" value="ARA-EU">Arabic(EU)</option>
                <option lang="AZE" value="AZE">Azerbaijani</option>
                <option lang="BEN" value="BEN">Bengali</option>
                <option lang="BOS" value="BOS">Bosnian</option>
                <option lang="BUL" value="BUL">Bulgarian</option>
                <option lang="Zawgyi-BUR" value="Zawgyi-BUR">Burmese(Zawgyi)</option>
                <option lang="SamsungOne-BUR" value="SamsungOne-BUR">Burmese(SamsungOne)</option>
                <option lang="S-CHI" value="S-CHI">Chinese(중국)</option>
                <option lang="HKG" value="HKG">Chinese(홍콩)</option>
                <option lang="SG-CHI" value="SG-CHI">Chinese(싱가폴)</option>
                <option lang="TPE" value="TPE">Chinese(대만)</option>
                <option lang="CRO" value="CRO">Croatian</option>
                <option lang="CZE" value="CZE">Czech</option>
                <option lang="DAN" value="DAN">Danish</option>
                <option lang="DUT" value="DUT">Dutch</option>
                <option lang="EST" value="EST">Estonian</option>
                <option lang="FAR" value="FAR">Farsi</option>
                <option lang="FIN" value="FIN">Finnish</option>
                <option lang="FRE" value="FRE">French</option>
                <option lang="CA-FRE" value="CA-FRE">French(캐나다)</option>
                <option lang="GEORGIAN" value="GEORGIAN">Georgian</option>
                <option lang="GER" value="GER">German</option>
                <option lang="GRE" value="GRE">Greek</option>
                <option lang="GUJ" value="GUJ">Gujarati</option>
                <option lang="HEB" value="HEB">Hebrew</option>
                <option lang="HIN" value="HIN">Hindi</option>
                <option lang="HUN" value="HUN">Hungarian</option>
                <option lang="IND" value="IND">Indonesian</option>
                <option lang="ITA" value="ITA">Italian</option>
                <option lang="JPN" value="JPN">Japanese(일본)</option>
                <option lang="KAN" value="KAN">Kannada</option>
                <option lang="KAZ" value="KAZ">Kazakh</option>
                <option lang="KIR" value="KIR">Kyrgyzstan</option>
                <option lang="ENGB2KOR" value="ENGB2KOR">English(GB)2Korean</option>
                <option lang="ENUS2KOR" value="ENUS2KOR">English(US)2Korean</option>
                <option lang="KOR2ENG-GB" value="KOR2ENG-GB">Korean2English(GB)</option>
                <option lang="KOR2ENG-US" value="KOR2ENG-US">Korean2English(US)</option>
                <option lang="LAO" value="LAO">Lao</option>
                <option lang="LAT" value="LAT">Latvian</option>
                <option lang="LIT" value="LIT">Lithuanian</option>
                <option lang="Main-KHM" value="Main-KHM">Khmer</option>
                <option lang="MAY" value="MAY">Malayalam</option>
                <option lang="MAR" value="MAR">Marathi</option>
                <option lang="MKD" value="MKD">Macedonian</option>
                <option lang="MON" value="MON">Mongolian</option>
                <option lang="CNR" value="CNR">Montenegrian</option>
                <option lang="BUR" value="BUR">Myanmar</option>
                <option lang="NOR" value="NOR">Norwegian</option>
                <option lang="ODI" value="ODI">Odia</option>
                <option lang="POL" value="POL">Polish</option>
                <option lang="POR" value="POR">Portuguese</option>
                <option lang="PUN" value="PUN">Punjabi</option>
                <option lang="B-POR" value="B-POR">Portuguese(브라질)</option>
                <option lang="ROM" value="ROM">Romanian</option>
                <option lang="RUS" value="RUS">Russian</option>
                <option lang="SER" value="SER">Serbian</option>
                <option lang="KOR2S-CHI" value="KOR2S-CHI">Korean2Chinese(중국)</option>
                <option lang="SC2ENG" value="SC2ENG">Chinese(S)2English</option>
                <option lang="SLK" value="SLK">SlovaK</option>
                <option lang="SLV" value="SLV">SloVenian</option>
                <option lang="SPA" value="SPA">Spanish</option>
                <option lang="M-SPA" value="M-SPA">Spanish(멕시코)</option>
                <option lang="Ltn-SPA" value="Ltn-SPA">Spanish(라틴)</option>
                <option lang="SWE" value="SWE">Swedish</option>
                <option lang="RGK" value="RGK">Tajikistan</option>
                <option lang="TAM" value="TAM">Tamil</option>
                <option lang="TEL" value="TEL">Telugu</option>
                <option lang="THA" value="THA">Thai</option>
                <option lang="TUR" value="TUR">Turkish</option>
                <option lang="TUK" value="TUK">Turkmenistan</option>
                <option lang="UKR" value="UKR">Ukrainian</option>
                <option lang="URD" value="URD">Urdu</option>
                <option lang="UZB" value="UZB">Uzbek</option>
                <option lang="VIE" value="VIE">Vietnamese</option>
            </select>
            <input name="searchString" id="searchString" type="text"/>
            <input type="submit" value="검색" />
        </form>
    </div>
</body>
</html>