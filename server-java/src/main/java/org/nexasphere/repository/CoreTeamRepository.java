package org.nexasphere.repository;

import org.nexasphere.model.entity.CoreTeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoreTeamRepository extends JpaRepository<CoreTeamMemberEntity, Long> {
    List<CoreTeamMemberEntity> findAllByOrderByCreatedAtAsc();
}
