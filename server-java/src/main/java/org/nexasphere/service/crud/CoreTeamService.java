package org.nexasphere.service.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexasphere.model.entity.CoreTeamMemberEntity;
import org.nexasphere.model.events.CoreTeamMemberAddedEvent;
import org.nexasphere.model.events.CoreTeamMemberRemovedEvent;
import org.nexasphere.repository.CoreTeamRepository;
import org.nexasphere.service.AdminEventPublisher;
import org.nexasphere.util.Sanitizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreTeamService {
    private final CoreTeamRepository repository;
    private final AdminEventPublisher eventPublisher;
    private final Sanitizer sanitizer;

    public List<CoreTeamMemberEntity> getAllMembers() {
        return repository.findAllByOrderByCreatedAtAsc();
    }

    public CoreTeamMemberEntity addMember(CoreTeamMemberEntity member, String adminEmail) {
        sanitizeInput(member);
        validateWhatsappNumber(member.getWhatsapp());
        CoreTeamMemberEntity saved = repository.save(member);
        eventPublisher.publish(new CoreTeamMemberAddedEvent(adminEmail, saved));
        return saved;
    }

    public void removeMember(Long id, String adminEmail) {
        CoreTeamMemberEntity member = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        repository.delete(member);
        eventPublisher.publish(new CoreTeamMemberRemovedEvent(adminEmail, member));
    }

    private void sanitizeInput(CoreTeamMemberEntity member) {
        member.setName(sanitizer.sanitizeHtml(member.getName()));
        member.setRole(sanitizer.sanitizeHtml(member.getRole()));
        member.setYear(sanitizer.sanitizeHtml(member.getYear()));
        member.setBranch(sanitizer.sanitizeHtml(member.getBranch()));
        member.setSection(sanitizer.sanitizeHtml(member.getSection()).toUpperCase());
        member.setWhatsapp(member.getWhatsapp() == null ? null : member.getWhatsapp().replaceAll("\\D", ""));
        member.setLinkedin(sanitizer.sanitizeHtml(member.getLinkedin()));
        member.setInstagram(sanitizer.sanitizeHtml(member.getInstagram()));
        member.setPhotoUrl(sanitizer.sanitizeHtml(member.getPhotoUrl()));
    }

    private void validateWhatsappNumber(String whatsapp) {
        if (whatsapp == null || !whatsapp.matches("\\d{10}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WhatsApp number must be exactly 10 digits");
        }
    }
}
