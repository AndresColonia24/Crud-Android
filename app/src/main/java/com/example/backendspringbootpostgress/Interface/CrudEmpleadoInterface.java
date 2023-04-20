package com.example.backendspringbootpostgress.Interface;

import com.example.backendspringbootpostgress.Model.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CrudEmpleadoInterface {
    @GET("/consultarAll")
    Call<List<Empleado>> getAll();

    @POST("/guardar")
    Call<Empleado> saveEmployee(@Body Empleado empleado);

    @DELETE("/user/{id}")
    Call<Empleado> deleteEmployee(@Path("id") long id);

    @GET("/consultar/{id}")
    Call<Empleado> consultarID(@Path("id") long id);

    @PUT("/actualizar/{id}")
    Call<Empleado> update(@Path("id") long id, @Body Empleado empleado);
}
