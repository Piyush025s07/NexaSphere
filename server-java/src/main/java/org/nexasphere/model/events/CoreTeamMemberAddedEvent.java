package org.nexasphere.model.events;

import org.nexasphere.model.entity.CoreTeamMemberEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CoreTeamMemberAddedEvent extends AdminEvent {
    private final CoreTeamMemberEntity member;

    public CoreTeamMemberAddedEvent(String adminEmail, CoreTeamMemberEntity member) {
        super(UUID.randomUUID().toString(), adminEmail, LocalDateTime.now(), "CORE_TEAM_MEMBER_ADDED");
        this.member = member;
        setMetadata(Map.of(
                "memberId", member.getId(),
                "memberName", member.getName(),
                "role", member.getRole(),
                "year", member.getYear()
        ));
    }

    public CoreTeamMemberEntity getMember() {
        return member;
    }
}
