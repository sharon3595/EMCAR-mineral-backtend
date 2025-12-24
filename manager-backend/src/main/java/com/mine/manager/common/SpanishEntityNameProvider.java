package com.mine.manager.common;

import com.mine.manager.util.Entities; // Importa tu nueva clase de constantes
import java.util.HashMap;
import java.util.Map;

/**
 * Proveedor de nombres de entidades en español para mensajes de error y logs.
 */
public class SpanishEntityNameProvider {

    private static final Map<String, String> NAME_MAP = new HashMap<>();

    static {
        // Entidades del dominio Minero
        NAME_MAP.put("Supplier", Entities.SUPPLIERS);
        NAME_MAP.put("Batch", Entities.BATCHES);
        NAME_MAP.put("Mine", Entities.MINES);
        NAME_MAP.put("Cooperative", Entities.COOPERATIVES);
        NAME_MAP.put("Company", Entities.COMPANY);
        NAME_MAP.put("Material", Entities.MATERIAL);
        NAME_MAP.put("MaterialType", Entities.MATERIAL_TYPE);
        NAME_MAP.put("Load", Entities.LOADS);
        NAME_MAP.put("Advance", Entities.ADVANCES);
        NAME_MAP.put("Liquidation", Entities.LIQUIDATIONS);

        NAME_MAP.put("User", Entities.USERS);
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