package com.example.fakestoreapp.services

import com.example.fakestoreapp.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET(value = "products")
    suspend fun getAllProducts() : List<Product>

    //Path, Query String y Body
    //1---> PATH
    //2---> ?name=Juan&lastName=Frausto
    // { "name":"Juan"} --> Body POST, PUT, PATCH
    @GET(value = "products/{id}")
    suspend fun getProductById( @Path( "id")id: Int) : Product
}