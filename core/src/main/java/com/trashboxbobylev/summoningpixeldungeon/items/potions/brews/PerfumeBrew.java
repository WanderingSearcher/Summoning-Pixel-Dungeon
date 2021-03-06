/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Summoning Pixel Dungeon
 * Copyright (C) 2019-2020 TrashboxBobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.summoningpixeldungeon.items.potions.brews;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.blobs.Blizzard;
import com.trashboxbobylev.summoningpixeldungeon.actors.blobs.Blob;
import com.trashboxbobylev.summoningpixeldungeon.actors.blobs.PerfumeGas;
import com.trashboxbobylev.summoningpixeldungeon.items.potions.AlchemicalCatalyst;
import com.trashboxbobylev.summoningpixeldungeon.items.potions.PotionOfFrost;
import com.trashboxbobylev.summoningpixeldungeon.items.scrolls.exotic.ScrollOfAffection;
import com.trashboxbobylev.summoningpixeldungeon.items.stones.StoneOfTargeting;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class PerfumeBrew extends Brew {
	
	{
		image = ItemSpriteSheet.BREW_PERFUME;
	}
	
	@Override
	public void shatter(int cell) {
		if (Dungeon.level.heroFOV[cell]) {
			splash( cell );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}
		
		GameScene.add( Blob.seed( cell, 750, PerfumeGas.class ) );
	}
	
	@Override
	public int price() {
		//prices of ingredients
		return quantity * (60 + 40 + 10);
	}
	
	public static class Recipe extends com.trashboxbobylev.summoningpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ScrollOfAffection.class, StoneOfTargeting.class, AlchemicalCatalyst.class};
			inQuantity = new int[]{1, 1, 1};
			
			cost = 16;
			
			output = PerfumeBrew.class;
			outQuantity = 1;
		}
		
	}
}
