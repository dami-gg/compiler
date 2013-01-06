package Interprete;

import java.util.*;

public class Contexto {

    public int IP;

    public int BP;

    public Memory mem;

    public HashMap <String, Integer> TS;

    public int VG; // Índice para variables globales, comienza en 0 y va aumentándose

    public Contexto () {

        IP = 0;

        BP = 0;

        mem = new Memory ();

        TS = new HashMap <String, Integer> ();

        VG = 0;
    }

    public Contexto (Contexto c) {

        this.IP = c.IP;

        this.BP = c.BP;

        this.mem = c.mem;

        this.TS = c.TS;

        this.VG = c.VG;
    }

    public void AnadirEtiqueta (String etiq, int dir) {

        if (! TS.containsKey(etiq))

            TS.put(etiq, dir);

        else

            System.out.println ("\nError: etiqueta " + etiq + " repetida");
    }

    public int ObtenerDirEtiqueta (String etiq) {

        Integer dir = (Integer) TS.get(etiq);

        if (dir == null) {

            System.out.println ("\nError: la etiqueta " + etiq + " no existe");

            return -1;
        }

        return dir.intValue();
    }
}