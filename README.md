# Escape-from-fate
Proyecto de videojuego para la materia desarrollo de videojuegos dictada por el profesor Jose Sanchez, en la universidad del Zulia (LUZ)

1-	Concepto general del juego

a- Enfoque temático del juego
i-	Sueño a cumplir: Escapar de la prisión psiquiátrico superando los retos en cada nivel (habitación).
ii-	Entorno del juego: Se desarrolla en una prisión psiquiátrica.
iii-	Perspectiva: Camara Top down (vista desde arriba).

b- Mecánicas del juego
i-	Objetivo del jugador: Salir con vida de la prisión, superar los obstáculos.
ii-	Acciones del jugador: Resolver puzzles y derrotar enemigos, con uso de armas.
iii-	Retos a enfrentar el jugador: Los retos serán de tipo Intrínsecos.
c- Juegos similares: 

-	Hotline Miami.
 
-	Adventures of Lolo.
 
El juego consiste en escapar de una prisión psiquiátrica, en el que el jugador es un enfermo mental que vive el mundo “real” y uno “surreal”, en el que el mundo surreal interpreta a los demás integrantes del juego como personajes de videojuegos, o caricaturas famosas (mario bros, Mickey mouse, Son Goku…), y que para escapar de la prisión debe asesinar a cada uno de ellos y resolver puzzles para lograr el objetivo. El juego acaba cuando el jugador es asesinado o cuando es atrapado.

3-	Jugabilidad
El jugador termina el nivel cuando cumpla con el objetivo específico del mapa (acabe con los enemigos y/o supere el puzzle, según sea el caso). Cuando se cumpla con el objetivo específico del mapa, el jugador se debe situar en un punto específico para poder avanzar al siguiente nivel.

El jugador va a poder interactuar con muchos objetos de forma dinámica y a tiempo real para su propia estrategia, tales como mover una silla mientras dispara para poder cubrirse de los enemigos. También va a poder usar varios de esos objetos para resolver puzles específicos del mapa (el uso de una llave para abrir una puerta, por ejemplo…). También van a haber objetos destruibles en los mapas.

Los enemigos van a intentar eliminar al jugador, van a estar en una formación específica (caminando alrededor del mapa, en círculos…), o simplemente parados en algún punto esperando al jugador. Van a poder empujar objetos para poder pasar si es que significa un obstáculo para él.

El jugador  va a tener la opción de disparar o arrojar el arma para poder cambiarla, va a contar con un inventario de ítems en el mapa y si se acaba la munición del arma, deberá buscar otra.

4-	Apartado técnico


El videojuego se programa en el lenguaje Java con el framework libGDX 0.9.9, se hace uso del motor de físicas box2D para implementar las colisiones, interacciones, uso de partículas e impulsos para tener más dinamismo y realismo en el juego a tiempo real con el uso de la gravedad y demás cosas que ofrece esa librería.

Para la inteligencia artificial se hace uso del grafo A* con ligeras modificaciones para adaptarlo al juego. Los mapas son archivos tiled con matrices 16x16, donde sólo va a tener el piso y las paredes que se cargan solo como fondo en el mismo juego. Los objetos tales como sillas, escritorios… Se cargan dinámicamente en el programa de modo que sea posible la interacción con ellos a tiempo real.
