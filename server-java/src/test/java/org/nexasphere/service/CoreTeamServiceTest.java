package org.nexasphere.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nexasphere.model.entity.CoreTeamMemberEntity;
import org.nexasphere.model.events.AdminEvent;
import org.nexasphere.model.events.CoreTeamMemberAddedEvent;
import org.nexasphere.model.events.CoreTeamMemberRemovedEvent;
import org.nexasphere.repository.CoreTeamRepository;
import org.nexasphere.service.crud.CoreTeamService;
import org.nexasphere.util.Sanitizer;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoreTeamServiceTest {

    @Mock CoreTeamRepository repository;
    @Mock AdminEventPublisher eventPublisher;
    @Mock Sanitizer sanitizer;
    @InjectMocks CoreTeamService coreTeamService;

    @Test
    void getAllMembers_returnsSortedList() {
        CoreTeamMemberEntity first = sampleMember();
        first.setId(1L);
        CoreTeamMemberEntity second = sampleMember();
        second.setId(2L);

        when(repository.findAllByOrderByCreatedAtAsc()).thenReturn(List.of(first, second));

        List<CoreTeamMemberEntity> result = coreTeamService.getAllMembers();

        assertThat(result).containsExactly(first, second);
    }

    @Test
    void addMember_sanitizesAndPublishesEvent() {
        CoreTeamMemberEntity input = sampleMember();
        input.setWhatsapp("98765-43210");
        CoreTeamMemberEntity saved = sampleMember();
        saved.setId(11L);
        saved.setWhatsapp("9876543210");

        when(sanitizer.sanitizeHtml(anyString())).thenAnswer(inv -> inv.getArgument(0));
        when(repository.save(any(CoreTeamMemberEntity.class))).thenReturn(saved);

        CoreTeamMemberEntity result = coreTeamService.addMember(input, "admin@test.com");

        assertThat(result.getId()).isEqualTo(11L);
        assertThat(input.getWhatsapp()).isEqualTo("9876543210");
        assertThat(input.getSection()).isEqualTo("A");

        ArgumentCaptor<AdminEvent> eventCaptor = ArgumentCaptor.forClass(AdminEvent.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        assertThat(eventCaptor.getValue()).isInstanceOf(CoreTeamMemberAddedEvent.class);
        assertThat(eventCaptor.getValue().getMetadata()).containsEntry("memberId", 11L);
    }

    @Test
    void addMember_sanitizesHtmlInTextFields() {
        CoreTeamMemberEntity input = sampleMember();
        input.setName("<b>Rahul</b>");
        CoreTeamMemberEntity saved = sampleMember();
        saved.setId(12L);

        when(sanitizer.sanitizeHtml(anyString())).thenAnswer(inv -> inv.getArgument(0));
        when(sanitizer.sanitizeHtml("<b>Rahul</b>")).thenReturn("&lt;b&gt;Rahul&lt;/b&gt;");
        when(repository.save(any(CoreTeamMemberEntity.class))).thenReturn(saved);

        coreTeamService.addMember(input, "admin@test.com");

        assertThat(input.getName()).isEqualTo("&lt;b&gt;Rahul&lt;/b&gt;");
    }

    @Test
    void addMember_invalidSanitizedWhatsapp_throwsBadRequest() {
        CoreTeamMemberEntity input = sampleMember();
        input.setWhatsapp("12-34");
        when(sanitizer.sanitizeHtml(anyString())).thenAnswer(inv -> inv.getArgument(0));

        assertThatThrownBy(() -> coreTeamService.addMember(input, "admin@test.com"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("400");

        verify(repository, never()).save(any());
    }

    @Test
    void removeMember_notFound_throws404() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> coreTeamService.removeMember(99L, "admin@test.com"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }

    @Test
    void removeMember_existingMember_deletesAndPublishesEvent() {
        CoreTeamMemberEntity existing = sampleMember();
        existing.setId(5L);
        when(repository.findById(5L)).thenReturn(Optional.of(existing));

        coreTeamService.removeMember(5L, "admin@test.com");

        verify(repository).delete(existing);
        ArgumentCaptor<AdminEvent> eventCaptor = ArgumentCaptor.forClass(AdminEvent.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        assertThat(eventCaptor.getValue()).isInstanceOf(CoreTeamMemberRemovedEvent.class);
        assertThat(eventCaptor.getValue().getMetadata()).containsEntry("memberId", 5L);
    }

    private CoreTeamMemberEntity sampleMember() {
        CoreTeamMemberEntity member = new CoreTeamMemberEntity();
        member.setName("Rahul Sharma");
        member.setRole("Core Team Member");
        member.setYear("2nd Year");
        member.setBranch("CSE (AI & ML)");
        member.setSection("a");
        member.setEmail("rahul.sharma@example.com");
        member.setWhatsapp("9876543210");
        member.setLinkedin("https://linkedin.com/in/rahulsharma");
        member.setInstagram("https://instagram.com/rahulsharma");
        member.setPhotoUrl("https://example.com/photos/rahul.jpg");
        return member;
    }
}
