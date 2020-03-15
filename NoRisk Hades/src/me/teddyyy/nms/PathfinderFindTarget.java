package me.teddyyy.nms;

import me.teddyyy.utils.HadesUtils;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityMonster;
import net.minecraft.server.v1_14_R1.PathfinderGoal;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.HashMap;
import java.util.Map;

public class PathfinderFindTarget extends PathfinderGoal {

    public EntityMonster monster;
    public LivingEntity safe;
    public boolean attack;

    public PathfinderFindTarget(EntityMonster monster, LivingEntity safe, boolean attack) {
        this.monster = monster;
        this.safe = safe;
        this.attack = attack;
    }

    @Override
    public boolean a() {

        Map<Double, EntityLiving> p = new HashMap<>();
        double least = 100D;

        for (Entity ent : monster.getBukkitEntity().getNearbyEntities(10, 10, 10)) {

           if (ent instanceof Player) {
                Player target = (Player) ent;

                if (target.equals(safe)) {
                    continue;
                }

                // Checks
               if (target.getGameMode() == GameMode.CREATIVE || !monster.getEntitySenses().a(HadesUtils.get(target))) {
                   continue;
               }

                p.put(ent.getLocation().distance(monster.getBukkitEntity().getLocation()), HadesUtils.get((LivingEntity) ent));
            }
        }

        for (Double d : p.keySet()) {
            if (d < least) {
                least = d;
            }
        }

        EntityLiving e = p.get(least);

        if (e == null) {
            monster.setGoalTarget(null);
            return false;
        }

        monster.setGoalTarget(e, EntityTargetEvent.TargetReason.CUSTOM, true);
        return true;
    }

    @Override
    public void e() {
        if (monster.getGoalTarget() == null) {
            return;
        }

        monster.getNavigation().a(monster.getGoalTarget().getBukkitEntity().getLocation().getX(),
                monster.getGoalTarget().getBukkitEntity().getLocation().getY() + 1.0D,
                monster.getGoalTarget().getBukkitEntity().getLocation().getZ(), 1.5F);

        if (!attack) { // We want the entity to not attack but able to move from the method above.
            return;
        }

        if (monster.getBukkitEntity().getLocation().distance(monster.getGoalTarget().getBukkitEntity().getLocation()) <= 1.5D) {
            if (monster.getEntitySenses().a(monster.getGoalTarget())) { // canSee method
                ((LivingEntity) monster.getGoalTarget().getBukkitEntity()).damage(4.0D, safe);
            }
        }

    }

    @Override
    public boolean b() {
        return false;
    }
}
