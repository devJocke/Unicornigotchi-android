package com.example.jocke.unicornigotchi.dal;

import com.example.jocke.unicornigotchi.dto.NeedsSum;
import com.example.jocke.unicornigotchi.dto.Unicorn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccessUnicorn {
    @GET("/UnicornApi/GetAllUnicorns")
    Call<List<Unicorn>> getAllUnicorns();

    @GET("/UnicornApi/{id}")
    Call<Unicorn> getUnicorn(@Path("id")  Integer id);

    @GET("/UnicornApi/SumOfAllNeeds")
    Call<NeedsSum> getUnicornNeedsSum();

}
