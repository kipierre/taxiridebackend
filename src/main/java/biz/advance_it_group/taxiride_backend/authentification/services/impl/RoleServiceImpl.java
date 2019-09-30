package biz.advance_it_group.taxiride_backend.authentification.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.dto.RoleRequest;
import biz.advance_it_group.taxiride_backend.authentification.entities.Roles;
import biz.advance_it_group.taxiride_backend.authentification.repositories.RoleRepository;
import biz.advance_it_group.taxiride_backend.authentification.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Roles> findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    public Roles save(Roles role) {
        roleRepository.save(role);

        return role;
    }

    /**
     * Find all roles from the database
     */
    public Collection<Roles> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Roles createRole(Roles role) {
        return null;
    }

    @Override
    public Roles updateRole(Long roleID, Roles roleRequest) {
        return null;
    }

    @Override
    public void deleteRole(Long roleId) {

    }

    @Override
    public List<Roles> getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Roles addRoleToUser(Long userId, Roles role) {
        return null;
    }

    @Override
    public Roles updateRoleWithUser(Long userId, Long roleId, Roles roleRequest) {
        return null;
    }

    @Override
    public void deleteRoleWithUser(Long userId, Long roleId) {

    }

    @Override
    public Optional<Roles> findById(Long roleId) {
        return null;
    }


    /**
     * Creates a new role from the role request
     */
    public Roles createRole(RoleRequest roleRequest) {
        Roles newRole = new Roles();
        newRole.setRole(roleRequest.getRole());
        return newRole;
    }
}

