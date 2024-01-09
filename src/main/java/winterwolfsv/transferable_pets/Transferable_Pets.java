package winterwolfsv.transferable_pets;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Transferable_Pets extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player targetPlayer)) return;
        Player player = event.getPlayer();

        if (!(player.isSneaking())) return;

        for (Entity localEntity : player.getNearbyEntities(12, 12, 12)) {
            if (!(localEntity instanceof Animals animalEntity)) continue;
            if (!(animalEntity.isLeashed() && animalEntity.getLeashHolder() == player)) continue;
            if (!(animalEntity instanceof Tameable pet && pet.getOwner() == player)) continue;

            pet.setOwner(targetPlayer);
            pet.setLeashHolder(targetPlayer);
            pet.playEffect(EntityEffect.WOLF_HEARTS);
            player.sendActionBar(targetPlayer.getName() + " is now the owner of " + pet.getName());
            targetPlayer.sendActionBar(player.getName() + " has transferred " + pet.getName() + " to you");

        }
    }

}
