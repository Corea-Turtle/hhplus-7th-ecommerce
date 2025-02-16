package kr.hhplus.be.server.interfaces.api.statics.controller;

import kr.hhplus.be.server.domain.statics.StaticsService;
import kr.hhplus.be.server.interfaces.api.statics.dto.response.StaticsTopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statics")
public class StaticsController {

    private final StaticsService staticsService;

    @GetMapping("/top5")
    public ResponseEntity<List<StaticsTopResponse>> getTopProduct(@RequestParam int day, @RequestParam int limit){

        List<StaticsTopResponse> response = staticsService.getTop5DayAndLimit(day, limit);

        return ResponseEntity.ok()
                .body(response);
    }

}
