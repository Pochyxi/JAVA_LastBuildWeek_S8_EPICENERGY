package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.Role;
import com.example.lastbuildweek.entities.RoleType;
import com.example.lastbuildweek.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {


	@Autowired
	private RoleService rs;

	// LISTA DEI RUOLI DISPONIBILI
	@GetMapping("")
	@PreAuthorize( "hasRole('ADMIN')" )
	public List<Role> getAllRoles() {

		return rs.getAll();

	}

	// LISTA DEI RUOLI DISPONIBILI PAGINABILE
	@GetMapping("/pageable")
	public ResponseEntity<Page<Role>> getAllRolesPageable(Pageable p) {
		Page<Role> findAll = rs.getAllPaginate(p);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	// RITORNA UN SINGOLO RUOLO PER ID(PK)
	@GetMapping("/{id}")
	@PreAuthorize( "hasRole('ADMIN')" )
	public ResponseEntity<Role> readById(@PathVariable Long id) throws Exception {

			return new ResponseEntity<>(rs.getById(id), HttpStatus.OK);			

	}

	// MODIFICA I RUOLI
	@PutMapping("")
	@PreAuthorize( "hasRole('ADMIN')" )
	public void update(@RequestBody Role role) {

		try {

			rs.save(role);

		} catch (Exception e) {

			log.error(e.getMessage());

		}

	}

	// ELIMINA RUOLO
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteById(@PathVariable Long id) {

		try {

			rs.deleteById(id);

		} catch (Exception e) {

			log.error(e.getMessage());

		}
	}
}