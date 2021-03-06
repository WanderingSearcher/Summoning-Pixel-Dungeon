/*
 *  Pixel Dungeon
 *  Copyright (C) 2012-2015 Oleg Dolya
 *
 *  Shattered Pixel Dungeon
 *  Copyright (C) 2014-2019 Evan Debenham
 *
 *  Summoning Pixel Dungeon
 *  Copyright (C) 2019-2020 TrashboxBobylev
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.summoningpixeldungeon.items.powers;

import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.ShatteredPixelDungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.DummyBuff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.FlavourBuff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Invisibility;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.Mob;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions.Minion;
import com.trashboxbobylev.summoningpixeldungeon.items.Item;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public abstract class Power extends Item {

    public static final String AC_DRINK = "DRINK";
    ArrayList<? extends Class> minionClasses;
    Minion.MinionClass featuredClass = null;

    {
        stackable = true;
        defaultAction = AC_DRINK;
        image = ItemSpriteSheet.WARRIOR_POWER;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_DRINK)){
            use();
        }
    }

    protected void use(){
        affectDungeon();
        buffPlayer();
        buffMinions();
        Invisibility.dispel();
        Dungeon.hero.spendAndNext(Actor.TICK);
        detach( Dungeon.hero.belongings.backpack );
    }

    protected boolean isRespectable(Minion minion){
        return minion.minionClass == featuredClass;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    Class<? extends FlavourBuff> playerBuff;
    float playerBuffDuration;
    protected void buffPlayer(){
        Buff.affect(Dungeon.hero, playerBuff, playerBuffDuration);
    }

    protected abstract void affectDungeon();

    protected void buffMinions(){
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob instanceof Minion && Dungeon.hero.fieldOfView[mob.pos]){
                affectMinion((Minion) mob);
            }
        }
    }

    Class<? extends FlavourBuff> basicBuff;
    Class<? extends FlavourBuff> classBuff;
    int basicBuffDuration;
    int classBuffDuration;

    protected void affectMinion(Minion minion){
        if (isRespectable(minion)){
                Buff.affect(minion, classBuff, classBuffDuration);
        } else Buff.affect(minion, basicBuff, basicBuffDuration);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public int price() {
        return 200 * quantity;
    }

    @Override
    public String info() {
        String info = desc();
        info += "\n\n" + Messages.get(this, "class_minion_buff");
        info += "\n\n" + Messages.get(Power.class, "class_members");
        info += "\n\n" + Messages.get(this, "members");
        info += "\n\n" + Messages.get(this, "other_minion_buff");
        return info;
    }
}
