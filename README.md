# PED14: Práctica Tecnologías Web
## Requisitos
- JDK 1.7
- Tomcat 7
- [Maven 3](https://maven.apache.org/)
- Eclipse IDE

##Instalación
En una consola, en el directorio del `workspace` de Eclipse, ejecutar:

      git clone https://github.com/predicador37/PED14

En Eclipse, `Archivo -> Importar -> Existing Maven Projects` y seleccionar el pom.xml del proyecto.

##Ejecución 
En una consola, en el directorio del proyecto, ejecutar:

      mvn clean
      mvn tomcat7:run

**No es necesario desplegar el WAR en un Tomcat, porque corre el suyo propio; de ejecutarse así, verificar que no haya otro Tomcat corriendo en el mismo puerto** (`/etc/init.d/tomcat7 stop`)

###Ejecución de tests
En una consola, en el directorio del proyecto, ejecutar:
    
      mvn test

##Empaquetado
Para generar un WAR para desplegarlo posteriormente, en una consola, en el directorio del proyecto, ejecutar:

      mvn package 

##Uso de git 
- Antes de nada, descargar lo que haya en el repositorio para evitar conflitos:

      git pull

- Se realizan los cambios o añadidos pertinentes en el código
- Se vuelve a hacer un `git pull`
- Se añaden los cambios para hacer commiti (desde el directorio raíz del proyecto):

      git add .

- Se hace commit de los cambios:

      git commit -m "Mensaje del commit explicativo del código enviado"

- Se hace push al repositorio, rama master:

      git push origin master
