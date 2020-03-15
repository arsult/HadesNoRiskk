package me.teddyyy.utils;

import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityMonster;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HadesUtils {

    public static void clearPathfinders(EntityMonster entity) {
        entity.goalSelector = new PathfinderGoalSelector(entity.getWorld().getMethodProfiler());
        entity.targetSelector = new PathfinderGoalSelector(entity.getWorld().getMethodProfiler());
    }

    public static EntityLiving get(LivingEntity entity) {
        return ((CraftLivingEntity) entity).getHandle();
    }

    public static EntityPlayer get(Player entity) {
        return ((CraftPlayer) entity).getHandle();
    }

}
