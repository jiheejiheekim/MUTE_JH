<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>MU:TE</title>
<link rel="stylesheet" href="resources/css/result.css">
<link rel="stylesheet" href="resources/css/modal.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://sdk.scdn.co/spotify-player.js"></script>
</head>
<body>
	<header>
  <div id="navi-container">
    <table id="navi-head">
      <tr>
        <td class="head-ba"><img class="back" src="resources/images/gom_button.png"></td>
      </tr>
      <tr>
        <td class="head-ba" rowspan="3"><a class="backft" style="color: lightgray;" href="/mute/mypage">마이페이지</a></td>
      </tr>
    </table>
  </div>
</header>

	<table class="table1">
		<tr>
			<td><img alt="gom_dance" src="resources/images/gom_dance.png"
				height="500" width="500"></td>
		</tr>
		<tr>
			<td><a class="dance" style="color: #FCAD36;">댄스를 좋아하는 당신은
					레인보우 샤베트!</a></td>
		</tr>
		<tr>
			<td><a class="dance" style="color: #FCAD36;">이브 프시케 그리고 레인보우
					샤베트.</a></td>
		</tr>
	</table>
	<br>

	<div class="con">
		<div class="reco">
			<br> <a class="tag">당신을 위한 #댄스 음악</a><br> <br>
			<table class="table2">
			<tr id="trhead">
				<td></td>
				<td>제목</td>
				<td>가수</td>
				<td>재생</td>
				<td>플레이리스트 추가</td>
			</tr>
            <c:if test="${not empty recommendations}">
                <c:forEach var="track" items="${recommendations}">
                    <tr>
                        <td>
                            <div class="cover">
                                <img src="${track.coverImageUrl}" alt="Album Cover" width="100" height="100">
                            </div>
                        </td>
                        <td>
                            ${track.name}
                        </td>
                        <td>
                        	 ${track.artistName}
                        </td>
                        <td>
							<img class="playPauseImage" src="<c:url value='/resources/images/play_pl.png'/>"
						    alt="Play/Pause" width="50" height="50" data-track-uri="${track.uri}"
						    onclick="togglePlayPause('${track.uri}', this);">
                        </td>
                        <td>
		                <a data-track-id="${track.id}" onclick="openPlaylistModal('${track.id}'); toggleModal('addModal')">
		                    <img src="resources/images/plus_pl.png" alt="plus">
		                </a>
		            </td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
		<br>
		<table class="table3">
			<tr>
				<td><button class="brereco"><a class="rereco" href="" style="color: black;"><img alt="rrreco" src="resources/images/rereco.png"> 유사한 3곡 다시 추천받기</a></button></td>
				<td><button class="brereco"><a class="rereco" href="/mute/main" style="color: black;"><img alt="rrreco" src="resources/images/repeat.png"> 다시 테스트하기</a></button></td>
			</tr>
		</table>
		<br>
		</div>
		<!-- <br> -->
		
		<div class="table4div">
			<table class="table4">
				<tr>
					<td class="table4head yeshover-container" colspan="2">　이것도 하나 주세요!　<img class="yes" src="resources/images/question.png">
					<div id="yeshover">내 취향과 비슷한 장르입니다!<br>친구들과 결과를 공유해보세요</div>
					</td>
				</tr>
				<tr>
					<td><a href="/mute/result_disco"><img class="table4img" src="resources/images/gom_disco.png"></a></td>
					<td><a href="/mute/result_techno"><img class="table4img" src="resources/images/gom_trot.png"></a></td>
				</tr>
				<tr>
					<td class="table4icname"><a href="/mute/result_disco" style="color:#C56834;">아몬드봉봉</a></td>
					<td class="table4icname"><a href="/mute/result_techno" style="color:#FF3232;">베리베리스트로베리</a></td>
				</tr>
				<tr>
					<td class="table4tag"><a href="/mute/result_disco" style="color: black;">#디스코</a></td>
					<td class="table4tag"><a href="/mute/result_techno" style="color: black;">#테크노</a></td>
				</tr>
			</table>
			
			<table class="table4">
				<tr>
					<td class="table4head nohover-container" colspan="2">　이 맛은 빼고 주세요!　<img class="no" src="resources/images/question.png">
					<div id="nohover">내 취향과 거리가 먼 장르입니다!<br>친구들과 결과를 공유해보세요</div>
					</td>
				</tr>
				<tr>
					<td><a href="/mute/result_folk"><img class="table4img" src="resources/images/gom_ballad.png"></a></td>
					<td><a href="/mute/result_indie"><img class="table4img" src="resources/images/gom_indie.png"></a></td>
				</tr>
				<tr>
					<td  class="table4icname"><a href="/mute/result_folk" style="color:#512626;">초코,우리이제헤이즐넛</a></td>
					<td class="table4icname"><a href="/mute/result_indie" style="color:#F34242;">사랑에빠진딸기</a></td>
				</tr>
				<tr>
					<td class="table4tag"><a href="/mute/result_folk" style="color: black;">#발라드</a></td>
					<td class="table4tag"><a href="/mute/result_indie" style="color: black;">#인디</a></td>
				</tr>
			</table>
		</div>

		<br>
	</div>
	<br>
	<!-- ================================================ -->
	<!-- The Modal -->
	<!-- 첫 번째 모달 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <form name="rf" id="rf">
            	<div id="scroll">
                <!-- 현재 사용자의 플레이리스트 목록을 표시할 테이블 -->
                <table class="modaltable" id="modaltable">
                      <c:forEach var="playlist" items="${playlists}">
                        <tr>
                            <td class="td"><img alt="gom_trot" src="resources/images/gom_button.png" height="65" width="65"></td>
                            <td class="td"><a class="pltitle text-body" href="" data-playlist-id="${playlist.id}"
                                    onclick="addTrackToPlaylist('${playlist.id}')">${playlist.name}</a></td>
                        </tr>
                    </c:forEach> 
                </table>
                </div>
                <br>
                <button type="button" class="close-btn" onclick="toggleModal('addModal')">닫기</button>
                <div id="add">
                    <button type="button" class="btn text-body large-button" data-toggle="modal" data-target="#modalplus"
                        style="font-size: 24px;">+ 새로운 플레이리스트 </button>
                </div>
            </form>
        </div>
    </div>
    <div class="notification" id="notification">
    	음악을 플레이리스트에 저장했습니다!
	</div>
	
	<div class="notification2" id="notification2">
    	새로운 플레이리스트를 생성했습니다!
	</div>
</div>

	
	<div class="modal fade" id="modalplus" tabindex="-1" role="dialog" data-target="#alert">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">플레이리스트 이름을 입력하세요</h3>
            </div>
            <div class="modal-body">
                <textarea id="modalContent" rows="1" cols="40" placeholder="제목은 20글자 이내로 입력하세요" maxlength="20" onkeydown="return event.keyCode !== 13;"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="close-btn"  onclick="checkAndSubmit()">확인</button>
                <button type="button" class="close-btn" data-dismiss="modal" >취소</button>
            </div>
        </div>
    </div>
</div>
	
	<div class="modal fade" id="modalAlert" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h3>플레이리스트 제목은 공백일 수 없습니다</h3>
            </div>
            <div class="modal-footer">
                <button type="button" class="close-btn" data-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>


<script>
	
	document.addEventListener('DOMContentLoaded', function () {
	    const pltitles = document.querySelectorAll('.pltitle'); // 여러 개의 .pltitle을 선택

	    const notification = document.getElementById('notification');
	    const tareset = document.querySelector('#modalplus .close-btn');
	    const mct = document.getElementById('modalContent');

	    // 각 .pltitle에 대해 이벤트 리스너 등록
	    pltitles.forEach(function (pltitle) {
	        pltitle.addEventListener('click', function (event) {
	            event.preventDefault();
	            console.log('pltitle clicked');
	            notify();
	        });
	    });

	    tareset.addEventListener('click', function () {
	        mct.value = '';
	    });
	});
	
	$(document).ready(function() {
	    $(".yes").hover(
	      function() {
	        $("#yeshover").css("display", "block");
	      },
	      function() {
	        $("#yeshover").css("display", "none");
	      }
	    );
	 });
	
	$(document).ready(function() {
	    $(".no").hover(
	      function() {
	        $("#nohover").css("display", "block");
	      },
	      function() {
	        $("#nohover").css("display", "none");
	      }
	    );
	 });

	
	function toggleModal(modalId) {
        $('#' + modalId).modal('toggle');
    }

    
	function openModalAlert() {
        $('#modalAlert').modal('show');
    }
    
    
    function checkAndSubmit() {
        event.preventDefault();
        const mcv = $('#modalContent').val().trim();
        const trackId = $('#trackIdInput').val(); // trackId를 읽어옴

        if (mcv === '') {
            openModalAlert();
        } else {
            // 사용자가 입력한 플레이리스트를 서버로 전송
            $.ajax({
                type: "POST",
                url: "/mute/addPlaylist", 
                data: { playlistName: mcv },
                success: function (res) {
                	const playlistId = res.playlistId; 
                	console.log('새로운 플레이리스트를 생성했습니다!');
                    $('#modalplus').modal('hide');
                    // 서버로부터 받은 응답으로 플레이리스트 목록 업데이트
                    addPlaylistToTable(mcv, playlistId);
                    
                 	// 모달 리로드
                    $('#addModal').find('.modal-body').load(location.href + ' #modaltable', function () {
                        $('#addModal').modal('show');
                    });
                    notify2();
                },
                error: function (err) {
                    alert('error'+err);
                    console.error('Error submitting playlist:', err);
                }
            });
        }
    }

 // 플레이리스트를 테이블에 동적으로 추가하는 함수
    function addPlaylistToTable(playlistName, playlistId) {
        var newRow = $('<tr>');

        var coverCell = $('<td>').addClass('td').append($('<img>').attr({
            'alt': 'gom_trot',
            'src': 'resources/images/gom_button.png',
            'height': '65',
            'width': '65'
        }));
        newRow.append(coverCell);

        var titleCell = $('<td>').addClass('td');
        /* var playlistLink = $('<a>').addClass('pltitle text-body').attr('href', '').text(playlistName); */

		var playlistLink = $('<a>').addClass('pltitle text-body').attr('href', '').data('playlist-id', playlistId).on('click', function(event) {
		    event.preventDefault();
		    var clickedPlaylistId = $(this).data('playlist-id');
		    addTrackToPlaylist(playlistId);
		    notify();
		}).text(playlistName);


        titleCell.append(playlistLink);
        newRow.append(titleCell);
        
        $('#modaltable').prepend(newRow);
        
    }

 
 	//알림
    function notify() {
        var notification = $('#notification');
        notification.css('display', 'block');

        setTimeout(function() {
            notification.css('display', 'none');
            $('#addModal').modal('hide');
        }, 1500);
    }

    function notify2() {
        var notification = $('#notification2');
        notification.css('display', 'block');

        setTimeout(function() {
            notification.css('display', 'none');
        }, 1500);
    }

  //--------------------------------------------------------------

    var trackId;
    var playlistId;
    
 	// 모달이 열릴 때 trackIdInput에 trackId를 저장
    $('#modalplus').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var trackId = button.data('track-id');
        $('#trackIdInput').val(trackId);
    });

    function openPlaylistModal(trackId, playlistId) {
        console.log("Track ID: " + trackId);
        window.trackId = trackId;
        window.playlistId = playlistId; // 플레이리스트 ID를 전역 변수에 저장
        toggleModal('addModal');
    }
    
    function toggleModal(modalId, trackId) {
        // 모달을 열 때 선택한 노래의 ID를 전달
        $('#' + modalId).data('track-id', trackId).modal('toggle');
    }

    function addTrackToPlaylist(playlistId) {
        console.log("a Track ID:" + window.trackId); // window.trackId로 수정
        console.log("a playlist ID: " + playlistId);
        
        // 선택한 노래의 ID와 플레이리스트의 ID를 서버로 전송
        $.ajax({
            type: "POST",
            url: "/mute/addTrackToPlaylist",
            data: { trackId: window.trackId, playlistId: playlistId },
            success: function (response) {
                console.log('음악을 플레이리스트에 추가했습니다!');
            },
            error: function (error) {
                alert("에러: Failed to add track to playlist - " + error.responseText);
            }
        });
    }
//--------------------------------------------------------------

let player;
let device_id;
let progress_ms;
let trackPositions = {};
	window.onSpotifyWebPlaybackSDKReady = () => {
	  const token = '${accessToken}'; // 사용자의 액세스 토큰을 여기에 설정
	  player = new Spotify.Player({
	    name: 'Web Playback SDK',
	    getOAuthToken: (cb) => { cb(token); }
	  });
	  console.dir(player)
	  // 로그인 및 초기화
	   
	player.on('player_state_changed', state => {
	  if (state) {
	    console.log('Current track:', state.track_window.current_track);
	    console.log('Current progress (ms):', state.position);
	    trackPositions[state.track_window.current_track.uri] = state.position;
	  }
	});
	  // Ready
	player.on('ready', data => {
	    console.log('Ready with Device ID', data.device_id);
	    device_id=data.device_id;
	    // Play a track using our new device ID
	    //play(data.device_id);
	});
	  
	  
	  player.connect();
	  console.log("===연결됨======================================")
	};
	
    const SPOTIFY_API_BASE = 'https://api.spotify.com/v1/me/player';
    const accessToken = "${accessToken}"; // Java 코드에서 받아온 accessToken
    // 이미지 토글 상태를 나타내는 객체
    var isPlayingMap = {};
 
    
 	// 이미지 클릭 이벤트에 플레이/일시정지 기능 추가
    function togglePlayPause(trackUri, imageElement) {
	    console.log("트랙에 대한 재생/일시정지 클릭: " + trackUri);
	    const isPlaying = imageElement.classList.contains('playing');
	
	    if (!isPlaying) {
	        // 재생을 계속하는 경우 현재 트랙의 진행 상황을 가져옵니다.
	        const currentState = player.getCurrentState();
	        currentState.then(state=>{
	            if(state){
	                console.log('!isPlaying:'+state.position)
	                trackPositions[trackUri] = state.position;
	            }
	        });
	    }
	
	    $.ajax({
	        url: SPOTIFY_API_BASE + (isPlaying ? '/pause' : '/play') + '?device_id=' + device_id,
	        type: 'PUT',
	        headers: {
	            'Authorization': 'Bearer ' + accessToken,
	            'Content-Type': 'application/json',
	        },
	        data: JSON.stringify({
	            uris: [trackUri],
	            device_ids: [device_id],
	            position_ms: trackPositions[trackUri] || 0,
	        }),
	        success: function () {
	            // 이미지 토글 호출
	            togglePlayPauseImage(trackUri, imageElement);
	        },
	        error: function (error) {
	            console.error('트랙 재생/일시정지 실패:', error);
	            console.error('API 호출 실패 상세 정보:', error.responseText);
	        },
	    });
	}
 	
 // 이미지 토글 함수
    function togglePlayPauseImage(trackUri, imageElement) {
        // 이미지 토글
        if (imageElement.classList.contains('playing')) {
            imageElement.src = '<c:url value="/resources/images/play_pl.png"/>';
            console.log("음악 일시정지");
        } else {
            imageElement.src = '<c:url value="/resources/images/pause_pl.png"/>';
            console.log("음악 재생");
        }
        // 토글 클래스 추가/제거
        imageElement.classList.toggle('playing');
    }
 
    let position_ms = null;

 	function playTest(uri){
 		const playlistUri = uri;
 		player.resume();
 		
 		/* player.play({
 		  uris: [playlistUri]
 		}); */
 	}

</script>

</body>
</html>