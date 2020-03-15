package me.teddyyy.listener;

import me.teddyyy.nms.ModifiedPathfinderGoalBowShoot;
import me.teddyyy.nms.ModifiedPathfinderGoalSwell;
import me.teddyyy.nms.PathfinderFindTarget;
import me.teddyyy.nms.PathfinderMoveToPlayer;
import me.teddyyy.utils.HadesUtils;
import net.minecraft.server.v1_14_R1.EntityCreeper;
import net.minecraft.server.v1_14_R1.EntityMonster;
import net.minecraft.server.v1_14_R1.EntitySkeleton;
import net.minecraft.server.v1_14_R1.PathfinderGoalFloat;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftMonster;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HadesListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {

        Player player = e.getPlayer();

        if (player.getItemInHand().getType() != Material.BONE) {
            return;
        }

        if (e.getRightClicked() instanceof Monster) {

            Monster monster = (Monster) e.getRightClicked();
            EntityMonster craftMonster = ((CraftMonster) monster).getHandle();
            boolean attack = monster.getType() != EntityType.CREEPER && monster.getType() != EntityType.SKELETON;

            // Clear pathfinders to apply ours.
            HadesUtils.clearPathfinders(craftMonster);
            craftMonster.goalSelector.a(0, new PathfinderMoveToPlayer(player, craftMonster));
            craftMonster.goalSelector.a(1, new PathfinderGoalFloat(craftMonster));
            craftMonster.goalSelector.a(2, new PathfinderFindTarget(craftMonster, player, attack));

            switch (monster.getType()) {
                case SKELETON:
                    craftMonster.goalSelector.a(3, new ModifiedPathfinderGoalBowShoot(player, ((EntitySkeleton) craftMonster), 1.0D, 20, 15.0F));
                    break;
                case CREEPER:
                    craftMonster.goalSelector.a(3, new ModifiedPathfinderGoalSwell(((EntityCreeper) craftMonster)));
                    break;
                default:
                    break;
            }
        }
    }
}
