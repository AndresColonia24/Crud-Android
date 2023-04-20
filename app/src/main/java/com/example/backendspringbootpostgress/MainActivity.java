package com.example.backendspringbootpostgress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.backendspringbootpostgress.Interface.CrudEmpleadoInterface;
import com.example.backendspringbootpostgress.Model.Empleado;
import com.google.gson.internal.GsonBuildConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    List<Empleado> listEmpleado;
    String baseURL;
    Empleado empleadoGuardado, juan, updatedEmpleado;
    CrudEmpleadoInterface crudEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Objeto Empleado de para probar los metodos de guardarEmpleado() y updateEmpleado()
        juan = new Empleado();
        juan.setNombre("Juan");
        juan.setEmail("juan@mail.com");
        juan.setPassword("juan123");

        //Hola profe para probar cambiar la ip por la ipv4 del adaptador de red
        baseURL = "http://192.168.101.6:8081";


        // Devuelve todos los registros
        getAll();
        // Se le pasa un id para consultar un registro
        //consultarID(1);
        // Se le pasa un id para borrar un registro
        //delete(2);
        // Se le pasa un objeto Empleado para hacer el registro
        //guardarEmpleado(juan);
        // Se le pasa un Long id  un objeto Empleado para actualizar un registro existente
        //updateEmpleado(1L,juan);
    }

    // Create Employee
    private void guardarEmpleado(Empleado empleado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.saveEmployee(empleado);

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {
                    empleadoGuardado = response.body();
                    Log.d("Guardar empleado", "Empleado guardado con éxito: " + empleadoGuardado.toString());
                } else {
                    Log.d("Guardar empleado", "Error al guardar el empleado: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Guardar empleado", "Error al guardar el empleado: " + t.getMessage());
            }
        });
    }

    // ReadAll Employee
    private void getAll(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<List<Empleado>> call = crudEmpleado.getAll();

        call.enqueue(new Callback<List<Empleado>>() {
            @Override
            public void onResponse(Call<List<Empleado>> call, Response<List<Empleado>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                listEmpleado = response.body();
                listEmpleado.forEach(p -> System.out.println(p.toString()));
            }

            @Override
            public void onFailure(Call<List<Empleado>> call, Throwable t) {
                System.out.println(t.getMessage());
                Log.e("Throw error:",t.getMessage());
            }
        });
    }

    // Read Employee
    private void consultarID(long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.consultarID(id);

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (!response.isSuccessful()) {
                    Log.d("Consultar ID Error", response.message());
                    return;
                }

                Empleado empleadoById = response.body();
                if (empleadoById != null) {
                    Log.d("Empleado por ID", empleadoById.toString());
                } else {
                    Log.d("Consultar ID Error", "La respuesta fue nula");
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Consultar ID Error", t.getMessage());
            }
        });
    }

    // Update Employee
    private void updateEmpleado(long id, Empleado empleado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.update(id, empleado);

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (!response.isSuccessful()) {
                    Log.e("Update Empleado Error", response.message());
                    return;
                }

                updatedEmpleado = response.body();
                if (updatedEmpleado != null) {
                    Log.d("Empleado Actualizado", updatedEmpleado.toString());
                } else {
                    Log.e("Update Empleado Error", "La respuesta fue nula");
                }
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Update Empleado Error", t.getMessage());
            }
        });
    }

    // Delete Employee
    private void delete(long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.deleteEmployee(id);
        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado>response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                System.out.println("Empleado con Id " + id + " fue eliminado");
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                System.out.println(t.getMessage());
                Log.e("Throw error:",t.getMessage());
            }
        });
    }

}