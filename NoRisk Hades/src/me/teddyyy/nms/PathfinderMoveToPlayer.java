package me.teddyyy.nms;

import net.minecraft.server.v1_14_R1.EntityMonster;
import net.minecraft.server.v1_14_R1.PathfinderGoal;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PathfinderMoveToPlayer extends PathfinderGoal {

    private Player player;
    private EntityMonster monster;

    public PathfinderMoveToPlayer(Player player, EntityMonster monster) {
        this.player = player;
        this.monster = monster;
    }

    @Override
    public boolean a() {
        return player != null && !player.isDead() && monster.getGoalTarget() == null;
    }

    // e() executes whenever a() returns true

    @Override
    public void e() {
        if (player == null || player.isDead()) {
            return;
        }

        if (monster == null) {
            return;
        }

        if (monster.getGoalTarget() != null) {
            return;
        }

        if (monster.getBukkitEntity().getLocation().distance(player.getLocation()) >= 4.0) {

            monster.getNavigation().a(player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ(), 1.5F);
            monster.getControllerLook().a(((CraftPlayer) player).getHandle(), 10.0F, 0.0F);

        } else if (monster.getBukkitEntity().getLocation().distance(player.getLocation()) >= 50.0D) {
            monster.getBukkitEntity().teleport(player.getLocation().add(0, 1, 0));
        }
    }

    @Override
    public boolean b() {
        return false;
    }
}
