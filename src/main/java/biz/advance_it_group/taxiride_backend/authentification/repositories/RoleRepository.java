package biz.advance_it_group.taxiride_backend.authentification.repositories;

import biz.advance_it_group.taxiride_backend.authentification.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

	Optional<Roles> findByRole(String role);
}
