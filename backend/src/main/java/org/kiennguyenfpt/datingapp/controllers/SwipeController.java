package org.kiennguyenfpt.datingapp.controllers;

import org.kiennguyenfpt.datingapp.dtos.requests.SwipeRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.SwipeResponse;
import org.kiennguyenfpt.datingapp.exceptions.AlreadyMatchedException;
import org.kiennguyenfpt.datingapp.responses.CommonResponse;
import org.kiennguyenfpt.datingapp.services.SwipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/swipes")
public class SwipeController {
    private final SwipeService swipeService;

    public SwipeController(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    @PostMapping("/swipe")
    public ResponseEntity<CommonResponse<SwipeResponse>> swipe(@RequestBody @Valid SwipeRequest swipeRequest) {
        CommonResponse<SwipeResponse> response = new CommonResponse<>();
        try {
            if (swipeRequest.getIsLike() == null) {
                throw new IllegalArgumentException("Like status cannot be null");
            }
            SwipeResponse swipeResponse = swipeService.swipe(swipeRequest.getUserId(), swipeRequest.getTargetUserId(), swipeRequest.getIsLike());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Swipe action completed successfully.");
            response.setData(swipeResponse);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (AlreadyMatchedException e) { // Bắt ngoại lệ AlreadyMatchedException riêng
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error performing swipe action: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        CommonResponse<String> response = new CommonResponse<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AlreadyMatchedException.class)
    public ResponseEntity<CommonResponse<String>> handleAlreadyMatchedException(AlreadyMatchedException e) {
        CommonResponse<String> response = new CommonResponse<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}