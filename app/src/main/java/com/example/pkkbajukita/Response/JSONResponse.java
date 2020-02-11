package com.example.pkkbajukita.Response;


import com.example.pkkbajukita.Value.ValueBarang;
import com.example.pkkbajukita.Value.ValueSelesai;
import com.example.pkkbajukita.Value.ValueUnggahan;

public class JSONResponse {
    private ValueBarang[] data;
    private ValueUnggahan[] unggahan;
    private ValueSelesai[] selesai;

    public ValueBarang[] getData() {
        return data;
    }

    public ValueUnggahan[] getUnggahan() {
        return unggahan;
    }

    public ValueSelesai[] getSelesai(){
        return selesai;
    }
}
