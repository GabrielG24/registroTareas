package com.tareas.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.tareas.controller.Validations;
import com.tareas.dao.TareasDao;
import com.tareas.model.Tarea;

@RestController
@RequestMapping("tareas")
public class TareasController {
	
	private static Logger log = LoggerFactory.getLogger(TareasController.class);
	
	@Autowired
	private TareasDao tareasDao;

/*	@GetMapping("/allpag")
	public Page<Tarea> getTareaPag(@PageableDefault(size=5,page=0) Pageable pageable){
		log.info("entro ala->");
		Page<Tarea> result=tareasDao.findAll(pageable);
		return result;
	}*/
	
	@GetMapping("/all")
	public ResponseEntity<List<Tarea>> getTareas(){
		List<Tarea> tareas=tareasDao.findAll();
		return ResponseEntity.ok(tareas);
	}
	
	@GetMapping(value="{tareaId}")
	public ResponseEntity getTareaById(@PathVariable("tareaId") String tareaId){
		log.info("tareaId->"+tareaId);
		Optional<Tarea> optionalTarea = tareasDao.findById(tareaId);
		if(optionalTarea.isPresent()) {
			return ResponseEntity.ok(optionalTarea.get());	
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND+ ": Tarea no encontrada. ",HttpStatus.NOT_FOUND);
		}			
	}	
	
		
	@RequestMapping(value="/v2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tarea> getTareaById2(@RequestBody Tarea tareaId, HttpServletRequest request) {
		log.info("tareaId->"+tareaId.getIdTarea());
		Tarea respuestaId = new Tarea();
		log.info("uno");
		ResponseEntity<Tarea> respuesta = new ResponseEntity<Tarea>(respuestaId,HttpStatus.IM_USED);
		log.info("dos");
		try {
			Optional<Tarea> optionalTarea = tareasDao.findById(String.valueOf(tareaId.getIdTarea()));
			log.info("a String->"+optionalTarea);
			if(optionalTarea.isPresent()) {
				log.info("entro");
				//respuesta = ResponseEntity.ok(optionalTarea.get());
				respuesta = new ResponseEntity<Tarea>(optionalTarea.get(),HttpStatus.OK);
			}else {
				respuesta = new ResponseEntity<>(respuestaId,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IllegalArgumentException iex) {
			respuesta = new ResponseEntity<>(respuestaId,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}catch (Exception e) {			
			respuesta = new ResponseEntity<>(respuestaId,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return respuesta;
	}
	
	
	@PostMapping
	public ResponseEntity<String> createTarea(@RequestBody Tarea tarea) {
		log.info("acaaaa->"+tarea.getFechaCreacion()+"mal");
		log.info("acaaaa->"+tarea.getVigente()+"mal");
		if( !tarea.getIdTarea().equals("")  && !tarea.getDescripcion().equals("") && tarea.getFechaCreacion()!=null ){
			tareasDao.save(tarea);
			return new ResponseEntity<>(HttpStatus.CREATED+ ": Tarea creada. ",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST+ ": Todos los datos de entrada son obligatorios ",HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@DeleteMapping(value="{tareaId}")
	public ResponseEntity<String> deleteTarea(@PathVariable("tareaId") String tareaId) {
		
		log.info("tareaId->"+tareaId);
		Optional<Tarea> optionalTarea = tareasDao.findById(tareaId);
		
		if(optionalTarea.isPresent()) {
			tareasDao.deleteById(tareaId);			
			return new ResponseEntity<>(HttpStatus.OK+ ": Tarea borrada. ",HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND+ ": tarea no encontrada. ",HttpStatus.NOT_FOUND);
		}
	}

	
	@PutMapping(value="{tareaId}")
	public ResponseEntity<Tarea> updateStudent(@PathVariable("tareaId") String tareaId,@RequestBody Tarea tarea) {

		Optional<Tarea> optionalTarea = tareasDao.findById(tareaId);
		
		if(optionalTarea.isPresent()) {
			Tarea updateTarea= optionalTarea.get();
			updateTarea.setIdTarea(tarea.getIdTarea());
			updateTarea.setDescripcion(tarea.getDescripcion());
			updateTarea.setFechaCreacion(tarea.getFechaCreacion());
			updateTarea.setVigente(tarea.getVigente());
			tareasDao.save(updateTarea);
		
		return ResponseEntity.ok(updateTarea);	
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}