package org.nexasphere.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexasphere.model.entity.CoreTeamMemberEntity;
import org.nexasphere.service.crud.CoreTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CoreTeamController {

    private final CoreTeamService coreTeamService;

    @GetMapping("/api/content/core-team")
    public ResponseEntity<List<CoreTeamMemberEntity>> getPublicCoreTeam() {
        log.info("Fetching public core team members");
        return ResponseEntity.ok(coreTeamService.getAllMembers());
    }

    @GetMapping("/api/admin/core-team")
    public ResponseEntity<List<CoreTeamMemberEntity>> getAdminCoreTeam() {
        log.info("Fetching admin core team members");
        return ResponseEntity.ok(coreTeamService.getAllMembers());
    }

    @PostMapping("/api/admin/core-team")
    public ResponseEntity<CoreTeamMemberEntity> addMember(
            @Valid @RequestBody CoreTeamMemberEntity member,
            @AuthenticationPrincipal String adminEmail) {
        log.info("Adding new core team member: {}", member.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coreTeamService.addMember(member, adminEmail));
    }

    @DeleteMapping("/api/admin/core-team/{id}")
    public ResponseEntity<Map<String, Boolean>> removeMember(
            @PathVariable Long id,
            @AuthenticationPrincipal String adminEmail) {
        log.info("Removing core team member ID: {}", id);
        coreTeamService.removeMember(id, adminEmail);
        return ResponseEntity.ok(Map.of("ok", true));
    }
}
