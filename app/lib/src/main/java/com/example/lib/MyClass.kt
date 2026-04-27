package com.example.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    println("===Corrutinas===")

    //1. RunBlocking, es una corrutina que BLOQUEA EL HILO PRINCIPAL
    //NO SE DEBE USAR EN PRODUCCION
    corrutinaLaunch()
    corrutinaAsync()
}

fun corrutinaAsync(){
    runBlocking {
        println("Haciendo peticiones /GET")
        val result = async{
            println("")
            delay(timeMillis = 6000)
            println("Retornando resultado")
            """ {"id":1,""name:"Abraham"} """
        }
        println("El resultado de la peticion es ${result.await()}")
        //Await nos ayuda a bloquear la corrutina hasta que termine de entregar el resultado
    }
}

fun corrutinaLaunch(){
    runBlocking {
        println("Cargando interfaz grafica...")
        launch (context = Dispatchers.IO){
            //Launch ejecuta código asíncrono
            consultaAPI()
        }
        println("La UI sigue cargando mientras terminala corrutina")
    }
}

suspend fun consultaAPI(){
    println("Consultando la API")
    delay(timeMillis = 2000) //Funciön asíncrona
    println("La respuesta es que todo salió bien")

}