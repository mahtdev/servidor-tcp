package com.ks.tools.servidor;

import com.ks.lib.tcp.Cliente;
import com.ks.lib.tcp.EventosTCP;
import com.ks.lib.tcp.Tcp;
import lombok.Data;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by migue on 16/10/2015.
 */
@Log4j2
@Data
public class Comunications implements EventosTCP
{
    @Setter
    private static boolean recursive;

    private String VMstrFile;
    private BufferedReader VMioFile;
    private Tcp conexion;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.sss");
    private String message = "";
    private int time;

    private Thread processFile;

    static
    {
        recursive = Boolean.parseBoolean(System.getProperty("recursive", "false"));
    }

    public Comunications()
    {
        VMstrFile = "";
    }

    public void setFile(String file)
    {
        VMstrFile = file;
    }

    public void openFile()
    {
        try
        {
            do
            {
                VMioFile = new BufferedReader(new FileReader(VMstrFile));
                if (VMioFile != null)
                {
                    while ((message = VMioFile.readLine()) != null)
                    {
                        conexion.enviar(message);
                        Thread.sleep(time);
                    }
                    VMioFile.close();
                }
            } while (recursive);
        }
        catch (Exception e)
        {
            log.error("Problema en el procesamiento del archivo {}", VMstrFile, e);
        }
    }

    public void conexionEstablecida(Cliente cliente)
    {
        log.info("Se realizo una conexion - " + cliente.getIP() + ":" + cliente.getPuerto());
        if (processFile == null && !processFile.isAlive())
        {
            try
            {
                processFile = new Thread(this::openFile);
                processFile.start();
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void errorConexion(String s)
    {
        log.error("Error en la conexion: {}", s);
        if (processFile != null && processFile.isAlive())
        {
            processFile.interrupt();
        }
    }

    public void datosRecibidos(String s, byte[] bytes, Tcp tcp)
    {
        log.info("Mensaje recibido de {}:{} - {}", tcp.getIP(), tcp.getPuerto(), s);
    }

    public void cerrarConexion(Cliente cliente)
    {
        log.info("Se cerro la conexion con {}:{}", cliente.getIP(), cliente.getPuerto());
        if (processFile != null && processFile.isAlive())
        {
            processFile.interrupt();
        }
    }

    @Override
    public void setLogger(Marker marker)
    {

    }

    public void setConexion(Tcp conexion)
    {
        this.conexion = conexion;
    }
}
