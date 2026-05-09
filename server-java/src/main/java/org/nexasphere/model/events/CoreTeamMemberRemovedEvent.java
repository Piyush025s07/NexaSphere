package org.nexasphere.model.events;

import org.nexasphere.model.entity.CoreTeamMemberEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CoreTeamMemberRemovedEvent extends AdminEvent {
    private final CoreTeamMemberEntity member;

    public CoreTeamMemberRemovedEvent(String adminEmail, CoreTeamMemberEntity member) {
        super(UUID.randomUUID().toString(), adminEmail, LocalDateTime.now(), "CORE_TEAM_MEMBER_REMOVED");
        this.member = member;
        setMetadata(Map.of(
                "memberId", member.getId(),
                "memberName", member.getName(),
                "role", member.getRole()
        ));
    }

    public CoreTeamMemberEntity getMember() {
        return member;
    }
}
