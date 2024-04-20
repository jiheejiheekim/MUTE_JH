<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/login.css">
<title>MU:TE</title>
</head>

<script>


function loginToSpotify() {
	//뮤테 client_id 12/26 변경
	/* var client_id = 'b8f5c70e3a244517add3cbd34de84531'; */    
   
	//최희정 client_id
    var client_id = 'c82612eab8154313a45a1233cb2e7374';
    var redirect_uri = 'http://localhost:9089/mute/main';

    var scope = 'user-read-private user-read-email user-library-read user-read-currently-playing playlist-read-private playlist-modify-private user-modify-playback-state ugc-image-upload user-read-playback-state user-modify-playback-state streaming playlist-modify-public playlist-read-collaborative user-library-modify app-remote-control';


    var spotifyAuthURL = 'https://accounts.spotify.com/authorize?' +
        'client_id=' + client_id +
        '&redirect_uri=' + redirect_uri +
        '&response_type=code' +
        '&scope=' + encodeURIComponent(scope);

    // Redirect the user to the Spotify authentication page
    window.location.href = spotifyAuthURL;

}

</script>

<body>

	<nav>
		<div>
			<img id="logo" alt="logo" src="resources/images/mutelogo.png" height="385" width="385"><!--로고 -->
			<h1>간편로그인</h1>
			<br><br><br>

			<button class="button-68" role="button" onclick="loginToSpotify()">
				<img id="spotifyLogo" src="resources/images/spotifyLogo.png" alt="spotifyLogo">
				<span>Sign in with Spotify</span>
			</button>


			
		</div>
		
		
	
	</nav>
</body>
</html>