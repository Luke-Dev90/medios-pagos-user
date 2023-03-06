package com.lchalela.mediospagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lchalela.mediospagos.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
