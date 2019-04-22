package com.avarioncraft.Clans.gui;

import org.bukkit.entity.Player;

import com.avarioncraft.Clans.data.Clan;
import com.avarioncraft.Clans.gui.main.BankGUI;
import com.avarioncraft.Clans.gui.main.ClanListGUI;
import com.avarioncraft.Clans.gui.main.ClanMemberListGUI;
import com.avarioncraft.Clans.gui.main.ClanMessageManagerGUI;
import com.avarioncraft.Clans.gui.main.ClanRequestGUI;
import com.avarioncraft.Clans.gui.main.ClanStyleGUI;
import com.avarioncraft.Clans.gui.main.ClanUpgradesGUI;
import com.avarioncraft.Clans.gui.main.HasClanGUI;
import com.avarioncraft.Clans.gui.main.InvitationGUI;
import com.avarioncraft.Clans.gui.main.LackClanGUI;
import com.avarioncraft.Clans.gui.main.MemberManagerGUI;
import com.avarioncraft.Clans.gui.main.MessageGUI;
import com.avarioncraft.Clans.gui.owner.ClanOwnerMainMenu;
import com.avarioncraft.Clans.gui.owner.ClanOwnerNoHome;
import com.avarioncraft.Clans.gui.owner.ClanOwnerRankGUI;
import com.avarioncraft.Clans.gui.owner.ClanRegionFlagsGUI;
import com.avarioncraft.Clans.gui.owner.RankEditGUI;
import com.avarioncraft.Clans.gui.subgui.RankSelectGUI;
import com.avarioncraft.Clans.gui.subgui.WarehouseOverview;

import net.crytec.api.smartInv.SmartInventory;
import net.crytec.api.util.F;

public class GUIs {
	public static void openMain(Player player) {
		if (!Clan.ofMember(player).isPresent()) {
			GUIs.LackClanGUI.open(player);
		} else {
			Clan clan = Clan.ofMember(player).get();

			if (player.getUniqueId().equals(clan.getOwner())) {				
				if (clan.hasHomeLocation()) {
					GUIs.OWNER_MAIN_MENU.open(player);
				} else {
					GUIs.OWNER_CREATE_MENU.open(player);
				}
			} else {
				player.sendMessage(F.error("Member GUI wurde noch nicht implementiert."));
			}
		}
	}
	
	public static final SmartInventory HasClanGUI = SmartInventory.builder().provider(new HasClanGUI()).title("Clan Menü").size(3, 9).build();
	public static final SmartInventory LackClanGUI = SmartInventory.builder().provider(new LackClanGUI()).title("Clan Menü").size(3, 9).build();
	
	public static final SmartInventory ClanMessages = SmartInventory.builder().provider(new MessageGUI()).title("Clan Nachrichten").size(5, 9).build();
	
	public static final SmartInventory ClanBankGUI = SmartInventory.builder().provider(new BankGUI()).title("Clan Bank").size(5, 9).build();
	
	public static final SmartInventory ClanUpgradeGUI = SmartInventory.builder().provider(new ClanUpgradesGUI()).title("Clan Upgrades").size(6, 9).build();
	
	public static final SmartInventory ClanMessageManager = SmartInventory.builder().provider(new ClanMessageManagerGUI()).title("Clan Nachrichten").size(5, 9).build();
	
	public static final SmartInventory MemberList = SmartInventory.builder().provider(new ClanMemberListGUI()).title("Mitglieder").size(5, 9).build();
	public static final SmartInventory MemberGUI = SmartInventory.builder().provider(new ClanOwnerRankGUI()).title("Clan Ränge").size(3, 9).parent(MemberList).build();
	public static final SmartInventory MemberManageGUI = SmartInventory.builder().provider(new MemberManagerGUI()).title("Mitglied verwalten").size(3, 9).parent(MemberList).build();
	
	public static final SmartInventory Invitation = SmartInventory.builder().provider(new InvitationGUI()).title("Einladungen/Privatsphäre").size(5, 9).build();
	
	public static final SmartInventory ClanRequestsGUI = SmartInventory.builder().provider(new ClanRequestGUI()).title("Anfragen").size(5, 9).parent(Invitation).build();
	
	public static final SmartInventory ClanListGUI = SmartInventory.builder().provider(new ClanListGUI()).title("Alle Clans").size(6, 9).parent(LackClanGUI).build();
	
	public static final SmartInventory ClanStyleGUI = SmartInventory.builder().provider(new ClanStyleGUI()).title("Clan Style").size(6, 9).build();
	
	public static final SmartInventory ClanRankGUI = SmartInventory.builder().provider(new ClanOwnerRankGUI()).title("Clan Ränge").size(5, 9).build();
	
	public static final SmartInventory ClanWarehouseOverview = SmartInventory.builder().provider(new WarehouseOverview()).title("Warehouse").size(5, 9).build();
	
	public static final SmartInventory ClanRankEditGUI = SmartInventory.builder().provider(new RankEditGUI()).title("Rang bearbeiten").parent(ClanRankGUI).size(6, 9).build();
	
	public static final SmartInventory OWNER_MAIN_MENU = SmartInventory.builder().provider(new ClanOwnerMainMenu()).title("Deine Clanverwaltung").size(6, 9).build();
	public static final SmartInventory OWNER_CREATE_MENU = SmartInventory.builder().provider(new ClanOwnerNoHome()).title("Erstelle deine Clanbasis").size(3, 9).build();	
	public static final SmartInventory OWNER_FLAG_SETTINGS = SmartInventory.builder().provider(new ClanRegionFlagsGUI()).size(4, 9).parent(OWNER_MAIN_MENU).build();
	
	
	
	// Sub GUIS
	
	public static final SmartInventory Sub_RankSelect = SmartInventory.builder().provider(new RankSelectGUI()).size(3, 9).title("§2Rang auswählen").parent(MemberGUI).build();
	
	
}