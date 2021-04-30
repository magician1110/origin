<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>About Hmm</title>
      <meta charset="utf-8">
        <link href="resources/css/about.css" rel="stylesheet" type="text/css">
          <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
 <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
            <body>
              <%@ include file="/header.jsp"%>

              <%-- 팀 사진 --%>
              <div class="the_container">
                <h1>Hmm 팀을 소개합니다.</h1>
                <img id="the_team" src="resources/img/about_hmm.jpg"/>
              </div>
              <div class="the_container">
                <h2>Hmm에 쏟아지는 사회 곳곳에서의 찬사!</h2>
                <div id="myCarousel" class="carousel slide text-center" data-ride="carousel">
                  <!-- Indicators -->
                  <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                    <li data-target="#myCarousel" data-slide-to="3"></li>
                    <li data-target="#myCarousel" data-slide-to="4"></li>
                  </ol>

                  <!-- 슬라이드 -->
                  <div class="carousel-inner" role="listbox">
                    <div class="item active">
                      <h4>"Hmm 최고의 IT 커뮤니티입니다. 자신있게 추천합니다."<br>
                          <span style="font-style:normal;">빌 게이츠, 前 마이크로소프트 사장, 전라남도 전주시 덕진구</span>
                        </h4>
                      </div>
                      <div class="item">
                        <h4>"이것은 혁명이다... 이전까지 이런 커뮤니티는 존재하지 않았다."<br>
                            <span style="font-style:normal;">김효숙, 이재훈 어머니, 청솔 부동산 대표</span>
                          </h4>
                        </div>
                        <div class="item">
                          <h4>"신이시여, 왜 다음카카오를 만드시고 Hmm을 다시 만드셨습니까?"<br>
                              <span style="font-style:normal;">스티브 잡스, 前 애플뮤직 대표, 무직</span>
                            </h4>
                          </div>
                          <div class="item">
                            <h4>"아, 배고프다. 피자 먹고 싶어."<br>
                                <span style="font-style:normal;">이기승, 웹개발 취업 희망자, 상록시티 주민</span>
                              </h4>
                            </div>
                            <div class="item">
                              <h4>"대한민국 대표 유산균 청인 딱좋아! 장 건강에 딱 좋아! 쾌변에 딱 좋아!"<br>
                                  <span style="font-style:normal;">박세준, 힐링 바이오 대표, 변비 환자</span>
                                </h4>
                              </div>
                        </div>

                        <!-- Left and right controls -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                          <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                          <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                          <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                          <span class="sr-only">Next</span>
                        </a>
                      </div>
                    </div>


                    <%-- Hmm 살펴보기 --%>
                    <div class="the_container">
                      <div class="container-fluid text-center bg-grey">
  <h2>Hmm 살펴보기</h2>
  <h4>우리 커뮤니티는 다음과 같은 기능을 제공합니다.</h4>
  <div class="row text-center">
    <div class="col-sm-4">
      <div class="thumbnail">
        <img src="resources/img/function1.JPG">
        <h3><strong>손쉬운 커뮤니티 활동</strong></h3>
        <h4>원하는 대로 즉시 정렬 가능한 게시글들!</h4>
      </div>
    </div>
    <div class="col-sm-4">
      <div class="thumbnail">
        <img src="resources/img/function2.JPG">
        <h3><strong>트렌드를 파악할 수 있는 주간투표</strong></h3>
        <h4>가장 뜨거운 화젯거리를 언제든지 살펴볼 수 있습니다</h4>
      </div>
    </div>
    <div class="col-sm-4">
      <div class="thumbnail">
        <img src="resources/img/function3.JPG">
        <h3><strong>남아도는 돈으로 유료결제!</strong></h3>
        <h4>물론 할인은 없습니다(환불도 없음)</h4>
      </div>
    </div>
</div>
</div>
</div>
                    <%-- <div class="the_container">
                      <h1>TESTING</h1>
                    </div>
                    <div class="the_container">
                      <h1>TESTING</h1>
                    </div>
                    <div class="the_container">
                      <h1>TESTING</h1>
                    </div> --%>

                  </body>
                  <%@ include file="/footer.jsp"%>

                </html>
