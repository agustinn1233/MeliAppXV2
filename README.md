MeliAppXv2
Esta aplicación fue diseñada y creada en base al modelo de Mercado Libre, con modificaciones.
No es la aplicación real, es una demo.
</br>
La aplicación cuenta con:
Pantalla inicial: En la cual le proporciona al usuario un buscador de productos, aparte de publicidad de marcas (estáticas) para acompañar el diseño de la vista.
</br>
Pantalla de listado de producto: La misma, lista los productos buscados por el usuario, esta pantalla se encuentra de forma adjunta a la pantalla inicial para proporcionarle al usuario la simpleza de utilizar la aplicación.
</br>
Pantalla de detalle del producto: Esta pantalla detalla el producto seleccionado por el usuario previamente.
</br>
La aplicación esta creada en base a Kotlin/Java
</br>
Para la creación del proyecto se utilizó el patrón MVVM (model–view–viewmodel).
 
Android Jetpack:
</br>
Jetpack es un conjunto de bibliotecas que ayuda a los desarrolladores a seguir las prácticas recomendadas, reducir el código estándar y escribir código que funcione de manera coherente en los dispositivos y las versiones de Android para que puedan enfocarse en el código que les interesa.
</br>
ViewModel es una clase que se encarga de preparar y administrar los datos para una Activity o una Fragment. También maneja la comunicación de la Actividad / Fragmento con el resto de la aplicación (por ejemplo, llamando a las clases de lógica de negocios).La única responsabilidad de ViewModel es administrar los datos para la IU. Nunca debe acceder a su jerarquía de vistas ni retener una referencia a la Actividad o al Fragmento
</br>
LiveData es una clase de retención de datos observable. A diferencia de una clase observable regular, LiveData está optimizada para ciclos de vida, lo que significa que respeta el ciclo de vida de otros componentes de las apps, como actividades, fragmentos o servicios. Esta optimización garantiza que LiveData solo actualice observadores de componentes de apps que tienen un estado de ciclo de vida activo, lo cual ayuda a prevenir la pérdida de memoria (Memory Leaks)
</br>
DataBinding es una biblioteca de vinculación de datos que permite vincular los componentes de la IU de tus diseños a las fuentes de datos de tu app usando un formato declarativo en lugar de la programación.
La vinculación de componentes en el archivo de diseño permite quitar varias llamadas al marco de trabajo de la IU en las actividades, que resultan más sencillas y fáciles de mantener. Esto también puede mejorar el rendimiento de la app y ayudar a evitar pérdidas de memoria y excepciones de puntero nulo.
</br>

Detalle de librerías añadidas al Proyecto:
</br>
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
</br>
implementation 'com.github.bumptech.glide:glide:4.11.0'
</br>
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
</br>
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
</br>
// DataBinding with Kotlin
apply plugin: 'kotlin-android'
</br>
apply plugin: 'kotlin-kapt'
</br>
apply plugin: 'kotlin-android-extensions'
</br>
kapt  'com.android.databinding:compiler:3.1.4'
</br>

</br></br>
Lifecycle (Uso en ViewModel & LiveData), permite realizar acciones a los cambios de estados de otros componentes como (Activity,Fragment), estos componentes de Lifecycle, ayudan a tener un código mejor organizado y de menor tamaño.
</br>
Glide: El objetivo es la carga de imágenes de manera rápida y eficiente, (a mi gusto mejor que Picasso, sin ofender a nadie).
</br> 
Retrofit: Librería que se utiliza para hacer llamadas en la red y obtener resultados estructurados de una vez. La única particularidad es que se necesita hacer clases que representen a nuestras entidades en formato “POJO”.
</br>
Project Structure:
</br>
Android Gradle Plugin Version: 4.1.1
</br>
Gradle Version 6.5
</br>
compileSdkVersion 30
</br>
buildToolsVersion 30.0.2
</br>
minSdkVersion 23
</br>
targetSdkVersion 30
</br>
build.gradle (project)
</br>
buildscript
</br>
ext.kotlin_version = '1.4.10'
</br>
dependencies
</br>
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"




