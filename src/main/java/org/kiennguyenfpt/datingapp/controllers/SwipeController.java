package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.SwipeRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.SwipeResponse;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.SwipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/swipes")
public class SwipeController {
    /*
    private final SwipeService swipeService;

    public SwipeController(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    @PostMapping("/swipe")
    public ResponseEntity<CommonResponse<SwipeResponse>> swipe(@RequestBody SwipeRequest swipeRequest) {
        CommonResponse<SwipeResponse> response = new CommonResponse<>();
        try {
            SwipeResponse swipeResponse = swipeService.swipe(swipeRequest.getUserId(), swipeRequest.getTargetUserId(), swipeRequest.isLike());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Swipe action completed successfully.");
            response.setData(swipeResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error performing swipe action: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

     */
}
