package com.music.mute.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.follow.UnfollowPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.ChangePlaylistsDetailsRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.playlists.RemoveItemsFromPlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@CrossOrigin(origins = "http://localhost:9089")
@Controller
public class DeleteAPIPlaylistController {

	@Autowired
	private SpotifyApi spotifyApi;

	@GetMapping("/delApiTest")
	public String getUserPlaylists(Model model, HttpSession session) {
		// 사용자의 Access Token을 세션에서 가져옴
		String accessToken = (String) session.getAttribute("accessToken");

		if (accessToken != null) {
			try {
				spotifyApi.setAccessToken(accessToken);

				final GetListOfCurrentUsersPlaylistsRequest playlistsRequest = spotifyApi
						.getListOfCurrentUsersPlaylists().build();

				final CompletableFuture<Paging<PlaylistSimplified>> playlistsFuture = playlistsRequest.executeAsync();

				// 실제 코드에서는 결과를 처리해야 합니다.
				PlaylistSimplified[] playlists = playlistsFuture.join().getItems();
				System.out.println(playlists[0]);
				model.addAttribute("playlists", playlists);
			} catch (Exception e) {
				// 예외 처리
				e.printStackTrace();
			}
		} else {
			// Access Token이 없는 경우, 로그인 페이지로 리다이렉트 또는 에러 처리
			return "redirect:/login"; // 예시: 로그인 페이지로 리다이렉트
		}

		return "delApiTest";
	}

	public String getPlaylistsItems_Sync(Model m, String playlistId) {
		try {
			final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistId).build();
			final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
			System.out.println("Total: " + playlistTrackPaging.getTotal());

			if (playlistTrackPaging.getTotal() == 0) {
				return null;
			}

			System.out.println(
					"Track's first artist: " + ((Track) playlistTrackPaging.getItems()[0].getTrack()).getArtists()[0]);
			System.out.println(
					"Track's first name : " + ((Track) playlistTrackPaging.getItems()[0].getTrack()).getName());
			m.addAttribute("trackTotal", playlistTrackPaging.getTotal());
			PlaylistTrack[] arr = playlistTrackPaging.getItems();
			String trackInfo = "";
			String artistInfo = "";
			String albumInfo = "";

			List<String> trackIdList = new ArrayList<>();//트랙삭제 구현 중 추가

			for (PlaylistTrack pt : arr) {
				Track tr = (Track) pt.getTrack(); // 트랙 정보
				ArtistSimplified[] artists = ((Track) pt.getTrack()).getArtists();

				String trackId = tr.getId();
				// trackId를 어딘가에 저장, 트랙삭제 구현 중 추가
				trackIdList.add(trackId);

				trackInfo += tr.getName() + "#";
				if (artists.length > 0) {
					// 가수가 한 명 이상인 경우 쉼표로 구분
					artistInfo += artists[0].getName();
				}

				for (int i = 1; i < artists.length; i++) {
					artistInfo += ", " + artists[i].getName();
				}

				artistInfo += "-";

				albumInfo += tr.getAlbum().getName() + ",";
				albumInfo += tr.getAlbum().getImages()[0].getUrl() + "#";
			}

			// playlistTrackPaging.getItems()[0].getTrack().getName()
			// 컨트롤러의 일부분
			String[] trackInfoArray = trackInfo.split("#");
			String[] artistInfoArray = artistInfo.split("-");
			String[] albumInfoArray = albumInfo.split("#");

			m.addAttribute("trackInfoArray", trackInfoArray);
			m.addAttribute("artistInfoArray", artistInfoArray);
			m.addAttribute("albumInfoArray", albumInfoArray);
			m.addAttribute("trackIdList", trackIdList);//트랙삭제 구현 중 추가

			return ((Track) playlistTrackPaging.getItems()[0].getTrack()).getName();
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public Track[] getTrack(String trackname) {
		// Build the request to search for tracks
		// 이 부분 수정해야됩니다
		SearchTracksRequest request = spotifyApi.searchTracks(trackname).build();

		try {
			// Execute the request and get the search results
			Track[] tracks = request.execute().getItems();

			if (tracks.length > 0) {
				// Get the ID of the first track in the search results
				String trackId = tracks[0].getId();
				System.out.println("첫번째Track ID: " + trackId);

			} else {
				System.out.println("No tracks found.");
			}
			return tracks;
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.err.println("Error: " + e.getMessage());
			return null;
		}

	}

	public void getTrack_Sync(String id) {
		// 앨범 트랙을 가져오기 위한 요청 작성
		GetAlbumsTracksRequest request = spotifyApi.getAlbumsTracks(id).limit(10).offset(0).build();

		try {
			// 요청 실행 및 앨범 트랙 가져오기
			Paging<TrackSimplified> trackSimplifiedPaging = request.execute();

			System.out.println("총 트랙 수: " + trackSimplifiedPaging.getTotal());

			// 플레이리스트 트랙을 반복하고 트랙 ID 출력
			for (TrackSimplified playlistTrack : trackSimplifiedPaging.getItems()) {
				String trackId = playlistTrack.getId();
				String trackName = playlistTrack.getName(); // 트랙의 이름 로깅
				System.out.println("트랙 ID: " + trackId + ", 트랙 이름: " + trackName);
			}
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("오류: " + e.getMessage());
		}
	}

	/* @PostMapping("/addPlaylist") */
	public String addPlaylist(Model model, HttpSession session, @RequestParam String playlistName) {
		String accessToken = (String) session.getAttribute("accessToken");

		if (accessToken != null) {
			try {
				spotifyApi.setAccessToken(accessToken);

				final GetCurrentUsersProfileRequest profileRequest = spotifyApi.getCurrentUsersProfile().build();
				final CompletableFuture<User> privateUserFuture = profileRequest.executeAsync();
				User privateUser = privateUserFuture.join();
				String userId = privateUser.getId();

				final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistName)
						.public_(false).build();

				final CompletableFuture<Playlist> playlistFuture = createPlaylistRequest.executeAsync();
				Playlist newPlaylist = playlistFuture.join();

				model.addAttribute("message", "Playlist added successfully: " + newPlaylist.getName());
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("error", "Error adding playlist");
			}
		} else {
			return "redirect:/login";
		}

		return "redirect:/delApiTest";
	}

	/*
	 * @GetMapping("/playlisttracks") public String getPlaylisttracks(Model model,
	 * HttpSession session, @RequestParam("playlistId") String playlistId) {
	 * System.out.println(">>>> Playlist ID: " + playlistId); String accessToken =
	 * (String) session.getAttribute("accessToken"); String trackName =
	 * getPlaylistsItems_Sync(model, playlistId); if (accessToken != null) { try {
	 * spotifyApi.setAccessToken(accessToken);
	 * 
	 * Playlist clickedPlaylist = getClickedPlaylistInfo(playlistId); // 메서드 이름 및
	 * 구현은 적절하게 변경되어야 합니다.
	 * 
	 * // 클릭한 플레이리스트 정보를 Model에 추가 model.addAttribute("playlist", clickedPlaylist);
	 * 
	 * if (trackName == null) return "redirect:/emptyPage"; Track[] tracks =
	 * getTrack(trackName); final GetAlbumsTracksRequest tracksRequest =
	 * spotifyApi.getAlbumsTracks(tracks[0].getAlbum().getId()).limit(10).build();
	 * 
	 * final CompletableFuture<Paging<TrackSimplified>> tracksFuture =
	 * tracksRequest.executeAsync();
	 * 
	 * tracksFuture.join().getItems();
	 * 
	 * // model.addAttribute("tracks", tracks); } catch (Exception e) {
	 * e.printStackTrace(); model.addAttribute("error",
	 * "Error fetching playlist tracks"); } } else { return "redirect:/login"; }
	 * 
	 * return "/playlisttracks"; }
	 */

	private Playlist getClickedPlaylistInfo(String playlistId) {
		try {
			// GetPlaylistRequest를 사용하여 특정 플레이리스트의 정보를 가져옴
			final GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId).build();
			final Playlist clickedPlaylist = getPlaylistRequest.execute();
			System.out.println("clickedPlaylist: " + clickedPlaylist);
			System.out.println("playlistname:" + clickedPlaylist.getName());

			// 가져온 플레이리스트 정보를 반환
			return clickedPlaylist;
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			// 예외 처리
			e.printStackTrace();
			return null;
		}
	}

	/* @PostMapping("/updatePlaylist") */
	public String updatePlaylist(Model model, HttpSession session, @RequestParam String playlistId,
			@RequestParam String editPlaylistName) {
		String accessToken = (String) session.getAttribute("accessToken");

		if (accessToken != null) {
			try {
				spotifyApi.setAccessToken(accessToken);

				// Spotify API를 사용하여 플레이리스트의 이름을 변경
				final ChangePlaylistsDetailsRequest changePlaylistDetailsRequest = spotifyApi
						.changePlaylistsDetails(playlistId).name(editPlaylistName).build();

				changePlaylistDetailsRequest.execute();

				// 수정 후, 사용자에게 적절한 메시지를 전달
				model.addAttribute("message", "Playlist updated successfully");
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("error", "Error updating playlist");
			}
		} else {
			return "redirect:/login";
		}

		return "redirect:/apiTest";
	}

	// 플리삭제------------------------------------------------

	@DeleteMapping("/deletePlaylist")
	public ResponseEntity<String> deletePlaylist(@RequestParam String playlistId, HttpSession session) {
		String accessToken = (String) session.getAttribute("accessToken");

		if (accessToken != null) {
			try {
				spotifyApi.setAccessToken(accessToken);

				// 플레이리스트 언팔로우 API 요청
				final UnfollowPlaylistRequest unfollowPlaylistRequest = spotifyApi.unfollowPlaylist(playlistId).build();
				unfollowPlaylistRequest.execute();

				// 삭제 후, 적절한 응답 반환
				return ResponseEntity.ok("Playlist delete successfully");
			} catch (Exception e) {
				e.printStackTrace();
				// 에러가 발생하면 500 Internal Server Error 반환
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting playlist");
			}
		} else {
			// 사용자가 인증되지 않은 경우 401 Unauthorized 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
		}
	}

	// 트랙삭제------------------------------------------------

	@GetMapping("/delplaylisttracks")
	public String showDeletePlaylistTracks(Model model, HttpSession session,
			@RequestParam("playlistId") String playlistId) {
		System.out.println(">>>> Playlist ID: " + playlistId);
		String accessToken = (String) session.getAttribute("accessToken");
		String trackName = getPlaylistsItems_Sync(model, playlistId);
		if (accessToken != null) {
			try {
				spotifyApi.setAccessToken(accessToken);

				Playlist clickedPlaylist = getClickedPlaylistInfo(playlistId); // 메서드 이름 및 구현은 적절하게 변경되어야 합니다.

				// 클릭한 플레이리스트 정보를 Model에 추가
				model.addAttribute("playlist", clickedPlaylist);

				if (trackName == null)
					return "redirect:/emptyPage";
				Track[] tracks = getTrack(trackName);
				final GetAlbumsTracksRequest tracksRequest = spotifyApi.getAlbumsTracks(tracks[0].getAlbum().getId())
						.limit(10).build();

				final CompletableFuture<Paging<TrackSimplified>> tracksFuture = tracksRequest.executeAsync();

				tracksFuture.join().getItems();

			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("error", "Error fetching playlist tracks");
			}
		} else {
			return "redirect:/login";
		}

		return "/delplaylisttracks";
	}

	
	@DeleteMapping("/delTrack")
    public ResponseEntity<String> deleteTrack(@RequestParam String playlistId, @RequestParam String trackId, HttpSession session) throws ParseException {
		
		String accessToken = (String) session.getAttribute("accessToken");
		
        // 트랙을 삭제하기 위한 Spotify API 요청 준비
        JsonArray tracks = JsonParser.parseString("[{\"uri\":\"spotify:track:" + trackId + "\"}]").getAsJsonArray();
        RemoveItemsFromPlaylistRequest removeItemsRequest = spotifyApi.removeItemsFromPlaylist(playlistId, tracks).build();

        try {
            // Spotify API를 통해 트랙 삭제 실행
            SnapshotResult snapshotResult = removeItemsRequest.execute();

            // 트랙 삭제 후, 적절한 응답 반환
            return ResponseEntity.ok("Track deleted successfully. Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();

            // 에러가 발생하면 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting track: " + e.getMessage());
        }
    }
	
	

}