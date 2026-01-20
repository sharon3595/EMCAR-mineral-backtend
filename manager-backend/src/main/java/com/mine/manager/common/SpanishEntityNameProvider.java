package com.mine.manager.common;

import com.mine.manager.util.Entities;
import java.util.HashMap;
import java.util.Map;

/**
 * Proveedor de nombres de entidades en español para mensajes de error y logs.
 */
public class SpanishEntityNameProvider {

    private static final Map<String, String> NAME_MAP = new HashMap<>();

    static {
        // Entidades del dominio Minero
        NAME_MAP.put("Supplier", Entities.SUPPLIER);
        NAME_MAP.put("Lot", Entities.LOT);
        NAME_MAP.put("Mine", Entities.MINE);
        NAME_MAP.put("Cooperative", Entities.COOPERATIVE);
        NAME_MAP.put("Company", Entities.COMPANY);
        NAME_MAP.put("Mineral", Entities.MINERAL);
        NAME_MAP.put("TypeMineral", Entities.MINERAL_TYPE);
        NAME_MAP.put("Load", Entities.LOAD);
        NAME_MAP.put("Advance", Entities.ADVANCE);
        NAME_MAP.put("Liquidation", Entities.LIQUIDATION);

        NAME_MAP.put("User", Entities.USER);
        NAME_MAP.put("Role", "Rol");
    }

    /**
     * Traduce el nombre simple de la clase (en inglés) al nombre legible en español.
     * @param englishName Nombre de la clase
     * @return Nombre en español
     */
    public static String getSpanishName(String englishName) {
        return NAME_MAP.getOrDefault(englishName, englishName);
    }
}