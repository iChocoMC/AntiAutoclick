package com.github.ichocomc.antiautoclick.checks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.interfaces.AutoClickCheck;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public class DelayCheck implements AutoClickCheck {

    private static long time = System.currentTimeMillis();

    private final byte minCPS;
    private final short maxDelay;

    public DelayCheck(FileConfiguration config) {
        minCPS = (byte) config.getInt("delay.cps-activate");
        maxDelay = (short) config.getInt("delay.maxDelay");
    }

    /*
     * Primero revisa si el click es izquierdo y si los cps son mayores o iguales a minCPS
     * Segundo resta el primer y segundo tiempo de click times[1 y 2] = delay en milisegundos
     *      Ejemplo: Tardaste 500ms en hacer el primer click y luego 400ms en otro
     *      Entonces 500ms - 400ms = tardaste 100ms
     *
     * Tercero revisa si el tiempo es menor a 5 | Esto para evitar falsos positivos debido al lag
     *      Si esto es verdad, el tiempo time[1] pasa a obtener el valor de time[2]
     *      y time[2] toma el valor del nuevo tiempo (System.currentTimeMillis() - time)
     *      Esto se hace porque el primer click no tiene registrado ningun tiempo
     *
     * Cuarto resta el delay con el antiguo delay, ejemplo:
     *      El delay entre este click y el anterior es de 100ms
     *      Pero el delay entre el click anterior y uno antes de este puede ser por ej: 80ms
     *      Entonces restamos 100 - 80 = 20
     *      Luego revisamos si 20 es menor a maxDelay, si es cierto es detectado como autoclick
     * 
     * Quinto se añade a times[2] el nuevo tiempo y el anterior a times[1]
     * Finalmente se añade el delay actual a times[0]
     */
    @Override
    public void check(PlayerInteractEvent event, PlayerInfo info, int clickType) {

        if (clickType != 0 || info.getByte(clickType) <= minCPS) {
            return;
        }

        int delay = info.getClickDelay();

        if (delay < 5) {
            info.addTime(System.currentTimeMillis() - time);
            return;
        }

        if (info.getResult(delay) < maxDelay) {
            info.addByte(2); // Add new report
        }
        info.addTime(System.currentTimeMillis() - time);
        info.setOldClickDelay(delay);
    }

    public static void resetTime() {
        time = System.currentTimeMillis();
    }
}