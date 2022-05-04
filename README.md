# registroTareas
Registro de tareas: Proyecto SpringBoot con base de datos mysql, se utiliza ORM con Jpa

Antes de ejecutar el proyecto SpringBoot realizar lo siguiente

1.- Instalar mysql
2.- entrar con usuario=root y password= root
3.- crear base de datos de nombre "prueba_tareas"
4.- Ejecutar el proyecto spring boot y se creará automáticamente la tabla tareas
5.- Ejecutar con postman los 4 servicios disponibles.
    _(get) tareas/all
    _(get) tareas/{id}
    _(post) tareas/v2   -> es otra versión para consultar por una tarea
    _(post) tareas  -> es para crear una tarea
    _(delete) tareas/{id}  -> borra una tarea
    _(put) tareas/{id}  -> actualiza una tarea
