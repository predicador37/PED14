# Notas para el despliegue
La práctica se ha desarrollado utilizando jdk 1.7. Por ello, para el correcto
funcionamient es necesario realizar los siguientes pasos:

- Ejecutar el comando update-alternatives --config java y seleccionar JDK 1.7
- Crear el archivo setenv.sh en la ruta:
  
  /usr/share/tomcat7/bin

  con el siguiente contenido:
  
  #!/bin/sh
  export JAVA_OPTS=”-Xms256m -Xmx1024m”