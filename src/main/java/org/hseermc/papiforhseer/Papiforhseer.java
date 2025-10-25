package org.hseermc.papiforhseer;

import java.util.concurrent.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.xinxin.BotApi.BotAction;
import com.xinxin.BotApi.BotBind;
import com.xinxin.BotEvent.GroupMessageEvent;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class Papiforhseer extends JavaPlugin {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<UUID, Long> cooldownMap = new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("插件加载中，请等待一会");
        Bukkit.getConsoleSender().sendMessage("插件加载完成");
        Bukkit.getConsoleSender().sendMessage("插件版本：1.0.0");
        Bukkit.getConsoleSender().sendMessage("作者：HseerMC");
        Bukkit.getConsoleSender().sendMessage("Hello World!");

        this.getCommand("qq").setExecutor(new CommandKit());
        this.getCommand("q").setExecutor(new Commandconsole());
        this.getCommand("chaxun").setExecutor(new onlineplayer());
    }

    public class CommandKit implements CommandExecutor {


        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 0) {
                sender.sendMessage("请输入要发送的文本。");
                return false;
            }

            Player p = (Player) sender;
            String username = p.getName();
            UUID playerUUID = p.getUniqueId();
            if (cooldownMap.containsKey(playerUUID)) {
                long cooldownTime = cooldownMap.get(playerUUID);
                long currentTime = System.currentTimeMillis();
                if (currentTime - cooldownTime < 20000) {
                    p.sendMessage("请等待20秒后再次执行命令。");
                    return false;
                }
            }
            cooldownMap.put(playerUUID, System.currentTimeMillis());

            StringBuilder message = new StringBuilder();
            StringBuilder gongping = new StringBuilder();
            message.append(p.getName()).append(":").append(String.join(" ", args));
            gongping.append("§e[QQ群聊]§b").append(p.getName()).append(":§f").append(String.join(" ", args));

            String gongpingfasong = gongping.toString();
            Bukkit.broadcastMessage(gongpingfasong);

            long group = 156188351;
            boolean fasong = true;
            executorService.submit(() -> {
                BotAction.sendGroupMessage(group, message.toString(), fasong);
                sender.sendMessage("[√]消息已发送到群聊。");
            });
            return true;
        }

    }

    public class Commandconsole implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            StringBuilder wzj = new StringBuilder();
            for (String arg : args) {
                wzj.append(arg).append(" ");
            }
            long group = 156188351;
            boolean fasong = true;
            executorService.submit(() -> {
                BotAction.sendGroupMessage(group, wzj.toString(), fasong);
                sender.sendMessage("[√]消息已发送到群聊。");
            });
            return true;
        }
    }

    public class onlineplayer implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            //if (command.getName().equalsIgnoreCase("list")) {
            executorService.submit(() -> {
                Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
                int playerCount = players.length;
                StringBuilder message = new StringBuilder();
                message.append("【在线人数】：").append(playerCount).append("\n");
                message.append("【在线列表】：\n");
                for (Player player : players) {
                    String playerId = player.getName().toString();
                    message.append(playerId).append("，");
                }
                long group = 156188351;
                boolean fasong = true;
                BotAction.sendGroupMessage(group, message.toString(), fasong);


            });
            return true;
        }

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
