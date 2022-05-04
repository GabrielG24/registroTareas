package com.tareas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.tareas.dao.TareasDao;
import com.tareas.model.Tarea;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TareasDaoTest {
	
	@Autowired
	TareasDao dao;
	
	Date date = new Date(0);
	
	@Test
	void TareaSaveTest(){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		formatter.format(date);
		Tarea tarea = new Tarea("2","test",date,true);
		Tarea tareaGuardada = dao.save(tarea);
		assertNotNull(tareaGuardada);
	}
	
	@Test
	void tareaFindByIdTest() {

	  String id = "1";
	  Optional<Tarea> tareaList = dao.findById(id);
	  assertTrue(tareaList.isPresent());
	}
	
	@Test
	@Rollback(false)
	void tareaUpdateTest() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		formatter.format(date);
		String id = "1";
		Tarea tarea = new Tarea(null,"test7",date,false);
		tarea.setIdTarea("1");
		dao.save(tarea);
		
		Optional<Tarea> tareaActualizar = dao.findById(id);
		
		assertThat(tareaActualizar.orElseThrow().getIdTarea()).isEqualTo(id);
	}

}
