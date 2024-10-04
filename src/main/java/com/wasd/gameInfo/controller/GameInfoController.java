package com.wasd.gameInfo.controller;

import com.wasd.gameInfo.dto.GameInfoDto;
import com.wasd.gameInfo.dto.UserGameInfoDto;
import com.wasd.gameInfo.service.GameInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gameInfo")
@RequiredArgsConstructor
public class GameInfoController {
    private final GameInfoService gameInfoService;

    // 임시 세션 부여
    @GetMapping("/session")
    public void getSession(HttpSession session){
        session.setAttribute("userId", "test");
    }

    /**
     * 전체 게임 목록 반환
     * @return 게임아이디(영문) 게임명(한글) 목록 반환
     */
    @GetMapping
    public ResponseEntity<List<GameInfoDto>> findGameInfo(){
        return ResponseEntity.ok(gameInfoService.findGameInfo());

    }

    /**
     * 사용자가 선택한 게임의 속성값들 반환
     * @param gameId 게임이름
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameInfoDto> findGameInfo(@PathVariable String gameId){
        return ResponseEntity.ok(gameInfoService.findGameInfo(gameId));
    }

    /**
     * 유저의 게임 목록들 반환
     * @param session 유저아이디 담긴 세션
     * @return List<GameInfoDto> 게임목록
     */
    @GetMapping("/user")
    public ResponseEntity<List<GameInfoDto>> findUserGameInfo(HttpSession session){
        return ResponseEntity.ok(gameInfoService.findUserGameInfo(session));
    }

    /**
     * 유저아이디, 게임아이디에 해당하는 게임 정보들 리턴
     * @param gameId 게임아이디
     * @param session 유저아이디 담긴 세션
     * @return GameInfo 해당 게임정보
     */
    @GetMapping("/user/game")
    public ResponseEntity<GameInfoDto> findUserGameInfo(@RequestParam String gameId, HttpSession session){
        return ResponseEntity.ok(gameInfoService.findUserGameInfo(gameId, session));
    }

    /**
     * 유저 게임 정보 insert
     * @param gameInfoDtoList 게임정보 목록
     * @param session 유저아이디 담긴 세션
     * @return UserGameInfoDto
     */
    @PostMapping("/user/game")
    public ResponseEntity<UserGameInfoDto> insertUserGameInfo(@RequestBody List<GameInfoDto> gameInfoDtoList, HttpSession session){
        // 이 메소드는 가입시에만 호출되므로 drop 후 insert 로 구현함
        return ResponseEntity.ok(gameInfoService.insertUserGameInfo(gameInfoDtoList, session));
    }

    // 유저가 선택한 게임 정보 update

    /**
     * 유저 선택 게임 정보 업데이트
     * @param gameInfoDto 수정된 게임 정보
     * @param session 유저 아이디 담긴 세션
     * @return UserGameInfoDto
     */
    @PutMapping("/user/game")
    public ResponseEntity<UserGameInfoDto> updateUserGameInfo(@RequestBody GameInfoDto gameInfoDto, HttpSession session){
        return ResponseEntity.ok(gameInfoService.updateUserGameInfo(gameInfoDto, session));
    }

    /**
     *  선택 게임 삭제
     * @param gameId 게임아이디
     * @param session 유저 아이디 담긴 세션
     * @return UserGameInfoDto 삭제 후 남은 현재 정보
     */
    @DeleteMapping("/user/game")
    public ResponseEntity<UserGameInfoDto> deleteUserGameInfo(@RequestParam String gameId, HttpSession session){
        return ResponseEntity.ok(gameInfoService.deleteUserGameInfo(gameId, session));
    }
}
