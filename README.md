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


