package gregtech.loaders.postload;

import net.minecraft.item.Item;

import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTMod;
import gregtech.common.config.Other;

public class GTPostLoad implements Runnable {

    @Override
    public void run() {
        processToolboxBans();
    }

    public static void processToolboxBans() {
        final ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

        for (final String name : Other.toolboxBans) {
            final String[] nameParts = name.split(":");

            if (nameParts.length == 2) {
                final Item item = GameRegistry.findItem(nameParts[0], nameParts[1]);
                if (item != null) {
                    builder.add(item);
                }
            }
        }

        GTMod.proxy.toolboxBans = builder.build();
    }
}
