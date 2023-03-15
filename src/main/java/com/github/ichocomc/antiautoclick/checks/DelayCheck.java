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
     * 1) Si los cps son mayores o iguales a minCPS sigue con el check
     * 2) Resta el primer y segundo tiempo de click times[1 y 2] = delay en milisegundos
     *      Ejemplo: Tardaste 500ms en hacer el primer click y luego 400ms en otro
     *      Entonces 500ms - 400ms = tardaste 100ms
     *
     * 3) Revisa si el tiempo es menor a 5 | Esto para evitar falsos positivos debido al lag
     *      Si esto es verdad, el tiempo time[1] pasa a obtener el valor de time[2]
     *      y time[2] toma el valor del nuevo tiempo (System.currentTimeMillis() - time)
     *      Esto se hace porque el primer click no tiene registrado ningun tiempo
     *
     * 4) Resta el delay con el antiguo delay, ejemplo:
     *      El delay entre este click y el anterior es de 100ms
     *      Pero el delay entre el click anterior y uno antes de este puede ser por ej: 80ms
     *      Entonces restamos 100 - 80 = 20
     * 5) Revisamos si 20 es menor a maxDelay, si es cierto es detectado como autoclick
     * 6) Se añade a times[2] el nuevo tiempo y el anterior a times[1]
     * 7) Finalmente se añade el delay actual a times[0]
     */
    @Override
    public void check(PlayerInteractEvent event, PlayerInfo info) {

        if (info.getByte(0) <= minCPS) {
            return;
        }

        int delay = info.getClickDelay();

        if (delay < 5) {
            info.addTime(System.currentTimeMillis() - time);
            return;
        }

        int result = info.getResult(delay);

        if (result > 0 && result < maxDelay) {
            info.addByte(2); // Add new report
        }
        info.addTime(System.currentTimeMillis() - time);
        info.setOldClickDelay(delay);
    }

    public static void resetTime() {
        time = System.currentTimeMillis();
    }
}