package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.team.JoinRequestResponse;
import ut.edu.teammatching.dto.team.LecturerJoinRequestDTO;
import ut.edu.teammatching.models.LecturerJoinRequest;
import ut.edu.teammatching.services.LecturerJoinRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/lecturer-join-requests")
@RequiredArgsConstructor
public class LecturerJoinRequestController {

    private final LecturerJoinRequestService lecturerJoinRequestService;

    @GetMapping("/pending/{lecturerId}")
    public ResponseEntity<List<LecturerJoinRequestDTO>> getPendingJoinRequestsForLecturer(@PathVariable Long lecturerId) {
        List<LecturerJoinRequestDTO> pendingRequests = lecturerJoinRequestService.getPendingRequestsForLecturer(lecturerId);
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping
    public ResponseEntity<LecturerJoinRequestDTO> sendJoinRequest(
            @RequestParam Long teamId,
            @RequestParam Long lecturerId,
            @RequestParam Long requesterId) {

        LecturerJoinRequest request = lecturerJoinRequestService.sendLecturerJoinRequest(
                teamId, lecturerId, requesterId
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
