package com.example.jocke.unicornigotchi.dal;

import com.example.jocke.unicornigotchi.dto.Unicorn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccessUnicorn {
    @GET("/UnicornApi/GetAll")
    Call<List<Unicorn>> getAllUnicorns();

    @GET("/UnicornApi/{id}")
    Call<List<Unicorn>> getUnicorn(@Path("unicorn") String unicorn);
}
