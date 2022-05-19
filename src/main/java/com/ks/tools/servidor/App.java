package com.ks.tools.servidor;

import com.ks.lib.tcp.Cliente;
import com.ks.lib.tcp.Servidor;
import com.ks.lib.tcp.Tcp;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Log4j2
public class App
{
    public static void main(String[] args)
    {
        log.info("Iniciando la aplicacion\n");

        try
        {
            int port = Integer.parseInt(System.getProperty("port", "9090"));

            if (port > 0)
            {
                String file = System.getProperty("file", null);
                String type = System.getProperty("connectionType", "server");
                int time = Integer.parseInt(System.getProperty("time", "1000"));

                Comunications data = new Comunications();
                if (type.equals("client"))
                {
                    String ip = System.getProperty("ip", "localhost");
                    log.info("Tipo de conexion: Cliente");
                    data.setConexion(new Cliente());
                    data.getConexion().setIP(ip);
                    log.info("IP: " + ip);
                }
                else
                {
                    log.info("Tipo de conexion: Servidor");
                    data.setConexion(new Servidor());
                }
                log.info("Puerto: " + port);
                data.getConexion().setPuerto(port);
                data.getConexion().setEventos(data);

                if (file != null)
                {
                    if (file.length() > 0)
                    {
                        File archivo = new File(file);
                        if (archivo.exists())
                        {
                            log.info("Leer el archivo: " + file);
                            data.setFile(file);
                            data.setTime(time);
                        }
                        else
                        {
                            log.error("WARN :\tNo se encontro el archivo, la aplicacion se va a levantar sin el envio de información.");
                        }
                    }
                }
                try
                {
                    data.getConexion().conectar();
                }
                catch (Exception e)
                {
                    log.error("Problema al levantar los puertos", e);
                }
            }
            Thread.currentThread().sleep(1000);
        }
        catch (Exception ex)
        {
            log.error("Problema al iniciar la aplicacion", ex);
        }
    }
}
