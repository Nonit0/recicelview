## Proyecto: Lista Horizontal de Personajes (RecyclerView)

Este proyecto implementa un RecyclerView horizontal básico para mostrar personajes de distintas series. El objetivo principal es disponer de una interfaz de elementos en formato de carrusel.

## Funcionalidad actual

En la versión actual, el proyecto incluye:

### Estructura del proyecto

- **RecyclerView** configurado en orientación horizontal mediante `LinearLayoutManager`.
- **Adapter**
  - `ViewHolderChar`: asigna las variables necesarias al ViewHolder.
  - `AdapterChar`: infla el ViewHolder y gestiona la lista.
- **Controller**: obtiene los datos desde el DAO y configura el RecyclerView.
- **DAO**: se crea la instancia de los objetos `Char`.
- **Interfaz**: define el método para obtener la lista de `Char`.
- **Models**: clase POJO que representa un personaje.
- **Repository (objects_models)**: almacena en memoria los datos de los personajes.

- Un diseño sencillo que permite visualizar los personajes de forma directa.
- Una implementación mínima que sirve como base para futuras ampliaciones.

## Funcionalidades previstas

En futuras versiones del proyecto se implementarán las siguientes mejoras:

### Login

- Sistema de login personalizado.

### Menú horizontal de categorías

- Se incorporará un menú horizontal que mostrará diferentes series como categorías seleccionables.

### Carga dinámica de fragments

- Cada categoría cargará un Fragment independiente, el cual incluirá su propio RecyclerView con los personajes correspondientes.

### Listas independientes por serie

- Cada sección dispondrá de su propia lista y adaptador, lo que facilitará la modularidad y el mantenimiento del proyecto.

## Objetivo del proyecto

El propósito final es construir una interfaz modular basada en:

- Un sistema de login único.
- Un sistema de categorías horizontales.
- Carga dinámica de fragments.
- Varios RecyclerViews independientes, uno por serie.

La implementación actual del RecyclerView horizontal constituye la base sobre la que se desarrollarán estas funcionalidades.
