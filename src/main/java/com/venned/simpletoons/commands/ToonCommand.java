package com.venned.simpletoons.commands;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.gui.CreationCharacterGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Pattern;

public class ToonCommand implements CommandExecutor, TabCompleter {

    private static final Map<String, Long> pkConfirmations = new HashMap<>();
    private static final Map<String, Long> forcePkConfirmations = new HashMap<>();
    CreationCharacterGUI creationGUI = new CreationCharacterGUI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Use: /toon <subcommand>");
            return true;
        }
        String sub = args[0].toLowerCase();
        switch (sub) {
            case "status":
                handleStatus(player, args);
                break;
            case "pk":
                handlePK(player, args);
                break;
            case "forcepk":
                handleForcePK(player, args);
                break;
            case "setname":
                handleSetName(player, args);
                break;
            case "setdesc":
            case "setbio":
                handleSetDesc(player, args);
                break;
            case "setculture":
                handleSetCulture(player, args);
                break;
            case "forceset":
                handleForceSet(player, args);
                break;
            case "revealnames":
                handleRevealNames(player, args);
                break;
            case "username":
                player.sendMessage(ChatColor.YELLOW + "Funcionalidad no implementada.");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Subcomando desconocido.");
                break;
        }
        return true;
    }

    private void handleStatus(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + "Use: /toon status <apply/remove> <status> OR /toon status <set/setremove/lock> <username> <status>");
            return;
        }
        String mode = args[1].toLowerCase();
        if (mode.equals("apply") || mode.equals("remove")) {
            Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                    .filter(ps -> ps.getPlayer().equals(player))
                    .findFirst();
            if (!psOpt.isPresent()) {
                player.sendMessage(ChatColor.RED + "No tienes un Toon activo.");
                return;
            }
            PlayerTon toon = psOpt.get().getToon();
            String status = args[2];
            if (mode.equals("apply")) {
                if (toon.getDescription() == null || toon.getDescription().isEmpty()) {
                    toon.setDescription(status);
                } else {
                    toon.setDescription(toon.getDescription() + ", " + status);
                }
                player.sendMessage(ChatColor.GREEN + "Se ha aplicado el estado: " + status);
            } else { // remove
                String newDesc = removeStatus(toon.getDescription(), status);
                toon.setDescription(newDesc);
                player.sendMessage(ChatColor.GREEN + "Se ha eliminado el estado: " + status);
            }
        } else if (mode.equals("set") || mode.equals("setremove") || mode.equals("lock")) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.RED + "Uso: /toon status " + mode + " <username> <status>");
                return;
            }
            String targetName = args[2];
            String status = args[3];
            Player target = Bukkit.getPlayerExact(targetName);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Jugador " + targetName + " no encontrado.");
                return;
            }
            Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                    .filter(ps -> ps.getPlayer().equals(target))
                    .findFirst();
            if (!psOpt.isPresent()) {
                player.sendMessage(ChatColor.RED + "El jugador " + targetName + " no tiene un Toon activo.");
                return;
            }
            PlayerTon toon = psOpt.get().getToon();
            if (mode.equals("set")) {
                toon.setDescription(status);
                player.sendMessage(ChatColor.GREEN + "Se ha establecido el estado de " + targetName + " a: " + status);
            } else if (mode.equals("setremove")) {
                toon.setDescription("");
                player.sendMessage(ChatColor.GREEN + "Se han eliminado los estados de " + targetName + ".");
            } else if (mode.equals("lock")) {
                toon.lockStatus(status);
                player.sendMessage(ChatColor.GREEN + "Se ha bloqueado el estado '" + status + "' para " + targetName + ".");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Subcomando de status desconocido.");
        }
    }

    private String removeStatus(String description, String status) {
        if (description == null) return "";
        String result = description.replaceFirst("^" + Pattern.quote(status) + "(,\\s*)?", "");
        result = result.replaceAll(",\\s*" + Pattern.quote(status), "");
        return result.trim();
    }

    private void handlePK(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.YELLOW + "¿Estás seguro que deseas eliminar tu Toon? Escribe /toon pk confirm para confirmar.");
            pkConfirmations.put(player.getName(), System.currentTimeMillis());
        } else if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
            Long time = pkConfirmations.get(player.getName());
            if (time == null || System.currentTimeMillis() - time > 30000) {
                player.sendMessage(ChatColor.RED + "Confirmación caducada. Vuelve a intentarlo.");
                pkConfirmations.remove(player.getName());
                return;
            }
            Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                    .filter(ps -> ps.getPlayer().equals(player))
                    .findFirst();
            if (!psOpt.isPresent()) {
                player.sendMessage(ChatColor.RED + "No tienes un Toon activo.");
                return;
            }
            PlayerTon toon = psOpt.get().getToon();
            Main.getInstance().getTonManager().getPlayerTons().remove(toon);
            Main.getInstance().getPlayerSelectSet().remove(psOpt.get());
            player.sendMessage(ChatColor.GREEN + "Tu Toon ha sido eliminado.");
            pkConfirmations.remove(player.getName());
        } else {
            player.sendMessage(ChatColor.RED + "Uso: /toon pk OR /toon pk confirm");
        }
    }

    private void handleForcePK(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /toon forcepk <username> OR /toon forcepk <username> confirm");
            return;
        }
        String targetName = args[1];
        Player target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Jugador " + targetName + " no encontrado.");
            return;
        }
        if (args.length == 2) {
            player.sendMessage(ChatColor.YELLOW + "¿Estás seguro que deseas forzar la eliminación del Toon de " + targetName + "? Escribe /toon forcepk " + targetName + " confirm para confirmar.");
            forcePkConfirmations.put(player.getName() + "_" + targetName, System.currentTimeMillis());
        } else if (args.length == 3 && args[2].equalsIgnoreCase("confirm")) {
            String key = player.getName() + "_" + targetName;
            Long time = forcePkConfirmations.get(key);
            if (time == null || System.currentTimeMillis() - time > 30000) {
                player.sendMessage(ChatColor.RED + "Confirmación caducada. Vuelve a intentarlo.");
                forcePkConfirmations.remove(key);
                return;
            }
            Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                    .filter(ps -> ps.getPlayer().equals(target))
                    .findFirst();
            if (!psOpt.isPresent()) {
                player.sendMessage(ChatColor.RED + "El jugador " + targetName + " no tiene un Toon activo.");
                return;
            }
            PlayerTon toon = psOpt.get().getToon();
            Main.getInstance().getTonManager().getPlayerTons().remove(toon);
            Main.getInstance().getPlayerSelectSet().remove(psOpt.get());
            player.sendMessage(ChatColor.GREEN + "Has eliminado el Toon de " + targetName + ".");
            target.sendMessage(ChatColor.RED + "Tu Toon ha sido eliminado por un administrador.");
            forcePkConfirmations.remove(key);
        } else {
            player.sendMessage(ChatColor.RED + "Uso: /toon forcepk <username> OR /toon forcepk <username> confirm");
        }
    }

    private void handleSetName(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /toon setname <nuevo_nombre>");
            return;
        }
        String newName = args[1];
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent()) {
            player.sendMessage(ChatColor.RED + "No tienes un Toon activo.");
            return;
        }
        PlayerTon toon = psOpt.get().getToon();
        toon.setName(newName);
        player.setDisplayName(newName);
        player.setPlayerListName(newName);
        player.sendMessage(ChatColor.GREEN + "Tu Toon ahora se llama: " + newName);
    }

    private void handleSetDesc(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /toon setdesc <nueva_descripción>");
            return;
        }
        String newDesc = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent()) {
            player.sendMessage(ChatColor.RED + "No tienes un Toon activo.");
            return;
        }
        PlayerTon toon = psOpt.get().getToon();
        toon.setDescription(newDesc);
        player.sendMessage(ChatColor.GREEN + "Tu descripción ha sido actualizada.");
    }

    private void handleSetCulture(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /toon setculture <nueva_cultura>");
            return;
        }
        String culture = args[1];

        if (!creationGUI.culturesList.contains(culture)) {
            player.sendMessage(ChatColor.RED + "Cultura inválida. Las culturas válidas son: " + creationGUI.culturesList);
            return;
        }

        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent()) {
            player.sendMessage(ChatColor.RED + "No tienes un Toon activo.");
            return;
        }
        PlayerTon toon = psOpt.get().getToon();
        toon.setCulture(culture);
        player.sendMessage(ChatColor.GREEN + "Tu cultura ha sido actualizada a: " + culture);
    }

    private void handleForceSet(Player player, String[] args) {
        if (args.length < 4) {
            player.sendMessage(ChatColor.RED + "Uso: /toon forceset <atributo> <username> <valor>");
            return;
        }
        String attribute = args[1].toLowerCase();
        String targetName = args[2];
        String value = args[3];
        Player target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Jugador " + targetName + " no encontrado.");
            return;
        }
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(target))
                .findFirst();
        if (!psOpt.isPresent()) {
            player.sendMessage(ChatColor.RED + "El jugador " + targetName + " no tiene un Toon activo.");
            return;
        }
        PlayerTon toon = psOpt.get().getToon();
        switch (attribute) {
            case "name":
                toon.setName(value);
                target.setCustomName(value);
                target.setCustomNameVisible(true);
                player.sendMessage(ChatColor.GREEN + "Nombre del Toon de " + targetName + " cambiado a: " + value);
                break;
            case "desc":
            case "bio":
                toon.setDescription(value);
                player.sendMessage(ChatColor.GREEN + "Descripción del Toon de " + targetName + " actualizada.");
                break;
            case "culture":
                if (!creationGUI.culturesList.contains(value)) {
                    player.sendMessage(ChatColor.RED + "Cultura inválida. Las culturas válidas son: " + creationGUI.culturesList);
                    break;
                }
                toon.setCulture(value);
                player.sendMessage(ChatColor.GREEN + "Cultura del Toon de " + targetName + " cambiada a: " + value);
                break;
            case "age":
                try {
                    int age = Integer.parseInt(value);
                    toon.setAge(age);
                    player.sendMessage(ChatColor.GREEN + "Edad del Toon de " + targetName + " cambiada a: " + age);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "La edad debe ser un número válido.");
                }
                break;
            case "gender":
                if (!creationGUI.genderList.contains(value)) {
                    player.sendMessage(ChatColor.RED + "Género inválido. Los géneros válidos son: " + creationGUI.genderList);
                    break;
                }
                toon.setGender(value);
                player.sendMessage(ChatColor.GREEN + "Género del Toon de " + targetName + " cambiado a: " + value);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Atributo inválido. Usa: name, desc, culture, age, gender.");
                break;
        }
    }

    private void handleRevealNames(Player player, String[] args) {
        if (!player.hasPermission("simpletoons.revealnames")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to reveal real names.");
        }
        player.sendMessage(ChatColor.YELLOW + "Real Names Mapping:");
        for (PlayerSelect ps : Main.getInstance().getPlayerSelectSet()) {
            String toonName = ps.getToon().getName();
            String realName = ps.getPlayer().getName();
            player.sendMessage(ChatColor.YELLOW + "Toon: " + toonName + " | Player: " + realName);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return completions;
        }

        if (args.length == 1) {
            String[] subcommands = {"status", "pk", "forcepk", "setname", "setdesc", "setculture", "forceset", "revealnames", "username"};
            for (String sub : subcommands) {
                if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(sub);
                }
            }
            return completions;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("status")) {
                String[] statusSubs = {"apply", "remove", "set", "setremove", "lock"};
                for (String s : statusSubs) {
                    if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(s);
                    }
                }
            } else if (args[0].equalsIgnoreCase("forceset")) {
                String[] attributes = {"name", "desc", "culture", "age", "gender"};
                for (String s : attributes) {
                    if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(s);
                    }
                }
            }
            return completions;
        }

        if (args.length == 3) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                    completions.add(p.getName());
                }
            }
            return completions;
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("forceset")) {
                String attribute = args[1].toLowerCase();
                if (attribute.equals("age")) {
                    completions.add("<number>");
                }
            }
            return completions;
        }

        return completions;
    }
}
