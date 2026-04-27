package com.example.fakestoreapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fakestoreapp.components.ProductItem
import com.example.fakestoreapp.models.Product
import com.example.fakestoreapp.services.ProductService
import com.example.fakestoreapp.ui.theme.FakeStoreAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun ProductScreen(
    innerPadding: PaddingValues = PaddingValues(all = 10.dp),
    navController: NavController = rememberNavController()
){
    val BASE_URL = "https://fakestoreapi.com/"
    var products by remember {
        mutableStateOf(listOf<Product>())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }
    //Efectos secundarios
    //Son funciones que se ejecutan cuando un composable se vuelve a ejecutar

    //Esta funcion se ejecuta cada que la key cambia, si se pone algo constante
    //solo se ejecuta al iniciar la app
    LaunchedEffect(key1 = true) {
        //Logica de conectarse al API
        try {
            //PASOS A SEGUIR
            //CREAR UNA INSTANCIA DE RETROFIT -> LIBRERIA PARA PETICIONES HTTP
            //Builder es un patron de diseño, que funciona para crear objetos complejos a partir de metodos
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            //EJECUTAR LA PETICION HTTP
            val result = async(context = Dispatchers.IO){

                val productService = retrofitBuilder.create(ProductService::class.java)
                productService.getAllProducts()

            }

            //MANEJAR LA RESPUESTA

            Log.i("ProductScreen", result.await().toString())
            products = result.await()
            isLoading = false
        }
        catch (e: Exception){
            Log.e("ProductScreen", e.toString())
            isLoading = false
        }
    }

    if (isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
    else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(products){ product ->
                ProductItem(
                    product = product,
                    onClick = {
                        navController.navigate("products/${product.id}")
                    }
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ProducScreenPreview(){
    FakeStoreAppTheme() {
        ProductScreen()
    }
}