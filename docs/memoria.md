# Introducción
# Composición del grupo y roles
## Miembros del grupo

- Héctor Garnacho García, 12398942X.
- Marcos Azorí, 53017273L.
- Miguel Expósito Martín, 72056097H.

## Matriz miembros/roles

|Rol|Marcos Azorí|Héctor Garnacho|Miguel Expósito |
|---|------------|---------------|----------------|
|Jefe de proyecto||X||
|Analista|X|X|X|
|Arquitecto|||X|
|Desarrollador||X|X|
|Integrador|||X|
|Validador|X|X||
|Documentador|X|X|X|

#Plan de trabajo
|Hito|Descripción|Fecha estimada |
|---|------------|---------------|----------------|
|Jefe de proyecto||X||
#Especificación de requerimientos
##Requisitos funcionales

Tabla 1. Identificación de requisitos funcionales
---------------------------------------------------------------------------------------
id      nombre            descripción
------- ----------------- -----------------------------------------------------------------
1       usuario anónimo   El sistema permitirá por defecto el acceso de usuarios anónimos a
                          una página de inicio que mostrará una interfaz de búsqueda.
2       parámetros de     La página de inicio permitirá realizar búsquedas por los siguientes
        búsqueda          parámetros: localización geográfica, experiencia profesional,
                          titulaciones y conocimientos de una determinada tecnología.
3       visualización de  El sistema permitirá a un usuario anónimo visualizar características
        características   de un determinado currículum encontrado mediante la interfaz de
                          búsqueda.
4       mostrar interés   El sistema debe permitir a los usuarios anónimos mostrar un interés
                          determinado por un elemento concreto del currículum (titulación...)
5       alta de usuairo   El sistema debe permitir a un usuario anónimo su alta en el sistema
                          a través de un formulario sencillo (nombre, contraseña, email,
                          provincia).
6       alta de elementos El sistema debe permitir añadir elementos al currículum de un usuario
                          registrado. Los detalles de dichos elementos deberán poder ser
                          consultados.
7       modificación de    El sistema debe permitir a un usuario registrado modificar los datos
        elementos         de los elementos que conforman su currículum.
8       baja de           El sistema debe permitir a un usuario registrado dar de baja elementos
        elementos         que conforman su currículum.
9       información de    La información asociada a los elementos deberá ser al menos la necesaria
        los elementos     para que funcionen los filtros de búsqueda. Opcionalmente, se podrá
                          incluir un documento.
10      perfil profesional El sistema deberá permitir a un usuario registrado elaborar un perfil
                          profesional con elementos que incluyan años de inicio y fin y cargo,
                          así como nombre de empresa y descripción breve.
11      perfil con foto    El sistema deberá permitir a un usuario registrado asociar una foto
                          a su perfil profesional.
12      baja del sistema  El sistema deberá permitir a un usurio registrado darse de baja en
                          el mismo.
13      administrador     Existirá un perfil administrador del sistema que podrá accer, entre
                          otras, al resto de funciones de los demás actores.
14      funciones de      El perfil administrador del sistema podrá habilitar o deshabilitar
        sistema           determinadas funciones como alta, modificación y baja de elementos.
                          Estando deshabilitadas, ningún usuario podrá utilizar dichas
                          funciones.
15      baja de usuarios  El perfil administrador del sistema podrá dar de baja a usuarios.

## Casos de uso

#Esquema de base de datos

##Consideraciones de diseño

Para simplificar el modelado del sistema, se han tomado las siguientes decisiones de diseño:

- Evitar, en la medida de lo posible, las relaciones `many-to-many`. Su tratamiento es complejo usando `frameworks` de persistencia, y la experiencia de muchos diseñadores indica que se agiliza el desarrollo si en su lugar se utilizan tan sólo relaciones `one-to-many`. Además, en el caso concreto de esta aplicación, dada la gran variedad de entidades que podrían darse (cursos, titulaciones, etc.), la posible ganancia obtenida de su normalización sería mínima en comparación con la dificultad entrañada.
- Utilizar búsquedas de tipo `like`: dado que no se especifican requisitos técnicos acerca del tipo de búsquedas, se optó por implementar la solución más simple (búsqueda de una cadena de texto en cualquier posición de otra). En futuras iteraciones del sistema podría contemplarse el uso de Hibernate Search, que permite realizar búsquedas *fulltext*.
- Utilizar enumeraciones para los tipos más simples: en concreto, para modelar los niveles de conocimiento (alto, medio, bajo).

##Dominio de la información

El dominio de la información asociada al sistema se compone de las siguientes entidades:

- Usuario: representa un usuario del sistema. Fundamentalmente almacena información sobre login (correo electrónico) y provincia a la que pertenece. Presenta una asociación `one-to-one` con currículum.
- Currículum: entidad principal que modela los datos asociados a un currículum de un usuario (nombre, apellidos, archivos...). Presenta asociaciones `one-to-many` con los elementos que lo componen (experiencia, titulaciones, cursos, conocimientos...)
- Experiencia profesional: entidad que modela las experiencias profesionales asociadas a un currículum, pudiendo especificarse sus fechas de inicio y de fin. La lógica de negocio se encargará de calcular la experiencia total.
- Titulación: entidad que modela las posibles titulaciones asociadas a un usuario. Texto libre y un campo con el año de finalización.
- Conocimiento: entidad que modela los posibles conocimientos asociados a un usuario. A su vez presentan un campo "nivel de conocimiento" (una enumeración con valores posibles medio, bajo y alto).
- Curso de formación: entidad que representa los posibles cursos de formación asociados a un usuario o currículum.

En la siguiente figura se encuentra un diagrama con el modelo de datos:

![Modelo de datos](./img/modelo.png)

#Arquitectura

La arquitectura del sistema se define teniendo presente el patrón DRY (Don't Repeat Yourself). Se seleccionó un arquetipo Maven con una integración adecuada de distintos frameworks según la capa concreta del patrón MVC (Modelo - Vista - Controlador).

El arquetipo Maven es un proyecto abierto relativamente conocido que puede descargarse aquí:

      https://github.com/kolorobot/spring-mvc-quickstart-archetype

Para poder utilizar el arquetipo con la configuración de plataforma especificada en los requisitos técnicos se tuvieron que hacer ciertos cambios que permitieran su ejecución con la plataforma Java EE 1.7. A partir de dicho arquetipo se confeccionó un esqueleto para el desarrollo del sistema que se subió a un repositorio de código fuente gratuito (Github), pudiendo consultarse aquí:

      https://github.com/predicador37/PED14

Dicho repositorio conforma la solución colaborativa utilizada por el grupo de desarrollo. En su página principal se encuentra información somera sobre las características del proyecto y documentación sobre el proceso de envío e intercambio de código, así como de construcción y pruebas del software.

La arquitectura del sistema consta de las siguientes capas:

## Modelo
Capa que contiene el modelo del dominio de la información, con las subcapas de acceso a datos (patrón Repository, en este caso) y lógica de negocios (POJOs y servicios). Para el modelo se utilizan los siguientes frameworks o tecnologías de apoyo:

### Acceso a datos
- JPA (Java Persistent Access) y su implementación Hibernate: proporcionan el acceso a datos a través del patrón *repository*. Como añadido, se ha utilizado la biblioteca `spring-data` para generar los métodos CRUD (Create - Read - Update - Delete) típicos en todas las entidades.

## Lógica de negocio
- POJOs: *Plain Old Java Objects*, para modelar las entidades básicas que se mapean con tablas en la base de datos. Contienen parte de la lógica de negocio, que será encapsulada posteriormente en servicios.
- Spring: framework "aglutinador" cuya característica principal es la **inversión de control** (IoC), que permite inyectar dependencias en las clases. Además, `Spring` garantiza la transaccionalidad en la definición de los distintos servicios que encapsulan la mayor parte de la lógica de la aplicación.

## Controlador
Capa que recibe las peticiones de las vistas y llama a los servicios que encapsulan la lógica de negocio para realizar ciertas operaciones y devolver los resultados en forma de vistas al usuario. Para implementar la capa de controlador se ha utilizado Spring MVC, un framework moderno (sucesor de Struts) que se integra fácilmente con el resto de tecnologías utilizadas en la arquitectura de la aplicación.

## Vista
Capa que constituye la frontera de interacción con el usuario, que le permite enviar sus peticiones a la capa de controlador y recibir información procesada. Para implementar la vista se han utilizado las siguientes tecnologías:

- Thymeleaf: framework de plantillas basado en HTML5 que se integra de forma nativa con Spring MVC, permitiendo características como el renderizado de HTML, validación, interacción con capas de seguridad, etc.

- Twitter Bootstrap: framework CSS que permite construir de forma sencilla una interfaz adaptable (`responsive`) para la aplicación, con estilos familiares y conocidos por la mayor parte de los usuarios de aplicaciones web.

- JQuery: framework javascript que permite dotar a la aplicación de funcionalidades de interfaz avanzadas así como interacciones asíncronas con el servidor (AJAX).

En la siguiente figura se muestra un resumen de la arquitectura utilizada:

![Arquitectura del sistema](./img/arquitectura.png)

#Guía de usuario
#Conclusiones

#Anexos
