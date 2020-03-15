package org.pacote.android.apps.simuladodetran.utils;

import java.util.Random;

public  class NumeroAleatorio {

    public static int geraInteiroDentroRange(int inicio,  int fim){
        Random r = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(1); // Evita recupear o mesmo número
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return r.nextInt(fim - inicio + 1) + inicio;
    }
}
