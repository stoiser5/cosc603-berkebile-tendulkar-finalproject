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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.freecol.common.model.Colony.ColonyChangeEvent;


/**
 * Helper container to remember a colony state prior to some
 * change, and fire off any consequent property changes.
 */
public class ColonyWas {

    private static final Logger logger = Logger.getLogger(ColonyWas.class.getName());

    /** a smart comment on the colony variable */
    private final Colony colony;
    /** a smart comment on the population variable */
    private final int population;
    /** a smart comment on the productionBonus variable */
    private final int productionBonus;
    /** a smart comment on the buildQueue variable */
    private final List<BuildableType> buildQueue;


    /**
     * Record the state of a colony.
     *
     * @param colony The <code>Colony</code> to remember.
     */
    public ColonyWas(final Colony colony) {
        this.colony = colony;
        this.population = colony.getUnitCount();
        this.productionBonus = colony.getProductionBonus();
        this.buildQueue = new ArrayList<>(colony.getBuildQueue());
        if (colony.getGoodsContainer() != null) {
            colony.getGoodsContainer().saveState();
        }
    }

    /**
     * Fire any property changes resulting from actions within a
     * colony.
     *
     * @return True if something changed.
     */
    public boolean fireChanges() {
        boolean ret = false;
        final int newPopulation = colony.getUnitCount();
        if (newPopulation != population) {
            final String populationChange = ColonyChangeEvent.POPULATION_CHANGE.toString();
            colony.firePropertyChange(populationChange, population, newPopulation);
            ret = true;
        }
        int newProdBonus = colony.getProductionBonus();
        if (newProdBonus != productionBonus) {
            String pc = ColonyChangeEvent.BONUS_CHANGE.toString();
            colony.firePropertyChange(pc, productionBonus,
                newProdBonus);
            ret = true;
        }
        final List<BuildableType> newBuildQueue = colony.getBuildQueue();
        if (!newBuildQueue.equals(buildQueue)) {
            String propertyChange = ColonyChangeEvent.BUILD_QUEUE_CHANGE.toString();
            colony.firePropertyChange(propertyChange, buildQueue, newBuildQueue);
            ret = true;
        }
        if (colony.getGoodsContainer() != null) {
        	
            colony.getGoodsContainer().fireChanges();
            ret = true;
        }
        return ret;
    }

	private Colony getColony() {
		return colony;
	}

	private int getPopulation() {
		return population;
	}

	private int getProductionBonus() {
		return productionBonus;
	}

	private List<BuildableType> getBuildQueue() {
		return buildQueue;
	}
   
}
