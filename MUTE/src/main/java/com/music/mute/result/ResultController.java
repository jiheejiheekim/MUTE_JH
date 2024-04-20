package com.music.mute.result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.mute.api.SpotifyPlaybackService;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.model_objects.miscellaneous.Device;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.player.GetInformationAboutUsersCurrentPlaybackRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersAvailableDevicesRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@Controller

public class ResultController {
	
	@Autowired
    private SpotifyApi spotifyApi;

    @Autowired
    private SpotifyPlaybackService playbackService;
    
    @Autowired
    private ResultService service;
    
    //private static final Logger logger = LoggerFactory.getLogger(AllResultController.class);

    @GetMapping("/result_folk")
    public String resultBallad(Model model, HttpSession session) {
        return service.getResultPage(model, session, "folk");
    }

    @GetMapping("/result_classical")
    public String resultClassic(Model model, HttpSession session) {
        return service.getResultPage(model, session, "classical");
    }

    @GetMapping("/result_dance")
    public String resultDance(Model model, HttpSession session) {
        return service.getResultPage(model, session, "dance");
    }
	
	@GetMapping("/result_techno") 
	public String resultTechno(Model model, HttpSession session) { 
		return service.getResultPage(model, session, "techno"); 
	}

    @GetMapping("/result_disco")
    public String resultDisco(Model model, HttpSession session) {
        return service.getResultPage(model, session, "disco");
    }

    @GetMapping("/result_hip-hop")
    public String resultHipHop(Model model, HttpSession session) {
        return service.getResultPage(model, session, "hip-hop");
    }

    @GetMapping("/result_indie")
    public String resultIndie(Model model, HttpSession session) {
        return service.getResultPage(model, session, "indie");
    }

    @GetMapping("/result_jazz")
    public String resultJazz(Model model, HttpSession session) {
        return service.getResultPage(model, session, "jazz");
    }

    @GetMapping("/result_r-n-b")
    public String resultRnb(Model model, HttpSession session) {
        return service.getResultPage(model, session, "r-n-b");
    }

    @GetMapping("/result_rock")
    public String resultRock(Model model, HttpSession session) {
        return service.getResultPage(model, session, "rock");
    }

    
    @GetMapping("/play/{trackId}")
    @ResponseBody
	public Map<String, String> playTrack(@PathVariable String trackId, HttpSession session) {
    	Map<String, String> map=new HashMap<>();
		try {
			String accessToken = (String) session.getAttribute("accessToken");
			if (accessToken != null) {
				// 현재 사용자의 활성 디바이스 ID 가져오기
				GetInformationAboutUsersCurrentPlaybackRequest playbackRequest = spotifyApi
						.getInformationAboutUsersCurrentPlayback().build();
				CompletableFuture<CurrentlyPlayingContext> playbackFuture = playbackRequest.executeAsync();
				CurrentlyPlayingContext playbackContext = playbackFuture.join();
				Device currentDevice = playbackContext.getDevice();
				String deviceId = (currentDevice != null) ? currentDevice.getId() : null;
				session.setAttribute("deviceId", deviceId);
				
				System.out.println("device: " + deviceId);
				// 노래 재생 시 디바이스 ID 사용
				if (deviceId != null) {
					playbackService.startOrResumePlayback(accessToken, "spotify:track:" + trackId, deviceId);
					System.out.println("Play track: " + trackId);
					map.put("trackId", trackId);
				} else {
					System.out.println("No active device found.");
					map.put("trackId", "No active device found.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", e.getMessage());
		}
		return map;
	}
    
    @PostMapping(value="/addPlaylist", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Map<String, String>> addPlaylist(Model model, HttpSession session,
            @RequestParam String playlistName) {
        String accessToken = (String) session.getAttribute("accessToken");
        Map<String, String> response = new HashMap<>();

        if (accessToken != null) {
            try {
                spotifyApi.setAccessToken(accessToken);

                // 현재 사용자의 프로필 정보 가져오기
                final GetCurrentUsersProfileRequest profileRequest = spotifyApi.getCurrentUsersProfile().build();
                final CompletableFuture<User> privateUserFuture = profileRequest.executeAsync();
                User privateUser = privateUserFuture.join();
                String userId = privateUser.getId();

                // 새로운 플레이리스트 생성
                final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistName)
                        .public_(false)
                        .build();
                final CompletableFuture<Playlist> playlistFuture = createPlaylistRequest.executeAsync();
                Playlist newPlaylist = playlistFuture.join();

                // 생성된 플레이리스트 정보를 응답에 추가
                response.put("playlistId", newPlaylist.getId());
                response.put("playlistName", newPlaylist.getName());
                System.out.println(newPlaylist.getId()+" <- playlistId");
                // addPlaylist 메서드에서 플레이리스트 생성 후
                String playlistId = newPlaylist.getId();
               // model.addAttribute("playlistId", playlistId);


                return new ResponseEntity<>(response, HttpStatus.OK);
                //return new ResponseEntity<>(Map.of("playlistId", newPlaylist.getId()), HttpStatus.OK);

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    
    @PostMapping("/addTrackToPlaylist")
	@ResponseBody
	public ResponseEntity<String> addTrackToPlaylist(@RequestParam String trackId, @RequestParam String playlistId,
			HttpSession session) {
		String accessToken = (String) session.getAttribute("accessToken");

		if (accessToken != null) {
			try {
				// 트랙을 플레이리스트에 추가하는 API 요청
				String[] uris = { "spotify:track:" + trackId }; // 트랙 URI를 String 배열로 전달
				spotifyApi.addItemsToPlaylist(playlistId, uris).build().execute();

				return new ResponseEntity<>("Track added to playlist successfully", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Failed to add track to playlist", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
		}
	}
    

}

