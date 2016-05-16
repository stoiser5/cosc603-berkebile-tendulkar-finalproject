/**
 *  Copyright (C) 2002-2016  The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.common.model;

import java.awt.Color;
import java.util.Iterator;

import net.sf.freecol.common.i18n.Messages;
import net.sf.freecol.common.model.Player.NoClaimReason;
import net.sf.freecol.common.model.Player.PlayerType;
import net.sf.freecol.server.model.ServerGame;
import net.sf.freecol.server.model.ServerPlayer;
import net.sf.freecol.server.model.ServerUnit;
import net.sf.freecol.util.test.FreeColTestCase;


public class PlayerTest extends FreeColTestCase {
    
    private static final UnitType freeColonist
        = spec().getUnitType("model.unit.freeColonist");
    private static final UnitType galleonType
        = spec().getUnitType("model.unit.galleon");
    
    @SuppressWarnings("null")
	public void testGetLandPrice(){
    	Specification specification = spec("freecol");
    	Game game = getStandardGame();
    	
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Tile tile = new Tile(game, specification.getId());
        int price = dutch.getLandPrice(tile);
        assertEquals(0, price);
        
        Specification specification1 = spec("freecol");
    	Game game1 = getStandardGame();
        Player dutchREF = game1.getPlayerByNationId("model.nation.dutchREF");
        Tile tile1 = new Tile(game1, specification1.getId());
        
        int price1 = dutchREF.getLandPrice(tile1);
        assertEquals(0, price1);
        
    }
    
    public void testcheckDeclareIndependence(){
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutchREF");
        StringTemplate template = dutch.checkDeclareIndependence();
        assertEquals(StringTemplate.template("model.player.colonialIndependence"),
        		template);
    	
    }
    
    public void testGetNationColor(){
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Color color = dutch.getNationColor();
        assertEquals(game.getSpecification().getNation("model.nation.dutch").getColor(), color);
        
    }
    
    public void testIsPotentialEnemy(){
    	
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Player dutchREF_p1 = game.getPlayerByNationId("model.nation.dutchREF");
        Player dutchREF_p2 = game.getPlayerByNationId("model.nation.dutchREF");
        
        dutch.setStance(dutchREF_p1, Stance.CEASE_FIRE);
        assertTrue(dutch.isPotentialEnemy(dutchREF_p1));

        dutch.setStance(dutchREF_p1, Stance.PEACE);
        assertTrue(dutch.isPotentialEnemy(dutchREF_p2));

        dutchREF_p2.setStance(dutch, Stance.CEASE_FIRE);
        assertFalse(dutchREF_p2.isPotentialEnemy(dutch));
    }
    
    public void testIsPotentialFriend(){
    	
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Player dutchREF_p1 = game.getPlayerByNationId("model.nation.dutchREF");
        Player dutchREF_p2 = game.getPlayerByNationId("model.nation.dutchREF");
        
        //dutchREF_p1.setStance(dutch, Stance.WAR);
        dutch.setStance(dutchREF_p1, Stance.WAR);
        assertTrue(dutch.isPotentialFriend(dutchREF_p1));

        dutchREF_p1.setStance(dutch, Stance.CEASE_FIRE);
        assertTrue(dutch.isPotentialFriend(dutchREF_p1));

        dutchREF_p2.setStance(dutch, Stance.WAR);
        assertFalse(dutchREF_p2.isPotentialFriend(dutch));
    }
    
    
    public void testGetNationLabel(){
    	
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        
        StringTemplate template = new StringTemplate();
        template = dutch.getNationLabel();
        assertEquals(StringTemplate.key(Messages.nameKey(dutch.getNationId())), template);
       
        
    }
    
    public void testGetCountryLabel(){
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        StringTemplate template = new StringTemplate();
        template = dutch.getCountryLabel();
        assertEquals(StringTemplate.template("countryName")
                .addStringTemplate("%nation%", dutch.getNationLabel()), template);
    	
    }
    
    public void testGetForcesLabel(){
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        StringTemplate template = dutch.getForcesLabel();
        assertEquals(StringTemplate.template("model.player.forces")
            .addStringTemplate("%nation%", dutch.getNationLabel()),
            template);
    }
    
    public void testGetWaitingLabel(){
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        StringTemplate template = dutch.getWaitingLabel();
        assertEquals(StringTemplate.template("model.player.waitingFor")
                .addStringTemplate("%nation%", dutch.getNationLabel()),
            template);
    	
    	
    }
    public void testGetMarketName(){
    	Game game = getStandardGame();
        Player inca = game.getPlayerByNationId("model.nation.inca");
        StringTemplate template = inca.getMarketName();
        assertEquals(StringTemplate.key("model.player.independentMarket"), template);
   
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        StringTemplate template1 = dutch.getMarketName();
        assertEquals(StringTemplate.key(dutch.getEuropeNameKey()), template1);
    
    }
    public void testGetRank(){
    	
    	Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        int rank = dutch.getRank();
    	assertEquals(1,rank);
    	
    	Player french = game.getPlayerByNationId("model.nation.french");
    	french.setAI(true);
    	int rank1 = french.getRank();
    	assertEquals(3,rank1);
    	
    	Player inca = game.getPlayerByNationId("model.nation.inca");
    	int rank2 = inca.getRank();
    	assertEquals(2,rank2);
    	
    } 
      public void testModifyGold(){
    	  
    	  Game game = getStandardGame();
          Player dutch = game.getPlayerByNationId("model.nation.dutch");
          int gold = dutch.modifyGold(0);
          assertEquals(0,gold);
          
          int newGold = 5;
          dutch.setGold(newGold);
          int gold1 = dutch.modifyGold(newGold);
          assertEquals(10,gold1);
          
    	  
      }
      
      public void testgetTotalImmigrationProduction(){
    	  
    	  Game game = getStandardGame();
          Player dutch = game.getPlayerByNationId("model.nation.dutch");
          int prod = dutch.getTotalImmigrationProduction();
          assertEquals(2, prod);
          
          Player tupi = game.getPlayerByNationId("model.nation.tupi");
          int prod1 = tupi.getTotalImmigrationProduction();
          assertEquals(0, prod1);
      }
      public void testCanOwnTile(){
    	  Specification specification = spec("freecol");
    	  Game game = getStandardGame();
          Player dutch = game.getPlayerByNationId("model.nation.dutch");
         
          Tile tile = new Tile(game, specification.getId());
         
          boolean canOwn = dutch.canOwnTile(tile);
          assertTrue(canOwn);
    	  
      }
      
    
       public void testSetNation() {
        Specification specification = spec("freecol");
        Game game = getStandardGame();
        NationOptions nationOptions = new NationOptions(specification);
        for (Nation nation : specification.getEuropeanNations()) {
            nationOptions.setNationState(nation, NationOptions.NationState.AVAILABLE);
        }
        game.setNationOptions(nationOptions);

        specification.applyDifficultyLevel("model.difficulty.medium");

        Player player = game.getPlayerByNationId("model.nation.english");
        
        Nation newNation = specification.getNation("model.nation.dutch");
        player.setNation(newNation);
		
		assertEquals(player.getNationId(), "model.nation.dutch");
                
    }

    public void testUnits() {
        Game game = getStandardGame();
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Player french = game.getPlayerByNationId("model.nation.french");
        Map map = getTestMap(spec().getTileType("model.tile.plains"));
        game.setMap(map);
        map.getTile(4, 7).setExplored(dutch, true);
        map.getTile(4, 8).setExplored(dutch, true);
        map.getTile(5, 7).setExplored(dutch, true);
        map.getTile(5, 8).setExplored(dutch, true);

        UnitType freeColonist = spec().getUnitType("model.unit.freeColonist");

        Unit unit1 = new ServerUnit(game, map.getTile(4, 7), dutch,
                                    freeColonist);
        Unit unit2 = new ServerUnit(game, map.getTile(4, 8), dutch,
                                    freeColonist);
        Unit unit3 = new ServerUnit(game, map.getTile(5, 7), dutch,
                                    freeColonist);
        Unit unit4 = new ServerUnit(game, map.getTile(5, 8), dutch,
                                    freeColonist);

        int count = 0;
        Iterator<Unit> unitIterator = dutch.getUnitIterator();
        while (unitIterator.hasNext()) {
            unitIterator.next();
            count++;
        }
        assertTrue(count == 4);

        unit1.dispose();
        assertFalse(dutch.hasUnit(unit1));

        unit2.changeOwner(french);
        assertFalse(dutch.hasUnit(unit2));
        assertTrue(french.hasUnit(unit2));
    }

    public void testEuropeanPlayer(Player player) {
        assertTrue(player.canBuildColonies());
        assertTrue(player.canHaveFoundingFathers());
        assertTrue(player.canMoveToEurope());
        assertTrue(player.isColonial());
        assertFalse(player.isDead());
        assertTrue(player.isEuropean());
        assertFalse(player.isIndian());
        assertFalse(player.isREF());
        assertEquals(2, player.getMaximumFoodConsumption());
    }

    public void testIndianPlayer(Player player) {
        assertFalse(player.canBuildColonies());
        assertFalse(player.canHaveFoundingFathers());
        assertFalse(player.canMoveToEurope());
        assertFalse(player.isColonial());
        assertFalse(player.isDead());
        assertFalse(player.isEuropean());
        assertTrue(player.isIndian());
        assertFalse(player.isREF());
        assertEquals(2, player.getMaximumFoodConsumption());
    }

    public void testRoyalPlayer(Player player) {
        assertFalse(player.canBuildColonies());
        assertFalse(player.canHaveFoundingFathers());
        assertTrue(player.canMoveToEurope());
        assertFalse(player.isColonial());
        assertEquals(player.getPlayerType(), Player.PlayerType.ROYAL);
        assertFalse(player.isDead());
        assertTrue(player.isEuropean());
        assertFalse(player.isIndian());
        assertTrue(player.isREF());
        assertEquals(2, player.getMaximumFoodConsumption());
    }

    public void testClassicPlayers() {
        Game game = getStandardGame("classic");

        // europeans
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Player french = game.getPlayerByNationId("model.nation.french");
        Player english = game.getPlayerByNationId("model.nation.english");
        Player spanish = game.getPlayerByNationId("model.nation.spanish");

        testEuropeanPlayer(dutch);
        testEuropeanPlayer(french);
        testEuropeanPlayer(english);
        testEuropeanPlayer(spanish);

        // indians
        Player inca = game.getPlayerByNationId("model.nation.inca");
        Player aztec = game.getPlayerByNationId("model.nation.aztec");
        Player arawak = game.getPlayerByNationId("model.nation.arawak");
        Player cherokee = game.getPlayerByNationId("model.nation.cherokee");
        Player iroquois = game.getPlayerByNationId("model.nation.iroquois");
        Player sioux = game.getPlayerByNationId("model.nation.sioux");
        Player apache = game.getPlayerByNationId("model.nation.apache");
        Player tupi = game.getPlayerByNationId("model.nation.tupi");
        testIndianPlayer(inca);
        testIndianPlayer(aztec);
        testIndianPlayer(arawak);
        testIndianPlayer(cherokee);
        testIndianPlayer(iroquois);
        testIndianPlayer(sioux);
        testIndianPlayer(apache);
        testIndianPlayer(tupi);

        // royal
        Player dutchREF = game.getPlayerByNationId("model.nation.dutchREF");
        Player frenchREF = game.getPlayerByNationId("model.nation.frenchREF");
        Player englishREF = game.getPlayerByNationId("model.nation.englishREF");
        Player spanishREF = game.getPlayerByNationId("model.nation.spanishREF");
        testRoyalPlayer(dutchREF);
        testRoyalPlayer(frenchREF);
        testRoyalPlayer(englishREF);
        testRoyalPlayer(spanishREF);
        assertEquals(dutchREF, dutch.getREFPlayer());
        assertEquals(frenchREF, french.getREFPlayer());
        assertEquals(englishREF, english.getREFPlayer());
        assertEquals(spanishREF, spanish.getREFPlayer());

    }

    public void testFreecolPlayers() {
        // the initialization code is basically the same as in
        // getStandardGame(), except that all European nations are
        // available
        Specification specification = spec("freecol");
        Game game = new ServerGame(specification);
        NationOptions nationOptions = new NationOptions(specification);
        for (Nation nation : specification.getEuropeanNations()) {
            nationOptions.setNationState(nation, NationOptions.NationState.AVAILABLE);
        }
        game.setNationOptions(nationOptions);

        specification.applyDifficultyLevel("model.difficulty.medium");
        for (Nation n : specification.getNations()) {
            if (n.isUnknownEnemy()) continue;
            Player p = new ServerPlayer(game, false, n, null, null);
            p.setAI(!n.getType().isEuropean() || n.getType().isREF());
            game.addPlayer(p);
        }

        // europeans
        Player dutch = game.getPlayerByNationId("model.nation.dutch");
        Player french = game.getPlayerByNationId("model.nation.french");
        Player english = game.getPlayerByNationId("model.nation.english");
        Player spanish = game.getPlayerByNationId("model.nation.spanish");
        Player portuguese = game.getPlayerByNationId("model.nation.portuguese");
        Player swedish = game.getPlayerByNationId("model.nation.swedish");
        Player danish = game.getPlayerByNationId("model.nation.danish");
        Player russian = game.getPlayerByNationId("model.nation.russian");

        testEuropeanPlayer(dutch);
        testEuropeanPlayer(french);
        testEuropeanPlayer(english);
        testEuropeanPlayer(spanish);
        testEuropeanPlayer(portuguese);
        testEuropeanPlayer(swedish);
        testEuropeanPlayer(danish);
        testEuropeanPlayer(russian);

        // indians
        Player inca = game.getPlayerByNationId("model.nation.inca");
        Player aztec = game.getPlayerByNationId("model.nation.aztec");
        Player arawak = game.getPlayerByNationId("model.nation.arawak");
        Player cherokee = game.getPlayerByNationId("model.nation.cherokee");
        Player iroquois = game.getPlayerByNationId("model.nation.iroquois");
        Player sioux = game.getPlayerByNationId("model.nation.sioux");
        Player apache = game.getPlayerByNationId("model.nation.apache");
        Player tupi = game.getPlayerByNationId("model.nation.tupi");
        testIndianPlayer(inca);
        testIndianPlayer(aztec);
        testIndianPlayer(arawak);
        testIndianPlayer(cherokee);
        testIndianPlayer(iroquois);
        testIndianPlayer(sioux);
        testIndianPlayer(apache);
        testIndianPlayer(tupi);

        // royal
        Player dutchREF = game.getPlayerByNationId("model.nation.dutchREF");
        Player frenchREF = game.getPlayerByNationId("model.nation.frenchREF");
        Player englishREF = game.getPlayerByNationId("model.nation.englishREF");
        Player spanishREF = game.getPlayerByNationId("model.nation.spanishREF");
        Player portugueseREF = game.getPlayerByNationId("model.nation.portugueseREF");
        Player swedishREF = game.getPlayerByNationId("model.nation.swedishREF");
        Player danishREF = game.getPlayerByNationId("model.nation.danishREF");
        Player russianREF = game.getPlayerByNationId("model.nation.russianREF");
        testRoyalPlayer(dutchREF);
        testRoyalPlayer(frenchREF);
        testRoyalPlayer(englishREF);
        testRoyalPlayer(spanishREF);
        testRoyalPlayer(portugueseREF);
        testRoyalPlayer(swedishREF);
        testRoyalPlayer(danishREF);
        testRoyalPlayer(russianREF);
        assertEquals(dutchREF, dutch.getREFPlayer());
        assertEquals(frenchREF, french.getREFPlayer());
        assertEquals(englishREF, english.getREFPlayer());
        assertEquals(spanishREF, spanish.getREFPlayer());
        assertEquals(portugueseREF, portuguese.getREFPlayer());
        assertEquals(swedishREF, swedish.getREFPlayer());
        assertEquals(danishREF, danish.getREFPlayer());
        assertEquals(russianREF, russian.getREFPlayer());
    }

    public void testTension(){
        String errMsg = "";
        Game game = getStandardGame();

        ServerPlayer dutch = (ServerPlayer)game.getPlayerByNationId("model.nation.dutch");
        ServerPlayer french = (ServerPlayer)game.getPlayerByNationId("model.nation.french");

        int initialTension = 500;
        int change = 250;

        dutch.setTension(french, new Tension(initialTension));
        french.setTension(dutch, new Tension(initialTension));

        dutch.getTension(french).modify(change);

        int expectedDutchTension = initialTension + change;
        int expectedFrenchTension = initialTension;

        errMsg = "Dutch tension value should have changed";
        assertEquals(errMsg, expectedDutchTension, dutch.getTension(french).getValue());
        errMsg = "French tension value should have remained the same";
        assertEquals(errMsg, expectedFrenchTension ,french.getTension(dutch).getValue());
    }

    public void testAddAnotherPlayersUnit(){
        Game game = getStandardGame();
        Map map = getTestMap();
        game.setMap(map);

        Player dutch =  game.getPlayerByNationId("model.nation.dutch");
        Player french = game.getPlayerByNationId("model.nation.french");

        assertEquals("Wrong number of units for dutch player",0,dutch.getUnits().size());
        assertEquals("Wrong number of units for french player",0,french.getUnits().size());

        Unit colonist = new ServerUnit(game, map.getTile(6, 8), dutch,
                                       freeColonist);
        assertTrue("Colonist should be dutch", colonist.getOwner() == dutch);
        assertEquals("Wrong number of units for dutch player",1,dutch.getUnits().size());

        try{
            french.addUnit(colonist);
            fail("An IllegalStateException should have been raised");
        }
        catch (IllegalStateException e) {
            assertTrue("Colonist owner should not have been changed", colonist.getOwner() == dutch);
            assertEquals("Wrong number of units for dutch player",1,dutch.getUnits().size());
            assertEquals("Wrong number of units for french player",0,french.getUnits().size());

        }

    }
}
