/**
 *  Copyright (C) 2002-2016   The FreeCol Team
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

import net.sf.freecol.util.test.FreeColTestCase;


// TODO: Auto-generated Javadoc
/**
 * The Class MarketTest.
 */
public class MarketTest extends FreeColTestCase {

    /**
     * Make sure that the initial prices are correctly taken from the
     * specification.
     */
    public void testInitialMarket() {

        Game g = getStandardGame();

        Player p = g.getPlayerByNationId("model.nation.dutch");

        Market dm = p.getMarket();

        Specification s = spec();

        for (GoodsType good : s.getStorableGoodsTypeList()) {
            assertEquals(good.toString(), good.getInitialBuyPrice(), dm.getCostToBuy(good));
            assertEquals(good.toString(), good.getInitialSellPrice(), dm.getPaidForSale(good));
        }
    }
    
    /**
     * Test has been traded.
     */
    public void testHasBeenTraded(){
    	
    	Game game = getStandardGame();

        Player player = game.getPlayerByNationId("model.nation.dutch");

        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        goodsType.setPrice(10);
        goodsType.setId("1");
        
        assertFalse(market.hasBeenTraded(goodsType));
    }
    
    /**
     * Test get initial price.
     */
    public void testGetInitialPrice(){
    	
    	Game game = getStandardGame();

        Player player = game.getPlayerByNationId("model.nation.dutch");

        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        assertEquals(0, market.getInitialPrice(goodsType));
    	
    }
    
    /**
     * Test set initial price.
     */
    public void testSetInitialPrice(){
    	
    	Game game = getStandardGame();

        Player player = game.getPlayerByNationId("model.nation.dutch");

        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        market.setInitialPrice(goodsType, 100);
        assertEquals(100, market.getInitialPrice(goodsType));
    	
    }
    
    /**
     * Test get bid price.
     */
    public void testGetBidPrice(){
    	Game game = getStandardGame();

        Player player = game.getPlayerByNationId("model.nation.dutch");
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        Market market = player.getMarket();
        
        assertEquals(0, market.getBidPrice(goodsType, 100));
        
    	
    	
    }
    
    /**
     * Test set arrears.
     */
    public void testSetArrears(){
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        Market market = player.getMarket();
        
        market.setArrears(goodsType, 100);
        assertEquals(100, market.getArrears(goodsType));
    	
    }
    
    /**
     * Test modify income after taxes.
     */
    public void testModifyIncomeAfterTaxes(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        Market market = player.getMarket();
        market.modifyIncomeAfterTaxes(goodsType, 100);
        assertEquals(100, market.getIncomeAfterTaxes(goodsType));
    	
    }
    
    	/**
	     * Test modify income before taxes.
	     */
	    public void testModifyIncomeBeforeTaxes(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        Market market = player.getMarket();
        market.modifyIncomeBeforeTaxes(goodsType, 100);
        assertEquals(100, market.getIncomeBeforeTaxes(goodsType));
    	
    }
    	
    /**
     * Test update.
     */
    public void testUpdate(){
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        Market market = player.getMarket();
        goodsType.setPrice(100);
        market.update(goodsType);
        assertEquals(100, goodsType.getPrice());
    	
    	
    }
    
    /**
     * Test set owner.
     */
    public void testSetOwner(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Market market = player.getMarket();
        market.setOwner(player);
        assertEquals(player, market.getOwner());
    	
    }
    
    /**
     * Test get amount in market.
     */
    public void testGetAmountInMarket(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        market.addGoodsToMarket(goodsType, 200);
        assertEquals(200, market.getAmountInMarket(goodsType));
        
    }
    
    /**
     * Test get amount in market1.
     */
    public void testGetAmountInMarket1(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        assertEquals(0, market.getAmountInMarket(goodsType));
        
    }
   
    /**
     * Test modify sales.
     */
    public void testModifySales(){
    	
    	Game game = getStandardGame();
        Player player = game.getPlayerByNationId("model.nation.dutch");
        Market market = player.getMarket();
        Specification specification = spec("freeCol");
        GoodsType goodsType = new GoodsType(specification.getId(), specification);
        
        market.addGoodsToMarket(goodsType, 200);
        market.modifySales(goodsType, 100);
        assertEquals(100, market.getSales(goodsType));
    	
    	
    }
    
//    public void testGetSalesPrice(){
//    	
//    	Game game = getStandardGame();
//        Player player = game.getPlayerByNationId("model.nation.dutch");
//        Market market = player.getMarket();
//        Specification specification = spec("freeCol");
//        GoodsType goodsType = new GoodsType(specification.getId(), specification);
//        market.addGoodsToMarket(goodsType, 200);
//        
//        assertEquals(250, market.getSalePrice(goodsType,100));
//    	
//    }
//    
    
    /**
 * Test get link target.
 */
    public void testGetLinkTarget(){
    	
      Game game = getStandardGame();
      Player player = game.getPlayerByNationId("model.nation.dutch");
      Market market = player.getMarket();
      market.setOwner(player);
      assertEquals( player.getEurope(), market.getLinkTarget(player));
      
      Player player1 = game.getPlayerByNationId("model.nation.french");
      assertEquals( null, market.getLinkTarget(player1));
      
    	
    }
    
    
    /**
     * Serialization and deserialization?.
     */
    public void testSerialization() {
        //fail();
    }

    /**
     * Do the transaction listeners work?.
     */
    public void testTransactionListeners() {
        //fail("Not yet implemented");
    }
}
