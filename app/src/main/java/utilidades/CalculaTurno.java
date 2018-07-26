package utilidades;

import java.util.Calendar;
import java.util.Date;

public class CalculaTurno {

    public static String DevuelveTurno()
    {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        int dia = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        String turno = "";

        if(hora >= 0 && hora < 6)
        {
            if(dia > 0 && dia < 4)
            {
                turno = "Tercero";
            }
            else
            {
                turno = "Cuarto";
            }
        }

        if(dia > 0 && dia < 6)
        {
            if(hora > 5 && hora < 13)
            {
                turno = "Primero";
            }
            else if(hora > 12 && hora < 20)
            {
                turno = "Segundo";
            }
        }

        if(dia == 6)
        {
            if (hora > 5 && hora < 12)
            {
                turno = "Primero";
            }
            else if (hora > 11 && hora < 20)
            {
                turno = "Segundo";
            }
        }

        if(hora > 19 && hora < 24)
        {
            if(dia > 3 && dia < 8)
            {
                turno = "Cuarto";
            }
            else
            {
                turno = "Tercero";
            }
        }

        if(dia == 0)
        {
            if(hora > 5 && hora < 20)
            {
                turno = "Tercero";
            }
        }

        return turno;
    }
}
