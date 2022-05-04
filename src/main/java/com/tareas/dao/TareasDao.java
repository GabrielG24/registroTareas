package com.tareas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tareas.model.Tarea;

public interface TareasDao extends JpaRepository<Tarea, String>{

}