package ut.edu.teammatching.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import ut.edu.teammatching.dto.team.JoinRequestResponse;
import ut.edu.teammatching.dto.team.LecturerJoinRequestDTO;
import ut.edu.teammatching.models.LecturerJoinRequest;
import ut.edu.teammatching.services.LecturerJoinRequestService;

import static ut.edu.teammatching.services.UserService.convertToDTO;

@RestController
@RequestMapping("/api/lecturer-join-requests")
@RequiredArgsConstructor
public class LecturerJoinRequestController {

    private final LecturerJoinRequestService lecturerJoinRequestService;

    @PostMapping
    public ResponseEntity<LecturerJoinRequestDTO> sendJoinRequest(
            @RequestParam Long teamId,
            @RequestParam Long lecturerId,
            @RequestParam Long requesterId,
            @RequestParam(required = false) String message) {

        LecturerJoinRequest request = lecturerJoinRequestService.sendLecturerJoinRequest(
                teamId, lecturerId, requesterId, message
        );

        return ResponseEntity.ok(LecturerJoinRequestDTO.fromEntity(request));
    }

    @PostMapping("/respond")
    public ResponseEntity<JoinRequestResponse> respondToRequest(
            @RequestParam Long requestId,
            @RequestParam Long lecturerId,
            @RequestParam Long leaderId,
            @RequestParam boolean accept) {

        try {
            String result = lecturerJoinRequestService.respondToLecturerJoinRequest(requestId, lecturerId, leaderId, accept);
            return ResponseEntity.ok(new JoinRequestResponse(true, result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new JoinRequestResponse(false, e.getMessage()));
        }
    }
}
